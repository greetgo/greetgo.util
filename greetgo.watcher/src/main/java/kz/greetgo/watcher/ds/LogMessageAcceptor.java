package kz.greetgo.watcher.ds;

@Deprecated
public interface LogMessageAcceptor {
  void acceptInfo(String logMessage);
  
  void acceptInfo2(String logMessage);
  
  void acceptCommon(String logMessage);
}
