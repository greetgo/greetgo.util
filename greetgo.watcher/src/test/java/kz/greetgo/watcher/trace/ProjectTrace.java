package kz.greetgo.watcher.trace;

import java.util.List;

import kz.greetgo.watcher.concurrent.Batcher;

public class ProjectTrace {
  
  public final static TraceIdThreadStorage tip = new TraceIdThreadStorage();
  
  private final Batcher<TraceRow> outer = new Batcher<TraceRow>(100) {
    @Override
    protected void batch(List<TraceRow> list) {
      
    }
  };
  
  public void info(String message) {
    outer.add(new TraceRow(tip.traceId(), message));
  }
}
