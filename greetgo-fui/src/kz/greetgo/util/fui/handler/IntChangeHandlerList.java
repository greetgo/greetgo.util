package kz.greetgo.util.fui.handler;

public class IntChangeHandlerList extends HandlerList<IntChangeHandler> {
  public void fire(int intValue) {
    for (final IntChangeHandler handler : this) {
      handler.changed(intValue);
    }
  }

  public final StrChangeHandler strChangeHandler = strValue -> {
    try {
      fire(strValue == null ? 0 : Integer.parseInt(strValue.trim()));
    } catch (NumberFormatException e) {
      fire(0);
    }
  };
}
