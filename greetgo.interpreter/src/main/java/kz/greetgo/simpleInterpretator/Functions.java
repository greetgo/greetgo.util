package kz.greetgo.simpleInterpretator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class Functions {
  
  public static Object abs(Object value) {
    if (value == null) return null;
    if (value instanceof Integer) {
      Integer x = (Integer)value;
      if (x.intValue() < 0) return -x.intValue();
      return x;
    }
    if (value instanceof Long) {
      Long x = (Long)value;
      if (x.longValue() < 0) return -x.longValue();
      return x;
    }
    if (value instanceof BigDecimal) {
      BigDecimal x = (BigDecimal)value;
      if (x.compareTo(BigDecimal.ZERO) < 0) return x.negate();
      return x;
    }
    if (value instanceof BigInteger) {
      BigInteger x = (BigInteger)value;
      if (x.compareTo(BigInteger.ZERO) < 0) return x.negate();
      return x;
    }
    throw new InterpreterError(ErrorCode.CANNOT_EXECUTE_FUNCTION,
        "Не могу вычислить функцию МОДУЛЬ для типа " + value.getClass().getName() + ", значение = "
            + value);
  }
  
  /**
   * Меняет знак значения на
   * противоположный
   * 
   * @param value
   *          значение
   * @return значение с противоположным
   *         знаком
   */
  public static Object negate(Object value) {
    if (value == null) return null;
    if (value instanceof Integer) {
      Integer ret = (Integer)value;
      return -ret;
    }
    if (value instanceof Long) {
      Long ret = (Long)value;
      return -ret;
    }
    if (value instanceof BigDecimal) {
      BigDecimal x = (BigDecimal)value;
      return x.negate();
    }
    if (value instanceof BigInteger) {
      BigInteger x = (BigInteger)value;
      return x.negate();
    }
    throw new InterpreterError(ErrorCode.CANNOT_NEGATE, "Не могу поменять знак у типа "
        + value.getClass().getName() + ", значение = " + value);
  }
  
  /**
   * Логическое "НЕ"
   * 
   * @param value
   * @return
   */
  public static Object not(Object value) {
    if (value == null) return true;
    if (value instanceof Integer) {
      Integer ret = (Integer)value;
      return ret.intValue() == 0;
    }
    if (value instanceof Long) {
      Long ret = (Long)value;
      return ret.longValue() == 0;
    }
    if (value instanceof BigDecimal) {
      BigDecimal x = (BigDecimal)value;
      return x.compareTo(BigDecimal.ZERO) == 0;
    }
    if (value instanceof BigInteger) {
      BigInteger x = (BigInteger)value;
      return x.compareTo(BigInteger.ZERO) == 0;
    }
    if (value instanceof Boolean) {
      return !(Boolean)value;
    }
    throw new InterpreterError(ErrorCode.CANNOT_NEGATE, "Не могу инвертировать тип "
        + value.getClass().getName() + ", значение = " + value);
  }
  
  private static BigDecimal bigDecimal(Object value) {
    if (value == null) {
      value = 0;
    }
    if (value instanceof Integer) {
      Integer ret = (Integer)value;
      return new BigDecimal(ret);
    }
    if (value instanceof Long) {
      Long ret = (Long)value;
      return new BigDecimal(ret);
    }
    if (value instanceof BigDecimal) {
      BigDecimal ret = (BigDecimal)value;
      return ret;
    }
    if (value instanceof BigInteger) {
      BigInteger ret = (BigInteger)value;
      return new BigDecimal(ret);
    }
    if (value instanceof Boolean) {
      boolean ret = (Boolean)value;
      return new BigDecimal(ret ? 1 :0);
    }
    throw new InterpreterError(ErrorCode.NOT_A_NUMBER, "Не могу преобразовать в число " + value);
  }
  
  private static final boolean binaryCompare(Object x, String op, Object y) {
    int cmp = bigDecimal(x).compareTo(bigDecimal(y));
    if ("<".equals(op)) {
      return cmp < 0;
    }
    if ("<=".equals(op)) {
      return cmp <= 0;
    }
    throw new InterpreterError(ErrorCode.UNKNOWN_FUNCTION, "Не могу выполнить сравнение " + op);
  }
  
  /**
   * Вычисляет значение выражения x < y
   * < z
   * 
   * Вместо < может быть <=
   * 
   * x < y < z === x < y AND y < z
   * 
   * @param x
   * @param o1
   * @param y
   * @param o2
   * @param z
   * @return результат
   */
  public static Object trippleCompare(Object x, String o1, Object y, String o2, Object z) {
    return binaryCompare(x, o1, y) && binaryCompare(y, o2, z);
  }
  
  public static Object plus(Object v1, Object v2) {
    return bigDecimal(v1).add(bigDecimal(v2));
  }
  
  public static Object minus(Object v1, Object v2) {
    return bigDecimal(v1).subtract(bigDecimal(v2));
  }
  
  public static Object mul(Object v1, Object v2) {
    return bigDecimal(v1).multiply(bigDecimal(v2));
  }
  
  public static Object divide(Object v1, Object v2) {
    if (v2 == null) throw new InterpreterError(ErrorCode.DIVISION_BY_ZERO, "Не могу делить " + v1
        + " на " + v2);
    try {
      return bigDecimal(v1).divide(bigDecimal(v2), RoundingMode.FLOOR);
    } catch (ArithmeticException e) {
      throw new InterpreterError(ErrorCode.DIVISION_BY_ZERO, "Не могу делить " + v1 + " на " + v2);
    }
  }
  
  private static final boolean bool(Object value) {
    if (value == null) return false;
    if (value instanceof Integer) {
      Integer ret = (Integer)value;
      return ret.intValue() != 0;
    }
    if (value instanceof Long) {
      Long ret = (Long)value;
      return ret.longValue() != 0;
    }
    if (value instanceof BigDecimal) {
      BigDecimal x = (BigDecimal)value;
      return x.compareTo(BigDecimal.ZERO) != 0;
    }
    if (value instanceof BigInteger) {
      BigInteger x = (BigInteger)value;
      return x.compareTo(BigInteger.ZERO) != 0;
    }
    if (value instanceof Boolean) {
      return (Boolean)value;
    }
    throw new InterpreterError(ErrorCode.CANNOT_BOOLEAN, "Не могу конвертировать тип " + value);
  }
  
  public static Boolean and(Object v1, Object v2) {
    return bool(v1) && bool(v2);
  }
  
  public static Boolean or(Object v1, Object v2) {
    return bool(v1) || bool(v2);
  }
  
  public static Boolean xor(Object v1, Object v2) {
    return bool(v1) ^ bool(v2);
  }
  
  public static Boolean lt(Object v1, Object v2) {
    return bigDecimal(v1).compareTo(bigDecimal(v2)) < 0;
  }
  
  public static Boolean le(Object v1, Object v2) {
    return bigDecimal(v1).compareTo(bigDecimal(v2)) <= 0;
  }
  
  public static Boolean eq(Object v1, Object v2) {
    if (v1 == null) return v2 == null;
    if (v1 instanceof String && v2 instanceof String) {
      String s1 = (String)v1;
      String s2 = (String)v2;
      return s1.equalsIgnoreCase(s2);
    }
    return v1.equals(v2);
  }
  
}
