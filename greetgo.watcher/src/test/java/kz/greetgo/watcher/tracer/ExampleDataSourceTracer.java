package kz.greetgo.watcher.tracer;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.sql.DataSource;

import kz.greetgo.watcher.concurrent.Batcher;
import kz.greetgo.watcher.concurrent.DataSourceBatcher;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ExampleDataSourceTracer extends ProjectTracer {
  private static final DataSource dataSource;
  static {
    final String name = "test_trace";
    String url = System.getenv("PG_ADMIN_URL");
    String user = System.getenv("PG_ADMIN_USERID");
    String password = System.getenv("PG_ADMIN_PASSWORD");
    DataSource ds = dataSource(url, user, password);
    
    try (Connection con = ds.getConnection()) {
      exec(con, "drop database " + name);
      exec(con, "drop user " + name);
      exec(con, "create user " + name);
      exec(con, "create database " + name);
      exec(con, "grant all on database " + name + " to " + name);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    
    dataSource = dataSource("jdbc:postgresql://localhost/" + name, name, "");
    try (Connection con = dataSource.getConnection()) {
      exec(con,
          "create table trace_table (run varchar(40), num bigint, stamp timestamp, message varchar(1024))");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  private static final DataSource dataSource(String url, String user, String password) {
    ComboPooledDataSource ds = new ComboPooledDataSource();
    try {
      ds.setDriverClass("org.postgresql.Driver");
    } catch (PropertyVetoException e) {
      e.printStackTrace();
    }
    ds.setJdbcUrl(url);
    ds.setUser(user);
    ds.setPassword(password);
    return ds;
  }
  
  private static final void exec(Connection con, String sql) {
    try (PreparedStatement ps = con.prepareStatement(sql)) {
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  private static final Batcher<ExampleEvent> BATCHER = new DataSourceBatcher<ExampleEvent>(100,
      dataSource, "insert into trace_table (run, num, stamp, message) values (?, ?, ?, ?);") {
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
