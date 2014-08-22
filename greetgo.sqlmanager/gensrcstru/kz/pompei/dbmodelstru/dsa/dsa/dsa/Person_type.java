package kz.pompei.dbmodelstru.dsa.dsa.dsa;
public enum Person_type {
  NATURAL, LEGAL, ;

public static Person_type valueOfOrNull(String str) {
  try { return valueOf(str);
  } catch (java.lang.IllegalArgumentException e) {
    return null;
  }
}
}
