package kz.greepto.gpen.editors.gpen.prop;

import kz.greepto.gpen.editors.gpen.action.Oper;
import kz.greepto.gpen.editors.gpen.prop.PropOptions;
import kz.greepto.gpen.editors.gpen.prop.ValueGetter;
import kz.greepto.gpen.editors.gpen.prop.ValueSetter;
import kz.greepto.gpen.util.Handler;
import kz.greepto.gpen.util.HandlerKiller;

@SuppressWarnings("all")
public interface PropAccessor {
  public final static Object DIFF_VALUES = new Object() {
    public String toString() {
      return "Разные значения";
    }
  };
  
  public abstract Class<?> getType();
  
  public abstract String getName();
  
  public abstract Object getValue();
  
  public abstract PropOptions getOptions();
  
  public abstract void setValue(final Object value);
  
  public abstract Oper getSettingOper(final Object newValue);
  
  public abstract HandlerKiller addChangeHandler(final Handler handler);
  
  public abstract boolean compatibleWith(final PropAccessor with);
  
  public abstract PropAccessor operator_add(final PropAccessor a);
  
  public abstract PropAccessor operator_plus(final PropAccessor a);
  
  public abstract ValueGetter getGetter();
  
  public abstract ValueSetter getSetter();
}
