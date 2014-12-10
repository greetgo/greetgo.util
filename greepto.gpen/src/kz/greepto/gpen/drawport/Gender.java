package kz.greepto.gpen.drawport;

public enum Gender {
  MALE("fdsfdsf"), FEMALE("fdsfdsfs");
  
  public final String displayStr;
  
  private Gender(String displayStr) {
    this.displayStr = displayStr;
  }
  
  public static String sqlCaseWhen() {
    StringBuilder sb = new StringBuilder();
    for (Gender x : values()) {
      sb.append(" when '").append(x.name()).append("' then '").append(x.displayStr).append("' ");
    }
    return sb.toString();
  }
}
