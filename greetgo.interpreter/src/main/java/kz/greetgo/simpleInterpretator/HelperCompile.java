package kz.greetgo.simpleInterpretator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HelperCompile {
  
  public static List<Place> preparePlaces(List<Holder> holders, Context context) {
    final List<Place> places = new ArrayList<Place>();
    MAIN: for (Holder holder : holders) {
      if (holder instanceof HolderSequence) {
        places.add(new PlaceEvalHolded(((HolderSequence)holder).compile(context)));
        continue MAIN;
      }
      if (holder instanceof HolderAtom) {
        HolderAtom ha = (HolderAtom)holder;
        if (ha.isOperator()) {
          places.add(new PlaceOper(ha.getAtom()));
          continue MAIN;
        }
        if (ha.isConstant()) {
          places.add(new PlaceConstant(ha.getAtom()));
          continue MAIN;
        }
        if (ha.isWord()) {
          Place last = null;
          if (places.size() > 0) {
            last = places.get(places.size() - 1);
          }
          if (last instanceof PlaceVar) {
            ((PlaceVar)last).getAtoms().add(ha.getAtom());
          } else {
            places.add(new PlaceVar(ha.getAtom()));
          }
          continue MAIN;
        }
        throw new InterpreterError("ОШИБКА");
      }
      throw new IllegalStateException("Unknown holder class = " + holder.getClass());
    }
    return places;
  }
  
  /**
   * PlaceVar, PlaceEvalHolded ->
   * PlaceVarOther
   * 
   * @param places
   * @param context
   */
  public static void functionsToEval(List<Place> places, Context context) {
    int index = 0;
    while (index < places.size() - 1) {
      if (places.get(index) instanceof PlaceVar) {
        PlaceVar placeVar = (PlaceVar)places.get(index);
        if (!placeVar.isFunction()) {
          index++;
          continue;
        }
      } else {
        index++;
        continue;
      }
      if (!(places.get(index + 1) instanceof PlaceEvalHolded)) {
        PlaceVar placeVar = (PlaceVar)places.get(index);
        throw new InterpreterError(ErrorCode.FUNCTION_WITH_NO_ARGS, "Использование функции '"
            + placeVar.name() + "' без аргументов");
      }
      {
        PlaceVar placeVar = (PlaceVar)places.remove(index);
        PlaceEval placeEval = (PlaceEval)places.remove(index);
        Eval eval = new EvalFunction(placeVar.name(), placeEval.getEval());
        places.add(index, new PlaceEvalOther(eval));
        index++;
      }
    }
    if (places.size() > 0) {
      if (places.get(places.size() - 1) instanceof PlaceVar) {
        PlaceVar placeVar = (PlaceVar)places.get(places.size() - 1);
        if (placeVar.isFunction()) {
          throw new InterpreterError(ErrorCode.FUNCTION_WITH_NO_ARGS, "Использование функции '"
              + placeVar.name() + "' без аргументов");
        }
      }
    }
  }
  
  /**
   * Преобразует не PlaceOper в
   * PlaceEval
   * 
   * @param places
   * @param context
   */
  public static void notOperToEval(List<Place> places, Context context) {
    for (int i = 0, N = places.size(); i < N; i++) {
      Place place = places.get(i);
      if (place instanceof PlaceOper) continue;
      if (place instanceof PlaceEval) continue;
      PlaceEvalOther placeEval = convertToEval(place, context);
      places.set(i, placeEval);
    }
  }
  
  private static PlaceEvalOther convertToEval(Place place, final Context context) {
    if (place instanceof PlaceConstant) {
      final PlaceConstant pc = (PlaceConstant)place;
      return new PlaceEvalOther(new Eval() {
        @Override
        public Object evaluate() {
          return atomToValue(pc.getAtom());
        }
        
        @Override
        public String toString() {
          return pc.toString();
        }
      });
    }
    
    if (place instanceof PlaceVar) {
      final PlaceVar pv = (PlaceVar)place;
      context.checkVarExists(pv.name());
      return new PlaceEvalOther(new Eval() {
        @Override
        public Object evaluate() {
          return context.getVarValue(pv.name());
        }
        
        @Override
        public String toString() {
          return pv.name();
        }
      });
    }
    
    throw new IllegalArgumentException("Cannot convert place class "
        + simpleName(place.getClass().getName()) + " to PlaceEvalOther");
  }
  
  public static String simpleName(String name) {
    String ret = name;
    int lastPoint = ret.lastIndexOf('.');
    if (lastPoint > 0) {
      ret = ret.substring(lastPoint + 1);
    }
    return ret;
  }
  
  public static Object atomToValue(Atom atom) {
    switch (atom.getType()) {
    case DATE:
      return HelperDate.convertStrToDate(atom.getValue());
    case DECIMAL:
      return strToBigDecimal(atom.getValue());
    case STRING:
      return unquoteStr(atom.getValue());
    }
    throw new IllegalArgumentException("Cannot extract value from atom: " + atom
        + ". Atom must be constant");
  }
  
  public static BigDecimal strToBigDecimal(String value) {
    if (value == null) return null;
    value = value.trim();
    if (value.length() == 0) return null;
    return new BigDecimal(value);
  }
  
  public static String unquoteStr(String value) {
    if (value == null) return null;
    return value.substring(1);
  }
  
  /**
   * Преобразует выражение без скобок в
   * эвалюатор. places содержит только
   * PlaceEval и PlaceOper
   * 
   * @param places
   *          знакоместа
   * @return эвалюатор
   */
  public static Eval operateEval(List<Place> places) {
    if (places.size() == 0) {
      throw new InterpreterError("Нет выражения");
    }
    // проверяет чтобы небыло двух операций или значений подряд
    checkTwoFollowsOpersOrEvals(places);
    compileFirstOperation(places);
    
    return compileTwiceOperations(places);
  }
  
  private static void compileFirstOperation(List<Place> places) {
    if (places.size() == 0) {
      throw new InterpreterError("Нет выражения");
    }
    if (!(places.get(0) instanceof PlaceOper)) return;
    if (places.size() == 1) {
      throw new InterpreterError("Операция без операнда");
    }
    PlaceOper placeOper = (PlaceOper)places.remove(0);
    PlaceEval placeEval = (PlaceEval)places.get(0);
    Eval eval = applySingleOper(placeOper, placeEval);
    places.set(0, new PlaceEvalOther(eval));
  }
  
  /**
   * Проверяет на отсутствие двух
   * операций или значений подряд
   * 
   * @param places
   *          список знакомест
   */
  public static void checkTwoFollowsOpersOrEvals(List<Place> places) throws InterpreterError {
    for (int i = 0, N = places.size() - 1; i < N; i++) {
      Place p1 = places.get(i);
      Place p2 = places.get(i + 1);
      if (p1 instanceof PlaceOper && p2 instanceof PlaceOper) {
        PlaceOper o1 = (PlaceOper)p1;
        PlaceOper o2 = (PlaceOper)p2;
        throw new InterpreterError("Нет значения между двумя операциями '"
            + o1.getAtom().getValue() + "' и '" + o2.getAtom().getValue() + "'");
      }
      if (p1 instanceof PlaceEval && p2 instanceof PlaceEval) {
        throw new InterpreterError("Нет операции между двумя значениями");
      }
    }
    // ещё проверим чтобы в конце не было операции
    if (places.size() > 0) {
      Place last = places.get(places.size() - 1);
      if (last instanceof PlaceOper) {
        PlaceOper o = (PlaceOper)last;
        throw new InterpreterError("В конце есть операция: '" + o.getAtom().getValue() + "'");
      }
    }
  }
  
  /**
   * Применяет одиночный оператор к
   * указанному значению
   * 
   * @param placeOper
   *          знакоместо оператора
   * @param placeEval
   *          знакоместо значения
   * @return результирующее значение
   */
  private static Eval applySingleOper(PlaceOper placeOper, final PlaceEval placeEval) {
    String oper = placeOper.getAtom().getValue();
    if ("+".equals(oper)) {
      return placeEval.getEval();
    }
    if ("-".equals(oper)) {
      return new Eval() {
        @Override
        public Object evaluate() {
          return Functions.negate(placeEval.getEval().evaluate());
        }
        
        @Override
        public String toString() {
          return '-' + placeEval.getEval().toString();
        }
      };
    }
    
    if (placeOper.isNotOper()) {
      return new Eval() {
        @Override
        public Object evaluate() {
          return Functions.not(placeEval.getEval().evaluate());
        }
        
        @Override
        public String toString() {
          return "noT " + placeEval.getEval().toString();
        }
      };
    }
    
    throw new InterpreterError("Операция '" + placeOper.getAtom().getValue()
        + "' небывает одиночной");
  }
  
  /**
   * Компилирует парные операции
   * 
   * @param places
   *          знакоместа
   * @return результирующий эвалюатор
   */
  private static Eval compileTwiceOperations(List<Place> places) {
    while (true) {
      if (places.size() == 1) {
        return ((PlaceEval)places.get(0)).getEval();
      }
      if (places.size() == 2) {
        throw new IllegalStateException("Unknown error");
      }
      
      int index = 0;
      int priority = 0;
      for (int i = 1, N = places.size(); i < N; i += 2) {
        PlaceOper placeOper = (PlaceOper)places.get(i);
        if (index == 0) {
          index = i;
          priority = placeOper.priority();
          continue;
        }
        if (priority > placeOper.priority()) {
          priority = placeOper.priority();
          index = i;
        }
      }
      
      if (index == 0) {
        throw new IllegalStateException("No operators");
      }
      
      final PlaceOper placeOper = (PlaceOper)places.get(index);
      
      if (placeOper.isTrippleComparator()) {
        int i2 = index + 2;
        if (i2 < places.size()) {
          PlaceOper placeOper2 = (PlaceOper)places.get(i2);
          if (placeOper2.isTrippleComparator()) {
            final Eval eval1 = ((PlaceEval)places.get(index - 1)).getEval();
            final String oper1 = placeOper.getAtom().getValue();
            final Eval eval2 = ((PlaceEval)places.get(index + 1)).getEval();
            final String oper2 = placeOper2.getAtom().getValue();
            final Eval eval3 = ((PlaceEval)places.get(index + 3)).getEval();
            Eval res = new Eval() {
              @Override
              public Object evaluate() {
                return Functions.trippleCompare(eval1.evaluate(), oper1, eval2.evaluate(), oper2,
                    eval3.evaluate());
              }
              
              @Override
              public String toString() {
                return '(' + eval1.toString() + ' ' + oper1 + ' ' + eval2.toString() + ' ' + oper2
                    + ' ' + eval3.toString() + ')';
              }
            };
            places.remove(index);
            places.remove(index);
            places.remove(index);
            places.remove(index);
            places.set(index - 1, new PlaceEvalOther(res));
            continue;
          }
        }
      }
      
      {
        final Eval eval1 = ((PlaceEval)places.get(index - 1)).getEval();
        final Eval eval2 = ((PlaceEval)places.get(index + 1)).getEval();
        Eval res = new Eval() {
          @Override
          public Object evaluate() {
            return executeDoubleOperator(eval1.evaluate(), placeOper, eval2.evaluate());
          }
          
          @Override
          public String toString() {
            return '(' + eval1.toString() + ' ' + placeOper.getAtom().getValue() + ' '
                + eval2.toString() + ')';
          }
        };
        places.remove(index);
        places.remove(index);
        places.set(index - 1, new PlaceEvalOther(res));
      }
      
    }
  }
  
  private static Object executeDoubleOperator(Object v1, PlaceOper oper, Object v2) {
    
    if (oper.isAnd()) {
      return Functions.and(v1, v2);
    }
    if (oper.isOr()) {
      return Functions.or(v1, v2);
    }
    if (oper.isXor()) {
      return Functions.xor(v1, v2);
    }
    
    String o = oper.getAtom().getValue();
    if ("+".equals(o)) {
      return Functions.plus(v1, v2);
    }
    if ("-".equals(o)) {
      return Functions.minus(v1, v2);
    }
    if ("*".equals(o)) {
      return Functions.mul(v1, v2);
    }
    if ("/".equals(o)) {
      return Functions.divide(v1, v2);
    }
    
    if ("<".equals(o)) {
      return Functions.lt(v1, v2);
    }
    if ("<=".equals(o)) {
      return Functions.le(v1, v2);
    }
    if (">".equals(o)) {
      return Functions.lt(v2, v1);
    }
    if (">=".equals(o)) {
      return Functions.le(v2, v1);
    }
    
    if ("=".equals(o) || "==".equals(o)) {
      return Functions.eq(v1, v2);
    }
    if ("!=".equals(o) || "<>".equals(o)) {
      return !Functions.eq(v1, v2);
    }
    
    throw new InterpreterError("Операция '" + o + "' не парная");
  }
}
