package kz.greepto.gpen.editors.gpen.prop;

import kz.greepto.gpen.editors.gpen.action.Oper;
import kz.greepto.gpen.util.Handler;
import kz.greepto.gpen.util.HandlerKiller;

@SuppressWarnings("all")
public interface SceneWorker {
  public abstract String takeId(final Object object);
  
  public abstract void applyOper(final Oper oper);
  
  public abstract HandlerKiller addChangeHandler(final Handler handler);
}
