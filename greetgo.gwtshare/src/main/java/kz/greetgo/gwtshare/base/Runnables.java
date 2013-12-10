package kz.greetgo.gwtshare.base;

import java.util.ArrayList;

public final class Runnables implements Runnable {
  private final ArrayList<Runnable> runnables = new ArrayList<Runnable>();
  
  public final void add(Runnable runnable) {
    runnables.add(runnable);
  }
  
  @Override
  public final void run() {
    for (Runnable runnable : runnables) {
      runnable.run();
    }
  }
}
