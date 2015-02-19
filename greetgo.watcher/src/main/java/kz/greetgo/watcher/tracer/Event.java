package kz.greetgo.watcher.tracer;

import java.util.Date;
import java.util.UUID;

public class Event {
  public final UUID run;
  public final long number;
  public final Date timestamp = new Date();
  
  public Event(TraceStamp stamp) {
    this.run = stamp.run();
    this.number = stamp.number();
  }
  
  @Override
  public String toString() {
    String hex8 = Integer.toHexString((int)run.getLeastSignificantBits());
    return String.format("Trace-%s-%08d %3$tF %3$tT", hex8, number, timestamp);
  }
}
