package kz.greetgo.util.events;

public interface HasAddEventHandler1<T> {
  HandlerKiller addEventHandler(EventHandler1<T> handler);
}
