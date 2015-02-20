package kz.greetgo.watcher.concurrent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

public abstract class DataSourceBatcher<T> extends Batcher<T> {
  private final DataSource dataSource;
  private final String sql;
  
  public DataSourceBatcher(long timeout, DataSource dataSource, String sql) {
    super(timeout);
    this.dataSource = dataSource;
    this.sql = sql;
  }
  
  protected abstract void unpack(PreparedStatement ps, T t) throws SQLException;
  
  @Override
  protected final void batch(List<T> list) {
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement ps = connection.prepareStatement(sql)) {
        for (T t : list) {
          unpack(ps, t);
          ps.addBatch();
        }
        ps.executeBatch();
        connection.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
