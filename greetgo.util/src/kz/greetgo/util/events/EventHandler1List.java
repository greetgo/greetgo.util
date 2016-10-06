package kz.greetgo.util.events;

public class EventHandler1List<T> extends AbstractEventHandlerList<EventHandler1<T>>
    implements HasAddEventHandler1<T> {
    
  public void fire(T t) {
    for (EventHandler1<T> handler : this) {
      handler.handle(t);
    }
  }
  
}
