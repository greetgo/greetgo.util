package kz.greetgo.conf;

public class SysParams {
  
  public static String get(String name) {
    {
      String ret = System.getProperty(name);
      if (ret != null && ret.trim().length() > 0) return ret;
    }
    return System.getenv(name);
  }
  
  public static String oracleAdminHost() {
    return get(oracleAdminHostKey());
  }
  
  public static String oracleAdminHostKey() {
    return "ORACLE_ADMIN_HOST";
  }
  
  public static String oracleAdminPort() {
    return get(oracleAdminPortKey());
  }
  
  public static String oracleAdminPortKey() {
    return "ORACLE_ADMIN_PORT";
  }
  
  public static String oracleAdminSid() {
    return get(oracleAdminSidKey());
  }
  
  public static String oracleAdminSidKey() {
    return "ORACLE_ADMIN_SID";
  }
  
  public static String oracleAdminUserid() {
    return get(oracleAdminUseridKey());
  }
  
  public static String oracleAdminUseridKey() {
    return "ORACLE_ADMIN_USERID";
  }
  
  public static String oracleAdminPassword() {
    return get(oracleAdminPasswordKey());
  }
  
  public static String oracleAdminPasswordKey() {
    return "ORACLE_ADMIN_PASSWORD";
  }
}
