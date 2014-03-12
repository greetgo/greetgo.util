package kz.greetgo.gbatis.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kz.greetgo.gbatis.util.ReflectUtil;
import kz.greetgo.gbatis.util.Setter;
import kz.greetgo.gbatis.util.SqlUtil;

public class Request {
  public String sql;
  public RequestType type;
  public final List<WithView> withList = new ArrayList<>();
  public final List<Param> paramList = new ArrayList<>();
  
  public ResultType resultType;
  public Class<?> resultDataClass;
  public boolean callNow;
  public String mapKeyField;
  public Class<?> mapKeyClass;
  
  private static void copyRow(ResultSet rs, Object object) throws SQLException,
      IllegalAccessException {
    for (Setter setter : ReflectUtil.scanSetters(object).values()) {
      
      {
        String name = toUnderScore(setter.name());
        if (hasColumn(rs, name)) {
          setter.set(SqlUtil.fromSql(rs.getObject(name), setter.type()));
          continue;
        }
      }
      
      {
        String name = setter.name();
        if (hasColumn(rs, name)) {
          setter.set(SqlUtil.fromSql(rs.getObject(name), setter.type()));
          continue;
        }
      }
      
    }
  }
  
  private static String toUnderScore(String str) {
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
  
  private static boolean hasColumn(ResultSet rs, String name) throws SQLException {
    try {
      rs.findColumn(name);
      return true;
    } catch (SQLException e) {
      return false;
    }
  }
  
  public Object createResultRowFromRS(ResultSet rs) throws Exception {
    if (isSimpleClass(resultDataClass)) {
      return SqlUtil.fromSql(rs.getObject(1), resultDataClass);
    }
    
    {
      Object object = resultDataClass.newInstance();
      copyRow(rs, object);
      return object;
    }
  }
  
  private static final Set<Class<?>> SIMPLE_CLASSES = new HashSet<>();
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
  
  private static boolean isSimpleClass(Class<?> cl) {
    if (cl.isPrimitive()) return true;
    if (cl.isEnum()) return true;
    if (cl.isAssignableFrom(String.class)) return true;
    return SIMPLE_CLASSES.contains(cl);
  }
}
