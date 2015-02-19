package kz.greetgo.watcher.tracer;

import kz.greetgo.watcher.concurrent.Batcher;

public abstract class Tracer<T extends Event> {
  protected abstract Batcher<T> batcher();
  
  protected abstract T pack(String message);
  
  protected abstract boolean isEnabled();
  
  public final void trace(String message) {
    if (isEnabled()) {
      batcher().add(pack(message));
    }
  }
  
}
