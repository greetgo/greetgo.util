package kz.greetgo.sqlmanager.gen;

public class AllUtil {
  
  public static String firstUpper(String str) {
    if (str == null) return null;
    str = str.trim();
    if (str.length() == 0) return null;
    
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }
  
}
