package kz.greetgo.fstorage.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import kz.greetgo.fstorage.FStorage;
import kz.greetgo.fstorage.FileDot;
import kz.greetgo.util.ServerUtil;

public abstract class AbstractFStorage implements FStorage {
  protected final DataSource dataSource;
  protected final String tableName;
  protected final int tableCount;
  
  public int fieldFilenameLen = 300;
  
  public AbstractFStorage(DataSource dataSource, String tableName, int tableCount) {
    this.dataSource = dataSource;
    this.tableName = tableName;
    this.tableCount = tableCount;
  }
  
  private String table(long id) {
    int size = 0;
    {
      int a = tableCount;
      while (a > 0) {
        size++;
        a = a / 10;
      }
    }
    String nom = "" + (id % tableCount);
    while (nom.length() < size) {
      nom = "0" + nom;
    }
    return tableName + tableCount + '_' + nom;
  }
  
  @Override
  public long addNewFile(FileDot fileDot) {
    try {
      return addNewFileInner(fileDot);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  private long addNewFileInner(FileDot fileDot) throws Exception {
    Connection con = dataSource.getConnection();
    try {
      return addNewFileCon(con, fileDot);
    } finally {
      con.close();
    }
  }
  
  protected abstract String nextIdSql(String sequenceName);
  
  private long nextId(Connection con) throws Exception {
    PreparedStatement ps = con.prepareStatement(nextIdSql(tableName + "_seq"));
    try {
      ResultSet rs = ps.executeQuery();
      try {
        rs.next();
        return rs.getLong(1);
      } finally {
        rs.close();
      }
    } finally {
      ps.close();
    }
  }
  
  @Override
  public FileDot getFile(long fileId) {
    try {
      return getFileInner(fileId);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  private void init(Connection con) throws Exception {
    if (!existsTable(con)) createTable(con);
  }
  
  private String sql(String name) {
    return ServerUtil.streamToStr(getClass().getResourceAsStream(name + ".sql"));
  }
  
  private boolean existsTable(Connection con) throws SQLException {
    PreparedStatement ps = con.prepareStatement(sql("exists_table"));
    try {
      ps.setString(1, table(0));
      ResultSet rs = ps.executeQuery();
      try {
        rs.next();
        return rs.getInt(1) > 0;
      } finally {
        rs.close();
      }
    } finally {
      ps.close();
    }
  }
  
  protected abstract String fieldTypeId();
  
  protected abstract String fieldTypeFilename();
  
  protected abstract String fieldTypeData();
  
  private void createTable(Connection con) throws Exception {
    {
      PreparedStatement ps = con.prepareStatement("create sequence " + tableName + "_seq");
      try {
        ps.executeUpdate();
      } finally {
        ps.close();
      }
    }
    for (int i = 0, C = tableCount; i < C; i++) {
      StringBuilder sql = new StringBuilder();
      sql.append("create table ").append(table(i)).append('(');
      sql.append("  id ").append(fieldTypeId()).append(" not null primary key,");
      sql.append("  filename ").append(fieldTypeFilename()).append(',');
      sql.append("  data ").append(fieldTypeData());
      sql.append(')');
      PreparedStatement ps = con.prepareStatement(sql.toString());
      try {
        ps.executeUpdate();
      } finally {
        ps.close();
      }
    }
  }
  
  private long addNewFileCon(Connection con, FileDot fileDot) throws Exception {
    init(con);
    long ret = nextId(con);
    
    insertFileDot(con, ret, fileDot);
    
    return ret;
  }
  
  private int insertFileDot(Connection con, long id, FileDot fileDot) throws Exception {
    PreparedStatement ps = con.prepareStatement("insert into " + table(id)
        + " (id,filename,data)values(?,?,?)");
    try {
      ps.setLong(1, id);
      ps.setString(2, fileDot.filename);
      ps.setBytes(3, fileDot.data);
      return ps.executeUpdate();
    } finally {
      ps.close();
    }
  }
  
  private FileDot getFileInner(long id) throws Exception {
    Connection con = dataSource.getConnection();
    try {
      return getFileCon(con, id);
    } finally {
      con.close();
    }
  }
  
  private FileDot getFileCon(Connection con, long id) throws Exception {
    PreparedStatement ps = con.prepareStatement("select * from " + table(id) + " where id = ?");
    try {
      ps.setLong(1, id);
      ResultSet rs = ps.executeQuery();
      try {
        if (!rs.next()) return null;
        
        return new FileDot(rs.getString("filename"), rs.getBytes("data"));
      } finally {
        rs.close();
      }
    } finally {
      ps.close();
    }
  }
}
