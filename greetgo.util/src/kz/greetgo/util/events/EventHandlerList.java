package kz.greetgo.util.events;

public class EventHandlerList extends AbstractEventHandlerList<EventHandler>
  implements HasAddEventHandler {

  public void fire() {
    for (EventHandler handler : this) {
      handler.handle();
    }
  }

}
