package kz.greepto.gpen.editors.gpen.prop;

import java.util.List;
import kz.greepto.gpen.editors.gpen.action.Oper;
import kz.greepto.gpen.editors.gpen.model.IdFigure;
import kz.greepto.gpen.util.Handler;
import kz.greepto.gpen.util.HandlerKiller;

@SuppressWarnings("all")
public interface SceneWorker {
  public abstract String takeId(final Object object);
  
  public abstract IdFigure findByIdOrDie(final String id);
  
  public abstract List<String> all();
  
  public abstract void applyOper(final Oper oper);
  
  public abstract HandlerKiller addChangeHandler(final Handler handler);
}
