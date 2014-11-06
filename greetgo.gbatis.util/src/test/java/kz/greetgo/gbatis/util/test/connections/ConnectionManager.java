package kz.greetgo.gbatis.util.test.connections;

import java.sql.Connection;

import kz.greetgo.gbatis.futurecall.DbType;

public abstract class ConnectionManager {
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
  
  public abstract Connection getNewConnection() throws Exception;
}
