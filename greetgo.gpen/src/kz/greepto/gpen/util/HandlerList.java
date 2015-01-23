package kz.greepto.gpen.util;

public class HandlerList extends HandlerListInstrument<Handler> {
  public void fire() {
    for (Handler h : this) {
      h.handle();
    }
  }
}
