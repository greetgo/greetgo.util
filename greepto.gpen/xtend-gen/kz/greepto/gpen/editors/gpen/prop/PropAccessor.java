package kz.greepto.gpen.editors.gpen.prop;

import kz.greepto.gpen.editors.gpen.action.Action;
import kz.greepto.gpen.util.Handler;
import kz.greepto.gpen.util.HandlerKiller;

@SuppressWarnings("all")
public interface PropAccessor {
  public abstract Class<?> getType();
  
  public abstract Object getValue();
  
  public abstract boolean isReadOnly();
  
  public abstract void setValue(final Object value);
  
  public abstract Action getSettingAction(final Object newValue);
  
  public abstract HandlerKiller addChangeHandler(final Handler handler);
}
