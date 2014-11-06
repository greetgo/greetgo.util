package kz.greetgo.gbatis.util.test.connections;

import java.sql.Connection;

public class ConnectionManagerPostgreSQL extends ConnectionManager {
  
  @Override
  public Connection getNewConnection() throws Exception {
    
    Class.forName("org.postgresql.Driver");
    
    // TODO Auto-generated method stub
    return null;
  }
  
}
