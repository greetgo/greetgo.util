package kz.greetgo.watcher.trace;

import java.util.UUID;

public class TraceIdThreadStorage {
  private final TraceIdGenerator generator = new TraceIdGenerator(UUID.randomUUID());
  
  private final ThreadLocal<TraceId> threadStorage = new ThreadLocal<TraceId>() {
    @Override
    protected TraceId initialValue() {
      return generator.next();
    }
  };
  
  public final void reset() {
    threadStorage.set(generator.next());
  }
  
  public final TraceId traceId() {
    return threadStorage.get();
  }
}
