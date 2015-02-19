package kz.greetgo.watcher.tracer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import kz.greetgo.watcher.concurrent.Batcher;
import kz.greetgo.watcher.concurrent.DbBatcher;

public class ExampleDbTracer extends ProjectTracer<ExampleEvent> {
  private static Connection connection = null; // TODO
  private static final Batcher<ExampleEvent> BATCHER = new DbBatcher<ExampleEvent>(100, connection,
      "insert into trace_table (run, num, stamp, message) values (?, ?, ?, ?)") {
    protected void unpack(PreparedStatement ps, ExampleEvent t) throws SQLException {
      ps.setString(1, t.run.toString());
      ps.setLong(2, t.number);
      ps.setTimestamp(3, new Timestamp(t.timestamp.getTime()));
      ps.setString(4, t.message);
    }
  };
  
  @Override
  protected boolean isEnabled() {
    return true; // May read from HotConfig
  }
  
  @Override
  protected Batcher<ExampleEvent> batcher() {
    return BATCHER;
  }
  
  @Override
  protected ExampleEvent pack(String message) {
    return new ExampleEvent(message);
  }
}
