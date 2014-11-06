package kz.greetgo.gbatis.util.test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kz.greetgo.gbatis.futurecall.DbType;
import kz.greetgo.gbatis.util.test.connections.ConnectionManager;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;

public abstract class TestBase {
  
  @DataProvider
  protected Object[][] connectProvider() throws Exception {
    
    List<Connection> conList = new ArrayList<>();
    
    Map<DbType, Connection> conMap = getConnectionMap();
    
    for (DbType dbType : DbType.values()) {
      Connection con = conMap.get(dbType);
      if (con != null) conList.add(con);
    }
    
    int C = conList.size();
    Object[][] ret = new Object[C][];
    for (int i = 0; i < C; i++) {
      ret[i] = new Object[] { conList.get(i) };
    }
    
    return ret;
  }
  
  private Map<DbType, Connection> connectionMap = null;
  
  private Map<DbType, Connection> getConnectionMap() throws Exception {
    if (connectionMap == null) {
      connectionMap = new HashMap<>();
      for (DbType dbType : DbType.values()) {
        ConnectionManager connectionManager = ConnectionManager.get(dbType);
        if (connectionManager == null) continue;
        Connection connection = connectionManager.getNewConnection();
        connectionMap.put(dbType, connection);
      }
    }
    return connectionMap;
  }
  
  @AfterClass
  protected void cleanConnectionMap() throws Exception {
    if (connectionMap == null) return;
    for (Connection con : connectionMap.values()) {
      con.close();
    }
    connectionMap = null;
  }
}
