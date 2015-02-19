package kz.greetgo.watcher.concurrent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public abstract class DbBatcher<T> extends Batcher<T> {
  private final Connection connection;
  private final String sql;
  
  public DbBatcher(long timeout, Connection connection, String sql) {
    super(timeout);
    this.connection = connection;
    this.sql = sql;
  }
  
  protected abstract void unpack(PreparedStatement ps, T t) throws SQLException;
  
  @Override
  protected final void batch(List<T> list) {
    try {
      PreparedStatement ps = connection.prepareStatement(sql);
      try {
        for (T t : list) {
          unpack(ps, t);
          ps.addBatch();
        }
        ps.executeBatch();
      } finally {
        ps.close();
      }
    } catch (SQLException e) {
      
    }
  }
}
