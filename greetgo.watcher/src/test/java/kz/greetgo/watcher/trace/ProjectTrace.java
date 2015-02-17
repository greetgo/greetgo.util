package kz.greetgo.watcher.trace;

import java.util.List;

public class ProjectTrace {
  
  public final static TraceIdThreadStorage tip = new TraceIdThreadStorage();
  
  private final BufferWorker<TraceRow> outer = new BufferWorker<TraceRow>(100) {
    @Override
    protected void save(List<TraceRow> list) {
      
    }
  };
  
  public void info(String message) {
    outer.append(new TraceRow(tip.traceId(), message));
  }
}
