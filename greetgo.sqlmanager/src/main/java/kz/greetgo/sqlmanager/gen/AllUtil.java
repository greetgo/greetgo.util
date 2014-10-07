package kz.greetgo.sqlmanager.gen;

/**
 * Вынесение различных функций
 * 
 * @author pompei
 * 
 */
public class AllUtil {
  
  /**
   * Поднять первую букву в верхний регистр
   * 
   * @param str
   *          исходная строка
   * @return строка с заглавной первой буквой
   */
  public static String firstUpper(String str) {
    if (str == null) return null;
    str = str.trim();
    if (str.length() == 0) return null;
    
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }
  
}
