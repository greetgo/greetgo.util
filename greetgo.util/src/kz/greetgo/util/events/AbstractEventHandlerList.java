package kz.greetgo.util.events;

import java.util.Iterator;

public abstract class AbstractEventHandlerList<H> implements Iterable<H> {
  private Dot last = null;
  
  private class Dot implements HandlerKiller {
    H h;
    Dot prev, next;
    
    @Override
    public void kill() {
      if (next == null) return;
      
      if (next == this) {
        last = null;
        next = prev = null;
        return;
      }
      
      next.prev = prev;
      prev.next = next;
      if (last == this) last = prev;
      
      next = prev = null;
    }
  }
  
  public HandlerKiller addEventHandler(H h) {
    final Dot dot = new Dot();
    dot.h = h;
    if (last == null) {
      last = dot;
      dot.next = dot.prev = dot;
    } else {
      dot.next = last.next;
      last.next.prev = dot;
      last.next = dot;
      dot.prev = last;
      
      last = dot;
    }
    return dot;
  }
  
  @Override
  public Iterator<H> iterator() {
    return new Iterator<H>() {
      Dot cur = last == null ? null :last.next;
      Dot first = cur;
      boolean justStarted = true;
      
      @Override
      public boolean hasNext() {
        if (cur == null) return false;
        if (justStarted) return true;
        return cur != first;
      }
      
      Dot lastNext = null;
      
      @Override
      public H next() {
        if (cur == null) throw new NullPointerException("Nothing to return");
        H ret = cur.h;
        lastNext = cur;
        cur = cur.next;
        justStarted = false;
        return ret;
      }
      
      @Override
      public void remove() {
        if (lastNext != null) lastNext.kill();
      }
    };
  }
}
