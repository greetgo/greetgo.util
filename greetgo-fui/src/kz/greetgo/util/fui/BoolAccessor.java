package kz.greetgo.util.fui;

import kz.greetgo.util.fui.handler.BoolChangeHandler;
import kz.greetgo.util.fui.handler.HandlerDetaching;

public interface BoolAccessor {
  boolean is();

  void set(boolean flag);

  HandlerDetaching attachChangeHandler(BoolChangeHandler handler);
}
