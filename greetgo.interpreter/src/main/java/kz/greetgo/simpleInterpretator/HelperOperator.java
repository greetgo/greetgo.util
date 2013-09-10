package kz.greetgo.simpleInterpretator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelperOperator {
  public static final Map<String, Integer> OPERATOR_PRIORITY = new HashMap<String, Integer>();
  static {
    int p = 1;
    OPERATOR_PRIORITY.put("^", p);
    p++;
    OPERATOR_PRIORITY.put("*", p);
    OPERATOR_PRIORITY.put("/", p);
    OPERATOR_PRIORITY.put("%", p);
    p++;
    OPERATOR_PRIORITY.put("+", p);
    OPERATOR_PRIORITY.put("-", p);
    p++;
    OPERATOR_PRIORITY.put("<=>", p);
    p++;
    OPERATOR_PRIORITY.put("=", p);
    OPERATOR_PRIORITY.put("==", p);
    OPERATOR_PRIORITY.put("!=", p);
    OPERATOR_PRIORITY.put("<>", p);
    OPERATOR_PRIORITY.put("<=", p);
    OPERATOR_PRIORITY.put(">=", p);
    OPERATOR_PRIORITY.put(">", p);
    OPERATOR_PRIORITY.put("<", p);
    p++;
    OPERATOR_PRIORITY.put("НЕ", p);
    OPERATOR_PRIORITY.put("NOT", p);
    p++;
    OPERATOR_PRIORITY.put("И", p);
    OPERATOR_PRIORITY.put("AND", p);
    p++;
    OPERATOR_PRIORITY.put("ИИЛИ", p);
    OPERATOR_PRIORITY.put("XOR", p);
    p++;
    OPERATOR_PRIORITY.put("ИЛИ", p);
    OPERATOR_PRIORITY.put("OR", p);
  }
  
  public static int priority(String operatorStr) {
    Integer ret = OPERATOR_PRIORITY.get(operatorStr.toUpperCase());
    if (ret == null) return 0;
    return ret.intValue();
  }
  
  public static boolean isOperator(String operatorStr) {
    return priority(operatorStr) > 0;
  }
  
  private static String BIG_SYMBOLS[] = null;
  
  public static final String[] bigSymbols() {
    if (BIG_SYMBOLS == null) {
      List<String> operators = new ArrayList<String>();
      operators.addAll(OPERATOR_PRIORITY.keySet());
      {
        int index = 0;
        while (index < operators.size()) {
          if (isBigSymbol(operators.get(index))) {
            index++;
          } else {
            operators.remove(index);
          }
        }
      }
      BIG_SYMBOLS = operators.toArray(new String[operators.size()]);
    }
    return BIG_SYMBOLS;
  }
  
  private static boolean isBigSymbol(String str) {
    if (str.length() <= 1) return false;
    return !HelperChar.isLetter(str.charAt(0));
  }
}
