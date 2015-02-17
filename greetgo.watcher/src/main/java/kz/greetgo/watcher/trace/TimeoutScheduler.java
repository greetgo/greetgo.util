package kz.greetgo.watcher.trace;

public abstract class TimeoutScheduler extends Thread {
  
  private final long timeout;
  
  public TimeoutScheduler(long timeout) {
    this.timeout = timeout;
  }
  
  @Override
  public void run() {
    while (true) {
      execute();
      
      try {
        Thread.sleep(timeout);
      } catch (InterruptedException e) {
        execute();
        break;
      }
      
    }
  }
  
  protected abstract void execute();
}
