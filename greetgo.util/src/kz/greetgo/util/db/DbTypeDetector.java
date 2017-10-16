package kz.greetgo.util.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbTypeDetector {
  public static DbType detect(Connection con) throws SQLException {
    String db = con.getMetaData().getDatabaseProductName().toLowerCase();
    if ("oracle".equals(db)) return DbType.Oracle;
    if ("postgresql".equals(db)) return DbType.PostgreSQL;
    if ("hsql database engine".equals(db)) return DbType.HSQLDB;
    if ("mysql".equals(db)) return DbType.MySQL;
    throw new IllegalArgumentException(
      "Unknown connection db: " + con.getMetaData().getDatabaseProductName());
  }

  public static DbType detect(DataSource dataSource) throws SQLException {
    try (Connection con = dataSource.getConnection()) {
      return detect(con);
    }
  }
}
