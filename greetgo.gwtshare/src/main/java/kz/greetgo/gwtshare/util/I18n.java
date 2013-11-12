package kz.greetgo.gwtshare.util;

public final class I18n {
  
  private I18n() {}
  
  public static final String pluralRu(int n, String s1, String s2, String s5) {
    n = Math.abs(n) % 100;
    if (n < 11 || n > 19) {
      int d = n % 10;
      if (d == 1) return s1;
      if (2 <= d && d <= 4) return s2;
    }
    return s5;
  }
}
