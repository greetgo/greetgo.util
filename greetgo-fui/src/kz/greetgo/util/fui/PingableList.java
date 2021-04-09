package kz.greetgo.util.fui;

import java.util.ArrayList;
import java.util.List;

public class PingableList {
  private final List<Pingable> list = new ArrayList<>();

  private final Object sync = new Object();

  public void add(Pingable pingable) {
    synchronized (sync) {
      list.add(pingable);
    }
  }

  public List<Pingable> list() {
    List<Pingable> ret = new ArrayList<>();
    synchronized (sync) {
      //noinspection CollectionAddAllCanBeReplacedWithConstructor
      ret.addAll(list);
    }
    return ret;
  }
}
