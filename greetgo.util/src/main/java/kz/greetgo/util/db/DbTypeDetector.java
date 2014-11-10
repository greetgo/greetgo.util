package kz.greetgo.util.db;

import java.sql.Connection;
import java.sql.SQLException;

public class DbTypeDetector {
  public static DbType detect(Connection con) throws SQLException {
    String db = con.getMetaData().getDatabaseProductName().toLowerCase();
    if ("oracle".equals(db)) return DbType.Oracle;
    if ("postgresql".equals(db)) return DbType.PostgreSQL;
    throw new IllegalArgumentException("Unknown connection db type");
  }
}
