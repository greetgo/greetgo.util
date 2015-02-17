package kz.greetgo.watcher.ds;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

@Deprecated
public abstract class DataSourceWatcher implements DataSource, LogMessageAcceptor {
  
  private ConnectionWatcher connectionWatcher = new ConnectionWatcher(this);
  
  private DataSource watchingDataSource;
  private boolean active = false;
  
  public void setActive(boolean active) {
    this.active = active;
  }
  
  public void setWatchingDataSource(DataSource watchingDataSource) {
    this.watchingDataSource = watchingDataSource;
  }
  
  @Override
  public PrintWriter getLogWriter() throws SQLException {
    return watchingDataSource.getLogWriter();
  }
  
  @Override
  public void setLogWriter(PrintWriter out) throws SQLException {
    watchingDataSource.setLogWriter(out);
  }
  
  @Override
  public void setLoginTimeout(int seconds) throws SQLException {
    watchingDataSource.setLoginTimeout(seconds);
  }
  
  @Override
  public int getLoginTimeout() throws SQLException {
    return watchingDataSource.getLoginTimeout();
  }
  
  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    return watchingDataSource.unwrap(iface);
  }
  
  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return watchingDataSource.isWrapperFor(iface);
  }
  
  @Override
  public Connection getConnection() throws SQLException {
    if (active) {
      acceptInfo("CONNECTION Open");
      return connectionWatcher.wrap(watchingDataSource.getConnection());
    }
    {
      return watchingDataSource.getConnection();
    }
  }
  
  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    if (active) {
      acceptInfo("CONNECTION Open : username = " + username + ", password = ***");
      return connectionWatcher.wrap(watchingDataSource.getConnection(username, password));
    }
    {
      return watchingDataSource.getConnection(username, password);
    }
  }
}
