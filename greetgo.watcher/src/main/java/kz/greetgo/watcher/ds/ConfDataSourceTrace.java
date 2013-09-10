package kz.greetgo.watcher.ds;

import kz.greetgo.watcher.trace.TopTrace;
import kz.greetgo.watcher.trace.LinkedMessageOuter;

public class ConfDataSourceTrace extends TopTrace {
  
  final LinkedMessageOuter outer;
  
  public ConfDataSourceTrace() {
    super(new LinkedMessageOuter());
    outer = (LinkedMessageOuter)super.outer;
  }
  
  public void info(String logMessage) {
    StringBuilder sb = new StringBuilder();
    appendTime(sb);
    appendHost(sb);
    appendProcessId(sb);
    appendThreadID(sb);
    appendTraceID(sb);
    append(sb, "INFO", logMessage);
    out(sb);
  }
  
  public void common(String logMessage) {
    StringBuilder sb = new StringBuilder();
    appendTime(sb);
    appendHost(sb);
    appendProcessId(sb);
    appendThreadID(sb);
    appendTraceID(sb);
    append(sb, "COMMON", logMessage);
    out(sb);
  }
  
  public void info2(String logMessage) {
    StringBuilder sb = new StringBuilder();
    appendTime(sb);
    appendHost(sb);
    appendProcessId(sb);
    appendThreadID(sb);
    appendTraceID(sb);
    append(sb, "INFO2", logMessage);
    out(sb);
  }
  
}
