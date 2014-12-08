package kz.greepto.gpen.editors.gpen.prop;

import kz.greepto.gpen.editors.gpen.action.Action;
import kz.greepto.gpen.editors.gpen.prop.PropOptions;
import kz.greepto.gpen.util.Handler;
import kz.greepto.gpen.util.HandlerKiller;

@SuppressWarnings("all")
public interface PropAccessor {
  public abstract Class<?> getType();
  
  public abstract String getName();
  
  public abstract Object getValue();
  
  public abstract PropOptions getOptions();
  
  public abstract void setValue(final Object value);
  
  public abstract Action getSettingAction(final Object newValue);
  
  public abstract HandlerKiller addChangeHandler(final Handler handler);
}
