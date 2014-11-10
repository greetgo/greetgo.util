package kz.greetgo.test.db_providers.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import kz.greetgo.conf.SysParams;

public class ConnectionManagerOracle extends ConnectionManager {
  
  @Override
  public Connection getNewConnection() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    
    try {
      return DriverManager.getConnection(url(), getDbSchema(), getDbSchema());
    } catch (SQLException e) {
      prepareDbSchema();
      
      return DriverManager.getConnection(url(), getDbSchema(), getDbSchema());
    }
  }
  
  private String url() {
    return "jdbc:oracle:thin:@" + SysParams.oracleAdminHost() + ":" + SysParams.oracleAdminPort()
        + ":" + SysParams.oracleAdminSid();
  }
  
  private void prepareDbSchema() {
    try {
      prepareDbSchemaException();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  private void prepareDbSchemaException() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    
    Connection con = DriverManager.getConnection(url(), SysParams.oracleAdminUserid(),
        SysParams.oracleAdminPassword());
    try {
      query(con, "create user " + getDbSchema() + " identified by " + getDbSchema());
      query(con, "grant all privileges to " + getDbSchema());
    } finally {
      con.close();
    }
  }
}
