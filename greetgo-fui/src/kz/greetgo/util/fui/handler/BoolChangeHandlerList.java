package kz.greetgo.util.fui.handler;

public class BoolChangeHandlerList extends HandlerList<BoolChangeHandler> {
  public void fire(boolean boolValue) {
    for (final BoolChangeHandler handler : this) {
      handler.changed(boolValue);
    }
  }
}
