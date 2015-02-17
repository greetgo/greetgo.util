package kz.greetgo.watcher.trace;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;

@Deprecated
public class TopFileTrace extends TraceIdThreadStorage {
  
  public int traceIdLength = 7;
  public int threadIdLength = 7;
  
  private static String processId = "unknown";
  private static String host = "unknown";
  
  static {
    
    String name = ManagementFactory.getRuntimeMXBean().getName();
    
    if (name != null) {
      String[] split = name.split("@");
      if (split.length > 0) {
        processId = split[0];
      }
      if (split.length > 1) {
        host = split[1];
      }
    }
  }
  
  protected final MessageOuter outer;
  
  public TopFileTrace(MessageOuter outer) {
    this.outer = outer;
  }
  
  public void close() {
    outer.close();
  }
  
  public String processId() {
    return processId;
  }
  
  public String host() {
    return host;
  }
  
  private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss.SSS");
  
  public static void tolen(int len, StringBuilder str) {
    if (str == null) str = new StringBuilder(len);
    if (str.length() >= len) return;
    for (int i = 0, C = len - str.length(); i < C; i++) {
      str.insert(0, '0');
    }
  }
  
  public void appendTime(StringBuilder sb) {
    if (sb.length() > 0) sb.append(' ');
    long millis = System.currentTimeMillis();
    sb.append(millis).append(' ');
    sb.append(DF.format(new Date(millis)));
  }
  
  public void appendTraceID(StringBuilder sb) {
    if (sb.length() > 0) sb.append(' ');
    sb.append(traceId().toString());
  }
  
  public void appendRunTraceIds(StringBuilder sb) {
    if (sb.length() > 0) sb.append(' ');
    {
      sb.append("R").append(traceId().run.toString().substring(0, 8));
    }
    {
      StringBuilder s = new StringBuilder(traceIdLength);
      s.append(traceId().number);
      tolen(traceIdLength, s);
      sb.append("TRACE").append(s);
    }
  }
  
  public void appendRunID(StringBuilder sb) {
    if (sb.length() > 0) sb.append(' ');
    sb.append("R").append(traceId().run.toString().substring(0, 8));
  }
  
  public void appendThreadID(StringBuilder sb) {
    if (sb.length() > 0) sb.append(' ');
    StringBuilder s = new StringBuilder(threadIdLength);
    s.append(ThreadId.get());
    tolen(threadIdLength, s);
    sb.append("THREAD").append(s);
  }
  
  public void appendMessage(StringBuilder sb, String message) {
    if (sb.length() > 0) sb.append(' ');
    sb.append("MESS ### ").append(message);
  }
  
  public void appendDelay(StringBuilder sb, long timeFrom, long timeTo) {
    if (sb.length() > 0) sb.append(' ');
    sb.append("DELAY ").append(timeTo - timeFrom);
  }
  
  public void appendDelayToNow(StringBuilder sb, long timeFrom) {
    if (sb.length() > 0) sb.append(' ');
    sb.append("DELAY ").append(System.currentTimeMillis() - timeFrom);
  }
  
  @Deprecated
  public void appendProcessId(StringBuilder sb) {
    if (sb.length() > 0) sb.append(' ');
    sb.append("PROCESS").append(processId);
  }
  
  @Deprecated
  public void appendHost(StringBuilder sb) {
    if (sb.length() > 0) sb.append(' ');
    sb.append("HOST").append(host);
  }
  
  public void append(StringBuilder sb, String key, String value) {
    if (sb.length() > 0) sb.append(' ');
    sb.append(key).append(' ').append(value);
  }
  
  public void out(StringBuilder sb) {
    outer.outMessage(sb.toString());
  }
}
