package kz.greetgo.watcher.tracer;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public final class TraceStamp {
  private final UUID run = UUID.randomUUID();
  private final AtomicLong currentNumber = new AtomicLong(0);
  private final ThreadLocal<Long> number = new ThreadLocal<Long>() {
    protected Long initialValue() {
      return currentNumber.incrementAndGet();
    }
  };
  
  public final UUID run() {
    return run;
  }
  
  public final long number() {
    return number.get();
  }
  
  public final void reset() {
    number.set(currentNumber.incrementAndGet());
  }
}
