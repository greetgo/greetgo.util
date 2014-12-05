package kz.greepto.gpen.editors.gpen.prop;

import kz.greepto.gpen.editors.gpen.action.Action;
import kz.greepto.gpen.util.Handler;
import kz.greepto.gpen.util.HandlerKiller;

@SuppressWarnings("all")
public interface SceneWorker {
  public abstract String takeId(final Object object);
  
  public abstract void sendAction(final Action action);
  
  public abstract HandlerKiller addChangeHandler(final Handler handler);
}
