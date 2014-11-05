package kz.greetgo.gbatis.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kz.greetgo.gbatis.futurecall.SqlViewer;
import kz.greetgo.gbatis.model.Result;
import kz.greetgo.gbatis.model.SqlWithParams;

public class OperUtil {
  
  public static boolean hasColumn(ResultSet rs, String name, Map<String, Boolean> hasColumnCache)
      throws SQLException {
    if (hasColumnCache != null) {
      Boolean ret = hasColumnCache.get(name);
      if (ret != null) return ret.booleanValue();
    }
    try {
      rs.findColumn(name);
      if (hasColumnCache != null) hasColumnCache.put(name, true);
      return true;
    } catch (SQLException e) {
      if (hasColumnCache != null) hasColumnCache.put(name, false);
      return false;
    }
  }
  
  public static String toUnderScore(String str) {
    StringBuilder sb = new StringBuilder();
    boolean wasLowercased = false;
    for (int i = 0, C = str.length(); i < C; i++) {
      char c = str.charAt(i);
      
      if (Character.isLowerCase(c)) {
        wasLowercased = true;
        sb.append(c);
        continue;
      }
      if (Character.isUpperCase(c) && wasLowercased) {
        sb.append('_');
        sb.append(Character.toLowerCase(c));
        wasLowercased = false;
        continue;
      }
      wasLowercased = false;
      
      sb.append(c);
    }
    return sb.toString();
  }
  
  public static void copyRow(ResultSet rs, Object object, Map<String, Boolean> hasColumnCache)
      throws SQLException, IllegalAccessException {
    for (Setter setter : ReflectUtil.scanSetters(object).values()) {
      
      {
        String name = toUnderScore(setter.name());
        if (hasColumn(rs, name, hasColumnCache)) {
          setter.set(SqlUtil.fromSql(rs.getObject(name), setter.type()));
          continue;
        }
      }
      
      {
        String name = setter.name();
        if (hasColumn(rs, name, hasColumnCache)) {
          setter.set(SqlUtil.fromSql(rs.getObject(name), setter.type()));
          continue;
        }
      }
      
    }
  }
  
  public static final Set<Class<?>> SIMPLE_CLASSES = new HashSet<>();
  static {
    SIMPLE_CLASSES.add(Boolean.class);
    SIMPLE_CLASSES.add(Integer.class);
    SIMPLE_CLASSES.add(Character.class);
    SIMPLE_CLASSES.add(Byte.class);
    SIMPLE_CLASSES.add(Short.class);
    SIMPLE_CLASSES.add(Double.class);
    SIMPLE_CLASSES.add(Long.class);
    SIMPLE_CLASSES.add(Float.class);
    SIMPLE_CLASSES.add(Double.class);
    SIMPLE_CLASSES.add(BigDecimal.class);
    SIMPLE_CLASSES.add(BigInteger.class);
  }
  
  public static boolean isSimpleClass(Class<?> cl) {
    if (cl.isPrimitive()) return true;
    if (cl.isEnum()) return true;
    if (cl.isAssignableFrom(String.class)) return true;
    if (cl.isAssignableFrom(Date.class)) return true;
    return SIMPLE_CLASSES.contains(cl);
  }
  
  public static Object createResultRowFromRS(ResultSet rs, Map<String, Boolean> hasColumnCache,
      Class<?> resultDataClass) throws Exception {
    if (isSimpleClass(resultDataClass)) {
      return SqlUtil.fromSql(rs.getObject(1), resultDataClass);
    }
    
    {
      Object object = resultDataClass.newInstance();
      copyRow(rs, object, hasColumnCache);
      return object;
    }
  }
  
  private static final String SQLERROR = "SQLERROR";
  
  private static <T> T callModi(Connection con, SqlWithParams sql, Result result) throws Exception {
    String err = SQLERROR;
    long startedAt = System.currentTimeMillis();
    PreparedStatement ps = con.prepareStatement(sql.sql);
    
    try {
      
      {
        int index = 1;
        for (Object param : sql.params) {
          ps.setObject(index++, param);
        }
      }
      
      T ret = castInt(ps.executeUpdate(), result.resultDataClass);
      err = null;
      return ret;
      
    } finally {
      ps.close();
      
      view(result.sqlViewer, err, sql, startedAt);
    }
  }
  
