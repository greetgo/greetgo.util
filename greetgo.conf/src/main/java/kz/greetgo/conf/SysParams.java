package kz.greetgo.conf;

public class SysParams {
  
  public static String get(String name) {
    return get(name, null);
  }
  
  public static String get(String name, String defaultValue) {
    {
      String ret = System.getProperty(name);
      if (ret != null && ret.trim().length() > 0) return ret;
    }
    {
      String ret = System.getenv(name);
      if (ret != null && ret.trim().length() > 0) return ret;
    }
    return defaultValue;
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
  
  public static String pgAdminUrlKey() {
    return "PG_ADMIN_URL";
  }
  
  public static String pgAdminUrl() {
    return get(pgAdminUrlKey(), "jdbc:postgresql://localhost/postgres");
  }
  
  public static String pgAdminUseridKey() {
    return "PG_ADMIN_USERID";
  }
  
  public static String pgAdminUserid() {
    return get(pgAdminUseridKey(), "postgres");
  }
  
  public static String pgAdminPasswordKey() {
    return "PG_ADMIN_PASSWORD";
  }
  
  public static String pgAdminPassword() {
    return get(pgAdminPasswordKey(), "");
  }
  
}
