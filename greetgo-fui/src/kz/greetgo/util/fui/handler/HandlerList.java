package kz.greetgo.util.fui.handler;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class HandlerList<Handler> implements Iterable<Handler>, HandlerAttaching<Handler> {

  private final ConcurrentHashMap<Long, Handler> map    = new ConcurrentHashMap<>();
  private final AtomicLong                       nextId = new AtomicLong(1);

  @Override
  public HandlerDetaching attach(Handler handler) {
    if (handler == null) {
      return () -> {};
    }
    var id = nextId.getAndIncrement();
    map.put(id, handler);
    return () -> map.remove(id);
  }

  @Override
  public Iterator<Handler> iterator() {
    return map.values().iterator();
  }
}
