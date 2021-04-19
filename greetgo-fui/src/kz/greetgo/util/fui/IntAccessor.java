package kz.greetgo.util.fui;

import kz.greetgo.util.fui.handler.HandlerDetaching;
import kz.greetgo.util.fui.handler.IntChangeHandler;

public interface IntAccessor {
  Integer get();

  int getInt();

  void set(Integer value);

  HandlerDetaching attachChangeHandler(IntChangeHandler handler);
}
