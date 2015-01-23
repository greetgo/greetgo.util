package kz.greepto.gpen.util;

import java.util.Iterator;

public class HandlerListInstrument<H> implements Iterable<H> {
  private Dot last = null;
  
  private class Dot {
    H h;
    Dot prev, next;
  }
  
  public HandlerKiller add(H h) {
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
    return new HandlerKiller() {
      @Override
      public void kill() {
        if (dot.next == null) return;
        
        if (dot.next == dot) {
          last = null;
          dot.next = dot.prev = null;
          return;
        }
        
        dot.next.prev = dot.prev;
        dot.prev.next = dot.next;
        if (last == dot) last = dot.prev;
        
        dot.next = dot.prev = null;
      }
    };
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
      
      @Override
      public H next() {
        if (cur == null) throw new NullPointerException("Nothing to return");
        H ret = cur.h;
        cur = cur.next;
        justStarted = false;
        return ret;
      }
      
      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
}
