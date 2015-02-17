package kz.greetgo.watcher.trace;

import java.util.Date;

public class TraceRow {
  public final Date time = new Date();
  public final TraceId traceId;
  public final String user;
  public final String message;
  
  public TraceRow(TraceId traceId, String user, String message) {
    this.traceId = traceId;
    this.user = user;
    this.message = message;
  }
  
  public TraceRow(TraceId traceId, String message) {
    this.traceId = traceId;
    this.user = "unknown";
    this.message = message;
  }
}