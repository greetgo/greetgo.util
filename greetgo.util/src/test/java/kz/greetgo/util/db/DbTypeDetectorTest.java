package kz.greetgo.util.db;

import static kz.greetgo.conf.SysParams.mysqlAdminPassword;
import static kz.greetgo.conf.SysParams.mysqlAdminUrl;
import static kz.greetgo.conf.SysParams.mysqlAdminUserid;
import static kz.greetgo.conf.SysParams.oracleAdminHost;
import static kz.greetgo.conf.SysParams.oracleAdminPassword;
import static kz.greetgo.conf.SysParams.oracleAdminPort;
import static kz.greetgo.conf.SysParams.oracleAdminSid;
import static kz.greetgo.conf.SysParams.oracleAdminUserid;
import static kz.greetgo.conf.SysParams.pgAdminPassword;
import static kz.greetgo.conf.SysParams.pgAdminUrl;
import static kz.greetgo.conf.SysParams.pgAdminUserid;
import static org.fest.assertions.api.Assertions.assertThat;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.util.UUID;

import javax.sql.DataSource;

import org.testng.annotations.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

public class DbTypeDetectorTest {
  
  private PoolDataSource getOraclePool() throws Exception {
    PoolDataSource pool = PoolDataSourceFactory.getPoolDataSource();
    
    pool.setURL("jdbc:oracle:thin:@" + oracleAdminHost() + ":" + oracleAdminPort() + ":"
        + oracleAdminSid());
    pool.setUser(oracleAdminUserid());
    pool.setPassword(oracleAdminPassword());
    
    pool.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
    pool.setConnectionPoolName("test_pool-" + UUID.randomUUID().toString());
    pool.setMinPoolSize(1);
    pool.setMaxPoolSize(2);
    pool.setInitialPoolSize(1);
    pool.setInactiveConnectionTimeout(120);
    pool.setValidateConnectionOnBorrow(true);
    pool.setMaxStatements(10);
    
    return pool;
  }
  
  @Test
  public void detect_OracleDataSource() throws Exception {
    
    PoolDataSource pool = getOraclePool();
    
    DbType dbType = DbTypeDetector.detect(pool);
    
    assertThat(dbType).isEqualTo(DbType.Oracle);
  }
  
  @Test
  public void detect_OracleConnection() throws Exception {
    
    PoolDataSource pool = getOraclePool();
    
    Connection con = pool.getConnection();
    
    DbType dbType = DbTypeDetector.detect(con);
    
    con.close();
    
    assertThat(dbType).isEqualTo(DbType.Oracle);
  }
  
  private DataSource getPostgresDatasource() throws PropertyVetoException {
    ComboPooledDataSource ret = new ComboPooledDataSource();
    ret.setDriverClass("org.postgresql.Driver");
    ret.setJdbcUrl(pgAdminUrl());
    ret.setUser(pgAdminUserid());
    ret.setPassword(pgAdminPassword());
    
    ret.setAcquireIncrement(1);
    ret.setMinPoolSize(1);
    ret.setMaxPoolSize(2);
    ret.setMaxIdleTime(120);
    
    return ret;
  }
  
  private DataSource getMysqlDatasource() throws PropertyVetoException {
    ComboPooledDataSource ret = new ComboPooledDataSource();
    ret.setDriverClass("com.mysql.jdbc.Driver");
    ret.setJdbcUrl(mysqlAdminUrl());
    ret.setUser(mysqlAdminUserid());
    ret.setPassword(mysqlAdminPassword());
    
    ret.setAcquireIncrement(1);
    ret.setMinPoolSize(1);
    ret.setMaxPoolSize(2);
    ret.setMaxIdleTime(120);
    
    return ret;
  }
  
  @Test
  public void detect_PostgresDataSource() throws Exception {
    DataSource ds = getPostgresDatasource();
    
    DbType dbType = DbTypeDetector.detect(ds);
    
    assertThat(dbType).isEqualTo(DbType.PostgreSQL);
  }
  
  @Test
  public void detect_MysqlDataSource() throws Exception {
    DataSource ds = getMysqlDatasource();
    
    DbType dbType = DbTypeDetector.detect(ds);
    
    assertThat(dbType).isEqualTo(DbType.MySQL);
  }
  
  @Test
  public void detect_PostgresConnection() throws Exception {
    DataSource ds = getPostgresDatasource();
    
    Connection con = ds.getConnection();
    DbType dbType = DbTypeDetector.detect(con);
    con.close();
    
    assertThat(dbType).isEqualTo(DbType.PostgreSQL);
  }
  
  @Test
  public void detect_MysqlConnection() throws Exception {
    DataSource ds = getMysqlDatasource();
    
    Connection con = ds.getConnection();
    DbType dbType = DbTypeDetector.detect(con);
    con.close();
    
    assertThat(dbType).isEqualTo(DbType.MySQL);
  }
  
  private DataSource getHsqlDbDatasource() throws PropertyVetoException {
    ComboPooledDataSource ret = new ComboPooledDataSource();
    
    ret.setDriverClass("org.hsqldb.jdbcDriver");
    ret.setJdbcUrl("jdbc:hsqldb:mem:test;DB_CLOSE_DELAY=-1");
    ret.setUser("sa");
    ret.setPassword("");
    
    ret.setAcquireIncrement(1);
    ret.setMinPoolSize(1);
    ret.setMaxPoolSize(2);
    ret.setMaxIdleTime(120);
    
    return ret;
  }
  
  @Test
  public void detect_hsqldbDataSource() throws Exception {
    DataSource ds = getHsqlDbDatasource();
    DbType dbType = DbTypeDetector.detect(ds);
    
    assertThat(dbType).isEqualTo(DbType.HSQLDB);
  }
  
  @Test
  public void detect_hsqldbConnection() throws Exception {
    DataSource ds = getHsqlDbDatasource();
    
    Connection con = ds.getConnection();
    DbType dbType = DbTypeDetector.detect(con);
    con.close();
    
    assertThat(dbType).isEqualTo(DbType.HSQLDB);
  }
}
