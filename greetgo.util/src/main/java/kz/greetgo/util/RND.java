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
  
  public static String str(int len) {
    char[] charArray = new char[len];
    for (int i = 0; i < len; i++) {
      charArray[i] = ALL.charAt(rnd.nextInt(ALL.length()));
    }
    return new String(charArray);
  }
  
  public static String intStr(int len) {
    char[] charArray = new char[len];
    for (int i = 0; i < len; i++) {
      charArray[i] = DEG.charAt(rnd.nextInt(DEG.length()));
    }
    return new String(charArray);
  }
  
  public static long plusLong(long max) {
    long L = rnd.nextLong();
    if (L < 0) L = -L;
    return L % max;
  }
  
  public static int plusInt(int max) {
    return rnd.nextInt(max);
  }
  
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
  
  public static byte[] byteArray(int len) {
    final byte[] ret = new byte[len];
    rnd.nextBytes(ret);
    return ret;
  }
  
  public static boolean bool() {
    return plusInt(10) % 2 == 0;
  }
  
  @SafeVarargs
  public static <E extends Enum<E>> E someEnum(E... values) {
    return values[rnd.nextInt(values.length)];
  }
  
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
