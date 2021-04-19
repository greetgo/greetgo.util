package kz.greetgo.util.fui.handler;

public class ButtonClickHandlerList extends HandlerList<ButtonClickHandler> {

  public void fire() {
    for (final ButtonClickHandler handler : this) {
      handler.clicked();
    }
  }

}
