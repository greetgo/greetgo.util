package kz.greetgo.watcher.tracer;

public interface Trace {
  void fatal(String message);
  
  void error(String message);
  
  void warning(String message);
  
  void info(String message);
  
  void debug(String message);
}
