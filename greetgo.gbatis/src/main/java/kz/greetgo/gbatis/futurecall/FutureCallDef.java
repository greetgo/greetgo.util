package kz.greetgo.gbatis.futurecall;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kz.greetgo.gbatis.model.FutureCall;
import kz.greetgo.gbatis.model.Request;
import kz.greetgo.gbatis.util.ReflectUtil;
import kz.greetgo.gbatis.util.Setter;
import kz.greetgo.gbatis.util.SqlUtil;
import kz.greetgo.sqlmanager.gen.Conf;
import kz.greetgo.sqlmanager.model.Stru;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

public class FutureCallDef<T> implements FutureCall<T> {
  
  private Conf conf;
  private Stru stru;
  private JdbcTemplate jdbc;
  private Request request;
  private Object[] args;
  
  public FutureCallDef(Conf conf, Stru stru, JdbcTemplate jdbc, Request request, Object[] args) {
    this.conf = conf;
    this.stru = stru;
    this.jdbc = jdbc;
    this.request = request;
    this.args = args;
  }
  
  @Override
  public T last() {
    return onPaged(null, 0, 0);
  }
  
  @Override
  public T on(Date at) {
    return onPaged(at, 0, 0);
  }
  
  @Override
  public T lastPaged(final int offset, final int pageSize) {
    return onPaged(null, offset, pageSize);
  }
  
  @Override
  public T onPaged(final Date at, final int offset, final int pageSize) {
    return jdbc.execute(new ConnectionCallback<T>() {
      @Override
      public T doInConnection(Connection con) throws SQLException, DataAccessException {
        try {
          return onPagedWithConnection(con, at, offset, pageSize);
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
    });
  }
  
  private T onPagedWithConnection(Connection con, Date at, int offset, int pageSize)
      throws Exception {
    
    PreparedSql preparedSql = PreparedSql.prepare(conf, stru, request, args, at,
        SqlUtil.defineDbType(con), offset, pageSize);
    
    return callPrepared(con, preparedSql);
  }
  
  private T callPrepared(Connection con, PreparedSql preparedSql) throws Exception {
    switch (request.type) {
    case CALL_FUNCTION:
      return callFunction(con, preparedSql);
      
    case SELECT:
      return callSelect(con, preparedSql);
      
    default:
      throw new IllegalArgumentException("Unknown request type = " + request.type);
    }
  }
  
  private T callSelect(Connection con, PreparedSql preparedSql) throws Exception {
    PreparedStatement ps = con.prepareStatement(preparedSql.sql);
    try {
      
      {
        int index = 1;
        for (Object param : preparedSql.params) {
          ps.setObject(index++, param);
        }
      }
      
      ResultSet rs = ps.executeQuery();
      try {
        
        return assemble(rs);
        
      } finally {
        rs.close();
      }
      
    } finally {
      ps.close();
    }
  }
  
  private T callFunction(Connection con, PreparedSql preparedSql) throws Exception {
    
    CallableStatement cs = con.prepareCall(preparedSql.sql);
    try {
      
      {
        int index = 1;
        for (Object param : preparedSql.params) {
          cs.setObject(index++, param);
        }
      }
      
      cs.execute();
      
    } finally {
      cs.close();
    }
    
    return null;
  }
  
  private T assemble(ResultSet rs) throws Exception {
    
    switch (request.resultType) {
    case SIMPLE:
      return assembleSimple(rs);
      
    case LIST:
      return assembleList(rs);
      
    case MAP:
      return assembleMap(rs);
      
    default:
      throw new IllegalArgumentException("Unknown request.resultType = " + request.resultType);
    }
    
  }
  
  @SuppressWarnings("unchecked")
  private T assembleMap(ResultSet rs) throws Exception {
    Map<Object, Object> ret = new HashMap<>();
    while (rs.next()) {
      Object object = request.newResultRowInstance();
      copyRow(rs, object);
      Object key = SqlUtil.fromSql(rs.getObject(request.mapKeyField), request.mapKeyClass);
      ret.put(key, object);
    }
    return (T)ret;
  }
  
  @SuppressWarnings("unchecked")
  private T assembleList(ResultSet rs) throws Exception {
    List<Object> ret = new ArrayList<>();
    while (rs.next()) {
      Object object = request.newResultRowInstance();
      copyRow(rs, object);
      ret.add(object);
    }
    return (T)ret;
  }
  
  private void copyRow(ResultSet rs, Object object) throws SQLException, IllegalAccessException {
    for (Setter setter : ReflectUtil.scanSetters(object).values()) {
      if (hasColumn(rs, setter.name())) {
        setter.set(SqlUtil.fromSql(rs.getObject(setter.name()), setter.type()));
      }
    }
  }
  
  private static boolean hasColumn(ResultSet rs, String name) throws SQLException {
    try {
      rs.findColumn(name);
      return true;
    } catch (SQLException e) {
      return false;
    }
  }
  
  @SuppressWarnings("unchecked")
  private T assembleSimple(ResultSet rs) throws Exception {
    if (!rs.next()) return null;
    Object object = request.newResultRowInstance();
    copyRow(rs, object);
    return (T)object;
  }
  
}
