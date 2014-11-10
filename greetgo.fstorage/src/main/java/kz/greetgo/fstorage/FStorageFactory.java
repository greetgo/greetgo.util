package kz.greetgo.fstorage;

import javax.sql.DataSource;

import kz.greetgo.fstorage.impl.AbstractFStorage;
import kz.greetgo.fstorage.impl.oracle.FStorageOracle;
import kz.greetgo.fstorage.impl.postgres.FStoragePostgres;

public class FStorageFactory {
  private int fieldFilenameLen = 300;
  private DataSource dataSource = null;
  private String tableName = null;
  private int tableCount = 0;
  
  public int getFieldFilenameLen() {
    return fieldFilenameLen;
  }
  
  public void setFieldFilenameLen(int fieldFilenameLen) {
    this.fieldFilenameLen = fieldFilenameLen;
  }
  
  public DataSource getDataSource() {
    return dataSource;
  }
  
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }
  
  public String getTableName() {
    return tableName;
  }
  
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }
  
  public int getTableCount() {
    return tableCount;
  }
  
  public void setTableCount(int tableCount) {
    this.tableCount = tableCount;
  }
  
  public FStorage createForPostgres() {
    check();
    return prepare(new FStoragePostgres(dataSource, tableName, tableCount));
  }
  
  public FStorage createForOracle() {
    check();
    return prepare(new FStorageOracle(dataSource, tableName, tableCount));
  }
  
  private AbstractFStorage prepare(AbstractFStorage ret) {
    ret.fieldFilenameLen = fieldFilenameLen;
    return ret;
  }
  
  private void check() {
    if (dataSource == null) throw new UnsetProperty("dataSource");
    if (tableName == null) throw new UnsetProperty("tableName");
    if (tableCount <= 0) throw new UnsetProperty("tableCount");
  }
}
