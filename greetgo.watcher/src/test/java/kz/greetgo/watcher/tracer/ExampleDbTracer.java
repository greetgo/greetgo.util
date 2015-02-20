package kz.greetgo.watcher.tracer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.sql.DataSource;

import kz.greetgo.watcher.concurrent.Batcher;
import kz.greetgo.watcher.concurrent.DataSourceBatcher;

public class ExampleDbTracer extends ProjectTracer {
  private static DataSource dataSource = null; // TODO
  private static final Batcher<ExampleEvent> BATCHER = new DataSourceBatcher<ExampleEvent>(100, dataSource,
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
