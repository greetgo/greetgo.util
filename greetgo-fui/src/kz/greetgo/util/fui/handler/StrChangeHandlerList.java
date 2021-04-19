package kz.greetgo.util.fui.handler;

public class StrChangeHandlerList extends HandlerList<StrChangeHandler> {

  public void fire(String strValue) {
    for (final StrChangeHandler handler : this) {
      handler.changed(strValue);
    }
  }
}
