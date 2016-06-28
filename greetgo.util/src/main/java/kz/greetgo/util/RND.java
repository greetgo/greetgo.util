package kz.greetgo.util;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class RND {

  public static final String rus = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
  public static final String RUS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";

  public static final String eng = "abcdefghijklmnopqrstuvwxyz";
  public static final String ENG = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  public static final String DEG = "0123456789";

  public static final String ALL = rus + RUS + eng + ENG + DEG;

  public static final Random rnd = new Random();

  /**
   * Генерирует случайную строку длинной <code>len</code> символов. Символы выбираются случайно из
   * следующего надора: арабские цифры, русский алфавит большой и малый регистры, английский алфавит
   * большой и малый регистры.
   * 
   * @param len
   *          блина генерируемой строки
   * @return сгенерированная строка
   */
  public static String str(int len) {
    char[] charArray = new char[len];
    for (int i = 0; i < len; i++) {
      charArray[i] = ALL.charAt(rnd.nextInt(ALL.length()));
    }
    return new String(charArray);
  }

  /**
   * Генерирует строку, сотоящую из случайно выбранных цыфр
   * 
   * @param len
   *          длинна генерируемой строки
   * @return сгенерированная строка
   */
  public static String intStr(int len) {
    char[] charArray = new char[len];
    for (int i = 0; i < len; i++) {
      charArray[i] = DEG.charAt(rnd.nextInt(DEG.length()));
    }
    return new String(charArray);
  }

  /**
   * <p>
   * Генерирует случайное положительное число типа <code>long</code> в диапазоне
   * </p>
   * <p>
   * 0 &lt;= x &lt; max
   * </p>
   * 
   * @param max
   *          максимальное значение генерируемого числа
   * @return сгенерированное число
   */
  public static long plusLong(long max) {
    long L = rnd.nextLong();
    if (L < 0) L = -L;
    return L % max;
  }

  /**
   * <p>
   * Генерирует случайное положительное число типа <code>int</code> в диапазоне
   * </p>
   * <p>
   * 0 &lt;= x &lt; max
   * </p>
   * 
   * @param max
   *          максимальное значение генерируемого числа
   * @return сгенерированное число
   */
  public static int plusInt(int max) {
    return rnd.nextInt(max);
  }

  /**
   * Генерирует случайную дату в диапазоне от <code>yearFrom</code> лет назад, до
   * <code>yearTo</code> лет назад
   * 
   * @param yearFrom
   *          столько лет назад - начало диапазона, в котором генерируется дата
   * @param yearTo
   *          столько лет назад - конец диапазона, в котором генерируется дата
   * @return случайно сгенерированная дата
   */
  public static Date dateYears(int yearFrom, int yearTo) {
    Calendar cal = new GregorianCalendar();
    cal.add(Calendar.YEAR, yearFrom);
    long from = cal.getTimeInMillis();
    cal.add(Calendar.YEAR, yearTo - yearFrom);
    long to = cal.getTimeInMillis();
    if (from > to) {
      long tmp = from;
      from = to;
      to = tmp;
    }
    final long time = from + plusLong(to - from);
    return new Date(time);
  }

  /**
   * Генерирует случайную дату в диапазоне от <code>fromDaysBeforeNow</code> дней назад, до
   * <code>toDayAfterNow</code> дней назад
   * 
   * @param fromDaysBeforeNow
   *          столько дней назад - начало диапазона, в котором генерируется дата
   * @param toDayAfterNow
   *          столько дней назад - конец диапазона, в котором генерируется дата
   * @return случайно сгенерированная дата
   */
  public static Date dateDays(int fromDaysBeforeNow, int toDayAfterNow) {
    Calendar cal = new GregorianCalendar();
    cal.add(Calendar.DAY_OF_YEAR, fromDaysBeforeNow);
    long from = cal.getTimeInMillis();
    cal.add(Calendar.DAY_OF_YEAR, toDayAfterNow - fromDaysBeforeNow);
    long to = cal.getTimeInMillis();
    long x = plusLong(to - from);
    cal.setTimeInMillis(from + x);
    return cal.getTime();
  }

  /**
   * Генерирует случайный массив длинной <code>len</code>
   * 
   * @param len
   *          длинна генерируемого массива
   * @return сгенерированный массив
   */
  public static byte[] byteArray(int len) {
    final byte[] ret = new byte[len];
    rnd.nextBytes(ret);
    return ret;
  }

  /**
   * Генерирует случайную булевскую величину
   * 
   * @return сгенерированная случайная величина
   */
  public static boolean bool() {
    return plusInt(10) % 2 == 0;
  }

  /**
   * Выбирает случайное значение из значений передаваемого enum-а
   * 
   * @param values
   *          значения enum-a
   * @return случайно выбранное значение
   */
  @SafeVarargs
  public static <E extends Enum<E>> E someEnum(E... values) {
    return values[rnd.nextInt(values.length)];
  }

  /**
   * <p>
   * Генерирует случайное вещественное положительное число в диапазоне
   * </p>
   * <p>
   * 0 &lt;= x &lt; max
   * </p>
   * 
   * @param max
   *          верхняя граница области выбираемых значений
   * @param point
   *          количество разрядов после запятой
   * @return сгенерированное число
   */
  public static double plusDouble(double max, int point) {
    double ret = rnd.nextDouble();
    ret *= (double)max;
    for (int i = 0; i < point; i++) {
      ret *= 10.0;
    }
    ret = Math.floor(ret);
    for (int i = 0; i < point; i++) {
      ret /= 10.0;
    }
    return ret;
  }

  /**
   * <p>
   * Генерирует случайное число типа <code>BigDecimal</code> в диапазоне
   * </p>
   * <p>
   * 0 &lt;= x &lt; max
   * </p>
   * 
   * @param max
   *          верхняя граница генерируемого числа
   * @param afterPoint
   *          количество разрядов после запятой
   * @return сгенерированное число
   */
  public static BigDecimal bd(long max, int afterPoint) {
    BigDecimal ret = new BigDecimal(plusLong(max));
    for (int i = 0; i < afterPoint; i++) {
      ret = ret.multiply(BigDecimal.TEN);
      ret = ret.add(new BigDecimal(plusInt(10)));
    }

    for (int i = 0; i < afterPoint; i++) {
      ret = ret.divide(BigDecimal.TEN);
    }
    return ret;
  }
}