  private static void view(SqlViewer sqlViewer, String err, SqlWithParams sql, long startedAt) {
    if (sqlViewer == null) return;
    
    try {
      long delay = System.currentTimeMillis() - startedAt;
      if (!sqlViewer.needView()) return;
      if (err == null) {
        sqlViewer.view(sql.sql, sql.params, delay);
      } else {
        sqlViewer.view(err + ' ' + sql.sql, sql.params, delay);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @SuppressWarnings("unchecked")
  private static <T> T castInt(int value, Class<?> returnClass) {
    if (Integer.class.equals(returnClass) || Integer.TYPE.equals(returnClass)) {
      return (T)new Integer(value);
    }
    if (Long.class.equals(returnClass) || Long.TYPE.equals(returnClass)) {
      return (T)new Long(value);
    }
    if (Void.class.equals(returnClass) || Void.TYPE.equals(returnClass)) {
      return null;
    }
    throw new IllegalArgumentException("Cannot use type " + returnClass + " in @Modi");
  }
  
  private static <T> T callSelect(Connection con, SqlWithParams sql, Result result)
      throws Exception {
    String err = SQLERROR;
    long startedAt = System.currentTimeMillis();
    
    PreparedStatement ps = con.prepareStatement(sql.sql);
    
    try {
      
      {
        int index = 1;
        for (Object param : sql.params) {
          ps.setObject(index++, param);
        }
      }
      
      ResultSet rs = ps.executeQuery();
      try {
        
        T ret = assemble(rs, result);
        err = null;
        return ret;
        
      } finally {
        rs.close();
        
        view(result.sqlViewer, err, sql, startedAt);
      }
      
    } finally {
      ps.close();
    }
  }
  
  private static <T> T callFunction(Connection con, SqlWithParams sql, Result result)
      throws Exception {
    String err = SQLERROR;
    long startedAt = System.currentTimeMillis();
    
    CallableStatement cs = con.prepareCall(sql.sql);
    try {
      
      {
        int index = 1;
        for (Object param : sql.params) {
          cs.setObject(index++, param);
        }
      }
      
      cs.execute();
      err = null;
      
    } finally {
      cs.close();
      
      view(result.sqlViewer, err, sql, startedAt);
    }
    
    return null;
  }
  
  private static <T> T assemble(ResultSet rs, Result result) throws Exception {
    
    switch (result.type) {
    case SIMPLE:
      return assembleSimple(rs, result);
      
    case LIST:
      return assembleList(rs, result);
      
    case SET:
      return assembleSet(rs, result);
      
    case MAP:
      return assembleMap(rs, result);
      
    default:
      throw new IllegalArgumentException("Unknown request.resultType = " + result.type);
    }
    
  }
  
  @SuppressWarnings("unchecked")
  private static <T> T assembleMap(ResultSet rs, Result result) throws Exception {
    Map<Object, Object> ret = new HashMap<>();
    Map<String, Boolean> hasColumnCache = new HashMap<>();
    while (rs.next()) {
      Object object = createResultRowFromRS(rs, hasColumnCache, result.resultDataClass);
      Object key = SqlUtil.fromSql(rs.getObject(result.mapKeyField), result.mapKeyClass);
      ret.put(key, object);
    }
    return (T)ret;
  }
  
  @SuppressWarnings("unchecked")
  private static <T> T assembleList(ResultSet rs, Result result) throws Exception {
    List<Object> ret = new ArrayList<>();
    Map<String, Boolean> hasColumnCache = new HashMap<>();
    while (rs.next()) {
      ret.add(createResultRowFromRS(rs, hasColumnCache, result.resultDataClass));
    }
    return (T)ret;
  }
  
  @SuppressWarnings("unchecked")
  private static <T> T assembleSet(ResultSet rs, Result result) throws Exception {
    Set<Object> ret = new HashSet<>();
    Map<String, Boolean> hasColumnCache = new HashMap<>();
    while (rs.next()) {
      ret.add(createResultRowFromRS(rs, hasColumnCache, result.resultDataClass));
    }
    return (T)ret;
  }
  
  @SuppressWarnings("unchecked")
  private static <T> T assembleSimple(ResultSet rs, Result result) throws Exception {
    if (!rs.next()) {
      if (Boolean.class.equals(result.resultDataClass)) return (T)Boolean.FALSE;
      if (Boolean.TYPE.equals(result.resultDataClass)) return (T)Boolean.FALSE;
      return null;
    }
    return (T)OperUtil.createResultRowFromRS(rs, null, result.resultDataClass);
  }
  
  public static <T> T call(Connection con, SqlWithParams sql, Result result) throws Exception {
    switch (sql.type) {
    case Call:
      return callFunction(con, sql, result);
      
    case Sele:
      return callSelect(con, sql, result);
      
    case Modi:
      return callModi(con, sql, result);
      
    default:
      throw new IllegalArgumentException("Unknown request type = " + sql.type);
    }
  }
}
