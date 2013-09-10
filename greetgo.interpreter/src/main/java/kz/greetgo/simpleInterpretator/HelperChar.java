package kz.greetgo.simpleInterpretator;

public class HelperChar {
  public static boolean isSpace(char c) {
    if (c == ' ') return true;
    if (c == '\n') return true;
    if (c == '\r') return true;
    if (c == '\t') return true;
    return false;
  }
  
  public static boolean isDigit(char c) {
    return '0' <= c && c <= '9';
  }
  
  public static final String ENG_SMALL_LETTERS = "abcdefghijklmnopqrstuvwxyz";
  public static final String ENG_BIG_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  public static final String RUS_SMALL_LETTERS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
  public static final String RUS_BIG_LETTERS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
  
  public static boolean isEngSmallLetter(char c) {
    return ENG_SMALL_LETTERS.indexOf(c) > -1;
  }
  
  public static boolean isEngBigLetter(char c) {
    return ENG_BIG_LETTERS.indexOf(c) > -1;
  }
  
  public static boolean isRusSmallLetter(char c) {
    return RUS_SMALL_LETTERS.indexOf(c) > -1;
  }
  
  public static boolean isRusBigLetter(char c) {
    return RUS_BIG_LETTERS.indexOf(c) > -1;
  }
  
  public static boolean isEngLetter(char c) {
    return isEngBigLetter(c) || isEngSmallLetter(c);
  }
  
  public static boolean isRusLetter(char c) {
    return isRusBigLetter(c) || isRusSmallLetter(c);
  }
  
  public static boolean isLetter(char c) {
    return isRusLetter(c) || isEngLetter(c);
  }
  
  public static boolean isWord(char c) {
    return c == '_' || c == '.' || isLetter(c) || isDigit(c);
  }
  
  public static boolean isKav(char c) {
    return c == '"' || c == '\'' || c == '`';
  }
  
  public static final String SYMBOLS = "()!%^*/-+=<>";
  
  public static boolean isSymbol(char c) {
    return SYMBOLS.indexOf(c) > -1;
  }
}
