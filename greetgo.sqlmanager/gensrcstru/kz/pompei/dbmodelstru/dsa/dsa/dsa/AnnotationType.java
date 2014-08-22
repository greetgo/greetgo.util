package kz.pompei.dbmodelstru.dsa.dsa.dsa;
public enum AnnotationType {
  ONE, TWO, THREE, FOUR, FIVE, SIX, ;

public static AnnotationType valueOfOrNull(String str) {
  try { return valueOf(str);
  } catch (java.lang.IllegalArgumentException e) {
    return null;
  }
}
}
