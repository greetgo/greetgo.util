package kz.greetgo.watcher.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class Batcher<T> {
  private final ConcurrentLinkedQueue<T> queue = new ConcurrentLinkedQueue<>();
  private final TimeoutWorker worker;
  private boolean started = false;
  
  public Batcher(long timeout) {
    worker = new TimeoutWorker(timeout) {
      private final List<T> list = new ArrayList<>();
      
      protected void work() {
        while (true) {
          T t = queue.poll();
          if (t == null) break;
          list.add(t);
        }
        
        if (list.isEmpty()) {
          return;
        }
        
        try {
          batch(new ArrayList<>(list));
        } catch (Exception e) {
          e.printStackTrace(System.err);
        } finally {
          list.clear();
        }
      }
    };
  }
  
  public final void add(T t) {
    if (!started) {
      worker.start();
      started = true;
    }
    queue.add(t);
  }
  
  public final void addAll(Collection<? extends T> c) {
    if (!started) {
      worker.start();
      started = true;
    }
    queue.addAll(c);
  }
  
  protected abstract void batch(List<T> list);
}
