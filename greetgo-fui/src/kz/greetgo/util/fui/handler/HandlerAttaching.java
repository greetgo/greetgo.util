package kz.greetgo.util.fui.handler;

public interface HandlerAttaching<Handler> {
  HandlerDetaching attach(Handler handler);
}
