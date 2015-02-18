package kz.greetgo.watcher.concurrent;

public abstract class TimeoutWorker extends Thread {
  private final long timeout;
  
  public TimeoutWorker(long timeout) {
    this.timeout = timeout;
  }
  
  @Override
  public final void run() {
    while (true) {
      work();
      try {
        Thread.sleep(timeout);
      } catch (InterruptedException e) {
        work();
        break;
      }
    }
  }
  
  protected abstract void work();
}
