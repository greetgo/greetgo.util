package kz.greetgo.test.db_providers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kz.greetgo.gbatis.futurecall.DbType;
import kz.greetgo.gbatis.futurecall.SqlViewer;
import kz.greetgo.gbatis.model.Result;
import kz.greetgo.gbatis.model.SqlWithParams;
import kz.greetgo.gbatis.util.OperUtil;
import kz.greetgo.test.db_providers.connections.ConnectionManager;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;

public abstract class TestBase {
  
  protected static final String CONNECT_PROVIDER = "connectProvider";
  
  @DataProvider
  protected Object[][] connectProvider() throws Exception {
    
    List<Connection> conList = new ArrayList<>();
    
    Map<DbType, Connection> conMap = getConnectionMap();
    
    for (DbType dbType : usingDbTypes()) {
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
  
  protected abstract DbType[] usingDbTypes();
  
  private Map<DbType, Connection> connectionMap = null;
  
  private Map<DbType, Connection> getConnectionMap() throws Exception {
    if (connectionMap == null) {
      connectionMap = new HashMap<>();
      for (DbType dbType : usingDbTypes()) {
        ConnectionManager connectionManager = ConnectionManager.get(dbType);
        if (connectionManager == null) continue;
        connectionManager.setDbSchema(dbSchema());
        Connection connection = connectionManager.getNewConnection();
        if (connection == null) continue;
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
  
  protected abstract String dbSchema();
  
  protected static void query(Connection con, String sql) throws SQLException {
    PreparedStatement ps = con.prepareStatement(sql);
    try {
      ps.executeUpdate();
    } finally {
      ps.close();
    }
  }
  
  protected static boolean queryForce(Connection con, String sql) throws SQLException {
    PreparedStatement ps = con.prepareStatement(sql);
    try {
      ps.executeUpdate();
      return true;
    } catch (SQLException e) {
      return false;
    } finally {
      ps.close();
    }
  }
  
  protected static SqlViewer stdSqlViewer() {
    return new SqlViewer() {
      @Override
      public void view(String sql, List<Object> params, long delay) {
        StringBuilder sb = new StringBuilder();
        for (Object p : params) {
          sb.append('[').append(p).append(']');
        }
        sb.append(' ');
        sb.append(sql);
        System.out.println(sb);
      }
      
      @Override
      public boolean needView() {
        return true;
      }
    };
  }
  
  protected int count(Connection con, String tableName, Object... fieldsAndValues) {
    StringBuilder sb = new StringBuilder("select count(1) from ");
    sb.append(tableName);
    int C = fieldsAndValues.length;
    if (C % 2 > 0) throw new IllegalArgumentException();
    List<Object> params = new ArrayList<>();
    for (int i = 0; i < C; i += 2) {
      String field = (String)fieldsAndValues[i];
      Object value = fieldsAndValues[i + 1];
      
      sb.append(i == 0 ? " where " :" and ").append(field).append(" = ?");
      params.add(value);
    }
    
    return OperUtil.call(con, SqlWithParams.selectWith(sb.toString(), params),
        Result.simple(Integer.class));
  }
}
