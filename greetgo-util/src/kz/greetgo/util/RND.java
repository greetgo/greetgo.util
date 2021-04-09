package kz.greetgo.util;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

public class RND {

  public static final String rus = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
  public static final String RUS = rus.toUpperCase();

  @SuppressWarnings("SpellCheckingInspection")
  public static final String eng = "abcdefghijklmnopqrstuvwxyz";
  public static final String ENG = eng.toUpperCase();

  public static final String DEG = "0123456789";

  public static final String ALL = rus + RUS + eng + ENG + DEG;
  public static final char[] ALL_CHARS = ALL.toCharArray();

  public static final String ALL_ENG = eng + ENG + DEG;
  public static final char[] ALL_ENG_CHARS = ALL_ENG.toCharArray();

  public static final Random rnd = new Random();

  /**
   * Generates random string with a length of <code>len</code> characters. Characters are selected
   * randomly from the following array: Arabic numerals, Russian alphabet uppercase and lowercase,
   * English alphabet uppercase and lowercase.
   *
   * @param len the length of generate string
   * @return generated string
   */
  public static String str(int len) {
    return strFrom(len, ALL_CHARS);
  }

  /**
   * Generates random string with a length of <code>len</code> characters. Characters are selected
   * randomly from the following array: Arabic numerals, English alphabet uppercase and lowercase.
   *
   * @param len the length of generate string
   * @return generated string
   */
  public static String strEng(int len) {
    return strFrom(len, ALL_ENG_CHARS);
  }

  /**
   * Generates random string with a length of <code>len</code> characters. Characters are selected
   * randomly from the array <code>availableChars</code>
   *
   * @param len            the length of generate string
   * @param availableChars the source of characters for generation string.
   * @return generated string
   */
  public static String strFrom(int len, char[] availableChars) {
    char[] charArray = new char[len];
    for (int i = 0; i < len; i++) {
      charArray[i] = availableChars[rnd.nextInt(availableChars.length)];
    }
    return new String(charArray);
  }

  /**
   * Generates a string consisting of random numbers
   *
   * @param len the length of generate string
   * @return generated string
   */
  public static String strInt(int len) {
    char[] charArray = new char[len];
    for (int i = 0; i < len; i++) {
      charArray[i] = DEG.charAt(rnd.nextInt(DEG.length()));
    }
    return new String(charArray);
  }

  /**
   * <p>
   * Generates a random positive number of type <code>long</code> in the range
   * </p>
   * <p>
   * 0 &lt;= x &lt; max
   * </p>
   *
   * @param max the maximum value of the generate number
   * @return generated number
   */
  public static long plusLong(long max) {
    long L = rnd.nextLong();
    if (L < 0) {
      L = -L;
    }
    return L % max;
  }

  /**
   * <p>
   * Generates a random positive number of type <code>int</code> in the range
   * </p>
   * <p>
   * 0 &lt;= x &lt; max
   * </p>
   *
   * @param max the maximum value of the generate number
   * @return generated number
   */
  public static int plusInt(int max) {
    return rnd.nextInt(max);
  }

  /**
   * Generates a random date in the range from <code>yearFrom</code> years ago, to
   * <code>yearTo</code> years ago
   *
   * @param yearFrom as many years ago - range start in which date is generated
   * @param yearTo   as many years ago - range end in which date is generated
   * @return randomly generated date
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
   * Generates a random date in the range from <code>fromDaysBeforeNow</code> days ago, to
   * <code>toDayAfterNow</code> days ago
   *
   * @param fromDaysBeforeNow as many days ago - range start in which date is generated
   * @param toDayAfterNow     as many days ago - range end in which date is generated
   * @return randomly generated date
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
   * Generates a random array with a length <code>len</code>
   *
   * @param len the length of generate array
   * @return generated array
   */
  public static byte[] byteArray(int len) {
    final byte[] ret = new byte[len];
    rnd.nextBytes(ret);
    return ret;
  }

  /**
   * Generates random Boolean value
   *
   * @return generated random value
   */
  public static boolean bool() {
    return plusInt(10) % 2 == 0;
  }

  /**
   * Selects a random value from the values transmitted from enum
   *
   * @param values enum values
   * @return randomly selected value
   */
  @SafeVarargs
  public static <E extends Enum<E>> E someEnum(E... values) {
    return values[rnd.nextInt(values.length)];
  }

  /**
   * Selects random element from varargs array
   *
   * @param values varargs source array
   * @param <T>    any returning type
   * @return random element from the array
   */
  @SafeVarargs
  public static <T> T from(T... values) {
    return values[rnd.nextInt(values.length)];
  }

  /**
   * Selects random element from list
   *
   * @param source source list
   * @param <T>    any returning type
   * @return random element from the list
   */
  public static <T> T of(Collection<T> source) {
    if (source instanceof List) {
      List<T> list = (List<T>) source;
      return list.get(rnd.nextInt(list.size()));
    } else {
      Object[] array = source.toArray();
      //noinspection unchecked
      return (T) array[rnd.nextInt(array.length)];
    }
  }

  /**
   * <p>
   * Generates a random real positive number in the range
   * </p>
   * <p>
   * 0 &lt;= x &lt; max
   * </p>
   *
   * @param max   the upper limit of the selected values
   * @param point the number of decimal places
   * @return generated number
   */
  public static double plusDouble(double max, int point) {
    double ret = rnd.nextDouble();
    ret *= max;
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
   * Generates a random number of type <code>BigDecimal</code> in the range
   * </p>
   * <p>
   * 0 &lt;= x &lt; max
   * </p>
   *
   * @param max        the upper limit of generate number
   * @param afterPoint the number of decimal places
   * @return generated number
   */
  public static BigDecimal bd(long max, int afterPoint) {
    BigDecimal ret = new BigDecimal(plusLong(max));
    for (int i = 0; i < afterPoint; i++) {
      ret = ret.multiply(BigDecimal.TEN);
      ret = ret.add(new BigDecimal(plusInt(10)));
    }

    for (int i = 0; i < afterPoint; i++) {
      //noinspection BigDecimalMethodWithoutRoundingCalled
      ret = ret.divide(BigDecimal.TEN);
    }
    return ret;
  }
}
