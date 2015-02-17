package kz.greetgo.watcher.ds;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

@Deprecated
public class ConnectionWatcher {
  
  private final LogMessageAcceptor log;
  private final StatementWatcher statementWatcher;
  
  public ConnectionWatcher(LogMessageAcceptor log) {
    this.log = log;
    statementWatcher = new StatementWatcher(log);
  }
  
  public Connection wrap(final Connection object) {
    
    InvocationHandler h = new InvocationHandler() {
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return invokeObject(object, method, args);
      }
    };
    
    return (Connection)Proxy.newProxyInstance(object.getClass().getClassLoader(),
        new Class<?>[] { Connection.class }, h);
  }
  
  private final String TID = "CONNECTION";
  
  private Object invokeObject(Connection object, Method method, Object[] args) throws Throwable {
    if ("close".equals(method.getName())) {
      log.acceptInfo(TID + " Close");
      return method.invoke(object, args);
    }
    if ("commit".equals(method.getName())) {
      log.acceptInfo(TID + " Commit");
      return method.invoke(object, args);
    }
    if ("setAutoCommit".equals(method.getName()) && args.length >= 1) {
      log.acceptInfo(TID + " SetAutoCommit " + args[0]);
      return method.invoke(object, args);
    }
    
    /*
    if ("getAutoCommit".equals(method.getName())) {
      Object ret = method.invoke(object, args);
      log.acceptInfo(TID + " GetAutoCommit returns " + ret);
      return ret;
    }
    if ("isClosed".equals(method.getName())) {
      Object ret = method.invoke(object, args);
      log.acceptInfo(TID + " IsClosed returns " + ret);
      return ret;
    }
    if ("isReadOnly".equals(method.getName())) {
      Object ret = method.invoke(object, args);
      log.acceptInfo(TID + " IsReadOnly returns " + ret);
      return ret;
    }
    */
    
    if ("prepareStatement".equals(method.getName()) && args.length == 1) {
      log.acceptInfo(TID + " PrepareStatement " + args[0]);
      return statementWatcher.wrap(method.invoke(object, args));
    }
    {
      log.acceptCommon(TID + " InvokingMethod " + method);
      return method.invoke(object, args);
    }
  }
  
}
