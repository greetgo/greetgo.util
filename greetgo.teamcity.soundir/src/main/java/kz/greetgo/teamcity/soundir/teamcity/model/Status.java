package kz.greetgo.teamcity.soundir.teamcity.model;

public enum Status {
  FAILURE, SUCCESS, UNKNOWN;
  
  public static Status fromStr(String name) {
    try {
      return valueOf(name);
    } catch (IllegalArgumentException | NullPointerException e) {
      return UNKNOWN;
    }
  }
  
}
