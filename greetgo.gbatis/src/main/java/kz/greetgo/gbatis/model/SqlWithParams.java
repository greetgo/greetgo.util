package kz.greetgo.gbatis.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SqlWithParams {
  public RequestType type;
  public String sql;
  public final List<Object> params = new ArrayList<>();
  
  public static SqlWithParams select(String sql, Object... params) {
    SqlWithParams ret = new SqlWithParams();
    ret.type = RequestType.Sele;
    ret.sql = sql;
    for (Object p : params) {
      ret.params.add(p);
    }
    return ret;
  }
  
  public static SqlWithParams select(String sql, Collection<Object> params) {
    SqlWithParams ret = new SqlWithParams();
    ret.type = RequestType.Sele;
    ret.sql = sql;
    ret.params.addAll(params);
    return ret;
  }
  
  public static SqlWithParams call(String sql, Object... params) {
    SqlWithParams ret = new SqlWithParams();
    ret.type = RequestType.Call;
    ret.sql = sql;
    for (Object p : params) {
      ret.params.add(p);
    }
    return ret;
  }
  
  public static SqlWithParams call(String sql, Collection<Object> params) {
    SqlWithParams ret = new SqlWithParams();
    ret.type = RequestType.Call;
    ret.sql = sql;
    ret.params.addAll(params);
    return ret;
  }
  
  public static SqlWithParams modi(String sql, Object... params) {
    SqlWithParams ret = new SqlWithParams();
    ret.type = RequestType.Modi;
    ret.sql = sql;
    for (Object p : params) {
      ret.params.add(p);
    }
    return ret;
  }
  
  public static SqlWithParams modi(String sql, Collection<Object> params) {
    SqlWithParams ret = new SqlWithParams();
    ret.type = RequestType.Modi;
    ret.sql = sql;
    ret.params.addAll(params);
    return ret;
  }
}
