package kz.greepto.gpen.editors.gpen.action;

import kz.greepto.gpen.editors.gpen.model.Scene;

@SuppressWarnings("all")
public abstract class Action {
  public abstract void apply(final Scene scene);
  
  public abstract void cancel(final Scene scene);
}
