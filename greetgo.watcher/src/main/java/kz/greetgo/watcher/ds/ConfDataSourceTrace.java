package kz.greetgo.watcher.ds;

import kz.greetgo.watcher.trace.LinkedMessageOuter;
import kz.greetgo.watcher.trace.TopFileTrace;

@Deprecated
public class ConfDataSourceTrace extends TopFileTrace {
  
  final LinkedMessageOuter outer;
  
  public ConfDataSourceTrace() {
    super(new LinkedMessageOuter());
    outer = (LinkedMessageOuter)super.outer;
  }
  
  public void info(String logMessage) {
    StringBuilder sb = new StringBuilder();
    appendTime(sb);
    appendTraceID(sb);
    append(sb, "INFO", logMessage);
    out(sb);
  }
  
  public void common(String logMessage) {
    StringBuilder sb = new StringBuilder();
    appendTime(sb);
    appendTraceID(sb);
    append(sb, "COMMON", logMessage);
    out(sb);
  }
  
  public void info2(String logMessage) {
    StringBuilder sb = new StringBuilder();
    appendTime(sb);
    appendTraceID(sb);
    append(sb, "INFO2", logMessage);
    out(sb);
  }
  
}
