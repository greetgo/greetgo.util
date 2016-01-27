package kz.greetgo.util.events;

public interface HasAddEventHandler {
  HandlerKiller addEventHandler(EventHandler handler);
}
