package kz.greetgo.gbatis.util.callbacks;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import kz.greetgo.gbatis.futurecall.SqlViewer;
import kz.greetgo.gbatis.util.model.ForeignKey;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;

public class ReferencesToCallback implements ConnectionCallback<Set<ForeignKey>> {
  public SqlViewer sqlViewer;
  
  @SuppressWarnings("unused")
  private final String tableName;
  
  public ReferencesToCallback(String tableName) {
    this.tableName = tableName;
  }
  
  public ReferencesToCallback(SqlViewer sqlViewer, String tableName) {
    this(tableName);
    this.sqlViewer = sqlViewer;
  }
  
  @Override
  public Set<ForeignKey> doInConnection(Connection con) throws SQLException, DataAccessException {
    // TODO Auto-generated method stub
    return null;
  }
}
