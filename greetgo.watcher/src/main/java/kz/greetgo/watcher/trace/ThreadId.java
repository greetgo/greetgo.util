package kz.greetgo.watcher.trace;

import java.util.concurrent.atomic.AtomicInteger;

@Deprecated
public class ThreadId {
  private static final AtomicInteger nextId = new AtomicInteger(1);
  
  private static final ThreadLocal<Integer> threadId = new ThreadLocal<Integer>() {
    @Override
    protected Integer initialValue() {
      return nextId.getAndIncrement();
    }
  };
  
  public static int get() {
    return threadId.get();
  }
}