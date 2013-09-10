package kz.greetgo.watcher.ds;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class StatementWatcher {
  
  private final LogMessageAcceptor log;
  
  public StatementWatcher(LogMessageAcceptor log) {
    this.log = log;
  }
  
  public Object wrap(Object object) {
    if (object == null) return null;
    
    if (object instanceof CallableStatement) {
      return wrapInner(CallableStatement.class, object);
    }
    
    if (object instanceof PreparedStatement) {
      return wrapInner(PreparedStatement.class, object);
    }
    
    if (object instanceof Statement) {
      return wrapInner(Statement.class, object);
    }
    
    return object;
  }
  
  private Object wrapInner(Class<?> iface, final Object object) {
    InvocationHandler h = new InvocationHandler() {
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return invokeObject(proxy, object, method, args);
      }
    };
    
    return Proxy.newProxyInstance(object.getClass().getClassLoader(), new Class<?>[] { iface }, h);
  }
  
  private final String TID = "STATEMENT";
  
  private Object invokeObject(Object proxy, Object object, Method method, Object[] args)
      throws Throwable {
    
    if (method.getName().startsWith("set") && args.length == 2) {
      log.acceptInfo2(TID + " " + method.getName() + " " + args[0] + ", " + args[1]);
      return method.invoke(object, args);
    }
    
    if (method.getName().startsWith("get") && args.length == 1) {
      Object ret = method.invoke(object, args);
      log.acceptInfo2(TID + " " + method.getName() + " " + args[0] + ", returns = " + ret);
      return ret;
    }
    
    if ("close".equals(method.getName())) {
      log.acceptInfo(TID + " Close");
      return method.invoke(object, args);
    }
    
    if ("executeQuery".equals(method.getName())) {
      log.acceptInfo2(TID + " ExecuteQuery"
          + ((args != null && args.length > 0) ? ": " + args[0] :""));
      return wrapRS(method.invoke(object, args));
    }
    if ("execute".equals(method.getName())) {
      log.acceptInfo2(TID + " Execute" + ((args != null && args.length > 0) ? ": " + args[0] :""));
      return method.invoke(object, args);
    }
    if ("addBatch".equals(method.getName())) {
      log.acceptInfo2(TID + " AddBatch");
      return method.invoke(object, args);
    }
    if ("executeBatch".equals(method.getName())) {
      Object ret = method.invoke(object, args);
      if (ret instanceof int[]) {
        StringBuilder sb = new StringBuilder();
        for (int i : (int[])ret) {
          sb.append(i).append(',');
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 1);
        log.acceptInfo(TID + " ExecuteBatch returns [" + sb.toString() + "]");
      } else {
        log.acceptInfo(TID + " ExecuteBatch returns " + ret);
      }
      return ret;
    }
    if ("executeUpdate".equals(method.getName())) {
      Object ret = method.invoke(object, args);
      log.acceptInfo(TID + " ExecuteUpdate returns = " + ret);
      return ret;
    }
    
    outCommonMessage(object, method, args);
    return wrapRS(method.invoke(object, args));
  }
  
  private Object wrapRS(final Object rs) {
    if (!(rs instanceof ResultSet)) return rs;
    
    log.acceptInfo("RESULT_SET Open");
    return Proxy.newProxyInstance(rs.getClass().getClassLoader(),
        new Class<?>[] { ResultSet.class }, new InvocationHandler() {
          @Override
          public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("close".equals(method.getName())) {
              log.acceptInfo("RESULT_SET Close");
              return method.invoke(rs, args);
            }
            return method.invoke(rs, args);
          }
        });
    
  }
  
  private void outCommonMessage(Object object, Method method, Object[] args) {
    log.acceptCommon(TID + " Invoke method " + method);
  }
}
