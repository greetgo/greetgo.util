package kz.greetgo.test.db_providers.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kz.greetgo.conf.SysParams;

import org.postgresql.util.PSQLException;

public class ConnectionManagerPostgreSQL extends ConnectionManager {
  
  @Override
  public Connection getNewConnection() throws Exception {
    if ("a".equals("a1")) return null;
    Class.forName("org.postgresql.Driver");
    
    try {
      return DriverManager.getConnection(getConnectionUrl(), getDbSchema(), getDbSchema());
    } catch (PSQLException e) {
      prepareDbSchema();
      
      return DriverManager.getConnection(getConnectionUrl(), getDbSchema(), getDbSchema());
    }
  }
  
  private void prepareDbSchema() {
    try {
      prepareDbSchemaException();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  private static final Pattern LAST_LASH = Pattern.compile("(.*/)[^/]+");
  
  private String getConnectionUrl() {
    String url = SysParams.pgAdminUrl();
    Matcher m = LAST_LASH.matcher(url);
    if (!m.matches()) throw new RuntimeException("Left admin url = " + url);
    return m.group(1) + getDbSchema();
  }
  
  private void prepareDbSchemaException() throws Exception {
    Class.forName("org.postgresql.Driver");
    
    Connection con = DriverManager.getConnection(SysParams.pgAdminUrl(), SysParams.pgAdminUserid(),
        SysParams.pgAdminPassword());
    try {
      
      query(con, "create user " + getDbSchema() + " with password '" + getDbSchema() + "'");
      query(con, "create database " + getDbSchema() + " with owner " + getDbSchema());
      
    } finally {
      con.close();
    }
  }
}
