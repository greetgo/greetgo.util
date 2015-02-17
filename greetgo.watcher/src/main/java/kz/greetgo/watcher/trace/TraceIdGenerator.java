package kz.greetgo.watcher.trace;

import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public final class TraceIdGenerator implements Iterator<TraceId> {
  private final AtomicLong nextId = new AtomicLong(1);
  
  private final UUID run;
  
  public TraceIdGenerator(UUID run) {
    this.run = run;
  }
  
  @Override
  public boolean hasNext() {
    return true;
  }
  
  @Override
  public TraceId next() {
    return new TraceId(run, nextId.incrementAndGet());
  }
  
  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }
}
