package kz.greetgo.fstorage.impl.postgres;

import javax.sql.DataSource;

import kz.greetgo.fstorage.impl.AbstractFStorage;

public class FStoragePostgres extends AbstractFStorage {
  
  public FStoragePostgres(DataSource dataSource, String tableName, int tableCount) {
    super(dataSource, tableName, tableCount);
  }
  
  @Override
  protected String nextIdSql(String sequenceName) {
    return "select nextval('" + sequenceName + "')";
  }
  
  @Override
  protected String fieldTypeId() {
    return "int8";
  }
  
  @Override
  protected String fieldTypeFilename() {
    return "varchar(" + fieldFilenameLen + ")";
  }
  
  @Override
  protected String fieldTypeData() {
    return "bytea";
  }
  
}
