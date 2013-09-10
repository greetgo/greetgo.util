package kz.greetgo.watcher.trace;

public interface MessageOuter {
  void outMessage(String message);
  
  void close();
}
