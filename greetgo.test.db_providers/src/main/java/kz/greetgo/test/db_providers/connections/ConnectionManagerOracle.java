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
      return DriverManager.getConnection(url(), mySchema(), mySchema());
    } catch (SQLException e) {
      prepareDbSchema();
      
      return DriverManager.getConnection(url(), mySchema(), mySchema());
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
  
  private String mySchema() {
    return System.getProperty("user.name") + '_' + getDbSchema();
  }
  
  private void prepareDbSchemaException() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    
    Connection con = DriverManager.getConnection(url(), SysParams.oracleAdminUserid(),
        SysParams.oracleAdminPassword());
    try {
      query(con, "create user " + mySchema() + " identified by " + mySchema());
      query(con, "grant all privileges to " + mySchema());
    } finally {
      con.close();
    }
  }
}
