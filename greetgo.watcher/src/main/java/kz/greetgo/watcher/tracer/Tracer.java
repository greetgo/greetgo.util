package kz.greetgo.watcher.tracer;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import kz.greetgo.watcher.concurrent.Batcher;

public abstract class Tracer {
  private final AtomicLong currentNumber = new AtomicLong(1);
  private final UUID run = UUID.randomUUID();
  private final ThreadLocal<Long> number = new ThreadLocal<Long>() {
    protected Long initialValue() {
      return currentNumber.incrementAndGet();
    }
  };
  
  protected abstract Batcher<TraceEvent> batcher();
  
  protected abstract String user();
  
  public final void reset() {
    number.set(currentNumber.incrementAndGet());
  }
  
  private final void event(String tag, TraceLevel level, String message) {
    batcher().add(new TraceEvent(run, number.get(), new Date(), tag, user(), level, message));
  }
  
  public Trace trace(final String tag) {
    return new Trace() {
      public void fatal(String message) {
        event(tag, TraceLevel.FATAL, message);
      }
      
      public void error(String message) {
        event(tag, TraceLevel.ERROR, message);
      }
      
      public void warning(String message) {
        event(tag, TraceLevel.WARNING, message);
      }
      
      public void info(String message) {
        event(tag, TraceLevel.INFO, message);
      }
      
      public void debug(String message) {
        event(tag, TraceLevel.DEBUG, message);
      }
    };
  }
}
