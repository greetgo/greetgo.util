package kz.greetgo.watcher.trace;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class BufferWorker<T> {
  
  private final TimeoutScheduler scheduler;
  
  public BufferWorker(long timeout) {
    scheduler = new TimeoutScheduler(timeout) {
      @Override
      protected void execute() {
        while (true) {
          T t = queue.poll();
          if (t == null) break;
          list.add(t);
        }
        
        try {
          save(new ArrayList<>(list));
        } catch (Exception e) {
          e.printStackTrace(System.err);
        } finally {
          list.clear();
        }
      }
    };
  }
  
  private boolean started = false;
  private final ConcurrentLinkedQueue<T> queue = new ConcurrentLinkedQueue<>();
  
  public final void append(T t) {
    if (!started) {
      scheduler.start();
      started = true;
    }
    queue.offer(t);
  }
  
  private final List<T> list = new ArrayList<>();
  
  protected abstract void save(List<T> list);
}
