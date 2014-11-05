package kz.greetgo.gbatis.util.impl;

import java.util.Date;
import java.util.List;

import kz.greetgo.gbatis.util.iface.Creator;
import kz.greetgo.gbatis.util.iface.UtilRegister;

import org.springframework.jdbc.core.JdbcTemplate;

public abstract class UtilRegisterImpl implements UtilRegister {
  
  protected abstract JdbcTemplate jdbc();
  
  @Override
  public void cleanTables(String... tableName) {
    // TODO Auto-generated method stub
    
  }
  
  @Override
  public <T> T getField(Class<T> cl, String tableName, String gettingField,
      Object... fieldValuePairs) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public String getStrField(String tableName, String gettingField, Object... fieldValuePairs) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public Long getLongField(String tableName, String gettingField, Object... fieldValuePairs) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public Integer getIntField(String tableName, String gettingField, Object... fieldValuePairs) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public Date getTimeField(String tableName, String gettingField, Object... fieldValuePairs) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public Double getDoubleField(String tableName, String gettingField, Object... fieldValuePairs) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public boolean getBoolField(String tableName, String gettingField, Object... fieldValuePairs) {
    // TODO Auto-generated method stub
    return false;
  }
  
  @Override
  public <T> T insert(String tableName, T object) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public int update(String tableName, Object object) {
    // TODO Auto-generated method stub
    return 0;
  }
  
  @Override
  public int deleteWhere(String tableName, String where, Object... values) {
    // TODO Auto-generated method stub
    return 0;
  }
  
  @Override
  public int countWhere(String tableName, String where, Object... values) {
    // TODO Auto-generated method stub
    return 0;
  }
  
  @Override
  public boolean existsKey(String tableName, Object object) {
    // TODO Auto-generated method stub
    return false;
  }
  
  @Override
  public <T> List<T> selectList(Class<T> cl, CharSequence sql, List<Object> params) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public <T> List<T> selectList(Creator<T> creator, CharSequence sql, List<Object> params) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public <T> List<T> seleList(Class<T> cl, CharSequence sql, Object... params) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public <T> List<T> seleList(Creator<T> cl, CharSequence sql, Object... params) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public <T> List<T> selectList(Class<T> cl, CharSequence sql, List<Object> params, int offset,
      int count) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public <T> List<T> selectList(Creator<T> creator, CharSequence sql, List<Object> params,
      int offset, int count) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public long selectLong(CharSequence sql, List<Object> params) {
    // TODO Auto-generated method stub
    return 0;
  }
  
  @Override
  public long seleLong(CharSequence sql, Object... params) {
    // TODO Auto-generated method stub
    return 0;
  }
  
  @Override
  public int selectInt(CharSequence sql, List<Object> params) {
    // TODO Auto-generated method stub
    return 0;
  }
  
  @Override
  public int seleInt(CharSequence sql, Object... params) {
    // TODO Auto-generated method stub
    return 0;
  }
  
  @Override
  public <T> T seleOneTo(T toObject, CharSequence sql, Object... params) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public boolean save(String tableName, Object object) {
    // TODO Auto-generated method stub
    return false;
  }
  
  @Override
  public <T> T save(String tableName, boolean insert, T object) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public int execUpdate(CharSequence sql, Object... params) {
    // TODO Auto-generated method stub
    return 0;
  }
  
  @Override
  public int executeUpdate(CharSequence sql, List<Object> params) {
    // TODO Auto-generated method stub
    return 0;
  }
}
