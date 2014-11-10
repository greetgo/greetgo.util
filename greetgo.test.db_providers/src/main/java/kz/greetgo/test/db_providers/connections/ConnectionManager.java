package kz.greetgo.test.db_providers.connections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import kz.greetgo.util.db.DbType;

public abstract class ConnectionManager {
  private String dbSchema;
  
  public static ConnectionManager get(DbType dbType) {
    final Class<?> classs;
    try {
      classs = Class.forName(ConnectionManager.class.getName() + dbType.name());
    } catch (ClassNotFoundException e) {
      return null;
    }
    try {
      return (ConnectionManager)classs.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }
  
  protected static void query(Connection con, String sql) throws SQLException {
    PreparedStatement ps = con.prepareStatement(sql);
    try {
      ps.executeUpdate();
    } finally {
      ps.close();
    }
  }
  
  public abstract Connection getNewConnection() throws Exception;
  
  public void setDbSchema(String dbSchema) {
    this.dbSchema = dbSchema;
  }
  
  public String getDbSchema() {
    return dbSchema;
  }
}
