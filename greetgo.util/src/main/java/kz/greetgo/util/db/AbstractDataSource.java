package kz.greetgo.util.db;

import java.io.PrintWriter;
import java.sql.SQLException;

import javax.sql.DataSource;

public abstract class AbstractDataSource implements DataSource {
  
  /**
   * Returns 0, indicating the default system timeout is to be used.
   */
  public int getLoginTimeout() throws SQLException {
    return 0;
  }
  
  /**
   * Setting a login timeout is not supported.
   */
  public void setLoginTimeout(int timeout) throws SQLException {
    throw new UnsupportedOperationException("setLoginTimeout");
  }
  
  /**
   * LogWriter methods are not supported.
   */
  public PrintWriter getLogWriter() {
    throw new UnsupportedOperationException("getLogWriter");
  }
  
  /**
   * LogWriter methods are not supported.
   */
  public void setLogWriter(PrintWriter pw) throws SQLException {
    throw new UnsupportedOperationException("setLogWriter");
  }
  
  //---------------------------------------------------------------------
  // Implementation of JDBC 4.0's Wrapper interface
  //---------------------------------------------------------------------
  
  @SuppressWarnings("unchecked")
  public <T> T unwrap(Class<T> iface) throws SQLException {
    if (iface == null) throw new NullPointerException("iface == null");
    if (!DataSource.class.equals(iface)) {
      throw new SQLException("DataSource of type [" + getClass().getName()
          + "] can only be unwrapped as [javax.sql.DataSource], not as [" + iface.getName());
    }
    return (T)this;
  }
  
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return DataSource.class.equals(iface);
  }
  
}
