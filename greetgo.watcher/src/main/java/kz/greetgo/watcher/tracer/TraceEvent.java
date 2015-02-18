package kz.greetgo.watcher.tracer;

import java.util.Date;
import java.util.UUID;

public final class TraceEvent {
  public final UUID run;
  public final long number;
  public final Date time;
  public final String tag;
  public final String user;
  public final TraceLevel level;
  public final String message;
  
  public TraceEvent(UUID run, long number, Date time, String tag, String user, TraceLevel level,
      String message) {
    this.run = run;
    this.number = number;
    this.time = time;
    this.tag = tag;
    this.user = user;
    this.level = level;
    this.message = message;
  }
}
