package kz.greetgo.util.fui;

import kz.greetgo.util.fui.handler.HandlerAttaching;
import kz.greetgo.util.fui.handler.StrChangeHandler;

public interface StrAccessor extends HandlerAttaching<StrChangeHandler> {
  String get();

  void set(String value);
}
