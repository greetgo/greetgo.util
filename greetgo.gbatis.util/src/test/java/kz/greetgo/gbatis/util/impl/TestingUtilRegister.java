package kz.greetgo.gbatis.util.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import kz.greetgo.gbatis.futurecall.SqlViewer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.AbstractDataSource;

public class TestingUtilRegister extends AbstractUtilRegister {
  
  private final class ConnectionDataSource extends AbstractDataSource {
    private final Connection con;
    
    private ConnectionDataSource(Connection con) {
      this.con = con;
    }
    
    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
      throw new SQLFeatureNotSupportedException();
    }
    
    @Override
    public Connection getConnection(String username, String password) throws SQLException {
      throw new UnsupportedOperationException();
    }
    
    @Override
    public Connection getConnection() throws SQLException {
      return (Connection)Proxy.newProxyInstance(getClass().getClassLoader(),
          new Class<?>[] { Connection.class }, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
              if (method.getName().equals("close") && method.getParameterTypes().length == 0) {
                //skip closing of connection
                return null;
              }
              return method.invoke(con, args);
            }
          });
    }
  }

  private JdbcTemplate jdbc;
  
  @Override
  protected JdbcTemplate jdbc() {
    return jdbc;
  }
  
  public void setConnection(final Connection con) {
    jdbc = new JdbcTemplate(new ConnectionDataSource(con));
  }
  
  public SqlViewer sqlViewer;
  
  @Override
  protected SqlViewer sqlViewer() {
    return sqlViewer;
  }
  
}
