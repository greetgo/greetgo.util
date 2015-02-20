package kz.greetgo.watcher.tracer;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;

import kz.greetgo.watcher.concurrent.Batcher;
import kz.greetgo.watcher.concurrent.DataSourceBatcher;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ExampleDataSourceTracer extends ProjectTracer {
  private static final ComboPooledDataSource ds = new ComboPooledDataSource();
  {
    String url = System.getenv("PG_ADMIN_URL");
    String user = System.getenv("PG_ADMIN_USERID");
    String password = System.getenv("PG_ADMIN_PASSWORD");
    try {
      ds.setDriverClass("org.postgresql.Driver");
    } catch (PropertyVetoException e) {
      e.printStackTrace();
    }
    ds.setJdbcUrl(url);
    ds.setUser(user);
    ds.setPassword(password);
    
    try (Connection con = ds.getConnection()) {
      for (String cmd : Arrays.asList("drop database test_", "drop user", "create user",
          "create database test_", "grant all on database " + user + " to")) {
        try (PreparedStatement ps = con.prepareStatement(cmd + " " + user)) {
          ps.executeUpdate();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  private static final Batcher<ExampleEvent> BATCHER = new DataSourceBatcher<ExampleEvent>(100, ds,
      "insert into trace_table (run, num, stamp, message) values (?, ?, ?, ?)") {
    protected void unpack(PreparedStatement ps, ExampleEvent t) throws SQLException {
      ps.setString(1, t.run.toString());
      ps.setLong(2, t.number);
      ps.setTimestamp(3, new Timestamp(t.timestamp.getTime()));
      ps.setString(4, t.message);
    }
  };
  
  public final void trace(String message) {
    BATCHER.add(new ExampleEvent(message));
  }
}
