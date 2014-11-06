package kz.greetgo.gbatis.util.impl;

import static org.fest.assertions.api.Assertions.assertThat;

import java.sql.Connection;
import java.util.List;

import kz.greetgo.gbatis.util.callbacks.NoUpdateException;
import kz.greetgo.gbatis.util.test.TestBase;

import org.testng.annotations.Test;

public class AbstractUtilRegisterTest extends TestBase {
  
  @Override
  protected String dbSchema() {
    return "gbatis_util";
  }
  
  @Test(dataProvider = CONNECT_PROVIDER)
  public void cleanTables(Connection con) throws Exception {
    TestingUtilRegister r = new TestingUtilRegister();
    r.setConnection(con);
    r.sqlViewer = stdSqlViewer();
    
    if (queryForce(con, "drop table for_clean_refNull")) {
      query(con, "drop table for_clean_refNotNull");
      query(con, "drop table for_clean");
    }
    
    query(con, "create table for_clean(id int not null primary key)");
    query(con, "create table for_clean_refNull(id int not null primary key,"
        + " ref int references for_clean)");
    query(con, "create table for_clean_refNotNull(id int not null primary key,"
        + " ref int not null references for_clean)");
    
    query(con, "insert into for_clean values (10)");
    query(con, "insert into for_clean values (20)");
    
    query(con, "insert into for_clean_refNull values (10, 10)");
    query(con, "insert into for_clean_refNull values (11, 10)");
    
    query(con, "insert into for_clean_refNotNull values (20, 20)");
    query(con, "insert into for_clean_refNotNull values (21, 20)");
    
    //
    //
    //
    r.cleanTables("for_clean");
    //
    //
    //
    
    assertThat(count(con, "for_clean")).isZero();
    assertThat(count(con, "for_clean_refNotNull")).isZero();
    assertThat(count(con, "for_clean_refNull")).isEqualTo(2);
  }
  
  @Test(dataProvider = CONNECT_PROVIDER)
  public void getField(Connection con) throws Exception {
    TestingUtilRegister r = new TestingUtilRegister();
    r.setConnection(con);
    r.sqlViewer = stdSqlViewer();
    
    queryForce(con, "drop table getField");
    query(con, "create table getField(id1 int, id2 int, intValue int, strValue varchar(10))");
    
    query(con, "insert into getField values (1,2,  234, 'asd')");
    query(con, "insert into getField values (1,3,  432, 'dsa')");
    
    assertThat(r.getField(Integer.class, "getField", "intValue", "id1", 1, "id2", 2))//
        .isEqualTo(234);
    
    assertThat(r.getField(String.class, "getField", "strValue", "id1", 1, "id2", 2))//
        .isEqualTo("asd");
    
    assertThat(r.getField(Integer.class, "getField", "intValue", "id1", 1, "id2", 3))//
        .isEqualTo(432);
    
    assertThat(r.getField(String.class, "getField", "strValue", "id1", 1, "id2", 3))//
        .isEqualTo("dsa");
  }
  
  public static class ForUpdate {
    public int id1, id2, intValue;
    public String strValue;
  }
  
  @Test(dataProvider = CONNECT_PROVIDER)
  public void update(Connection con) throws Exception {
    TestingUtilRegister r = new TestingUtilRegister();
    r.setConnection(con);
    r.sqlViewer = stdSqlViewer();
    
    queryForce(con, "drop table for_update");
    query(con, "create table for_update(id1 int, id2 int, intValue int,"
        + " strValue varchar(10), primary key(id1, id2))");
    
    query(con, "insert into for_update values(1, 2, 123, 'asd')");
    query(con, "insert into for_update values(2, 3, 321, 'dsa')");
    
    ForUpdate u = new ForUpdate();
    u.id1 = 1;
    u.id2 = 2;
    u.intValue = 777;
    u.strValue = "Hello";
    
    //
    //
    //
    r.update("for_update", u);
    //
    //
    //
    
    assertThat(r.getField(Integer.class, "for_update", "intValue", "id1", 1, "id2", 2))//
        .isEqualTo(u.intValue);
    assertThat(r.getField(String.class, "for_update", "strValue", "id1", 1, "id2", 2))//
        .isEqualTo(u.strValue);
    
    assertThat(r.getField(Integer.class, "for_update", "intValue", "id1", 2, "id2", 3))//
        .isEqualTo(321);
    assertThat(r.getField(String.class, "for_update", "strValue", "id1", 2, "id2", 3))//
        .isEqualTo("dsa");
  }
  
  @Test(dataProvider = CONNECT_PROVIDER, expectedExceptions = NoUpdateException.class)
  public void update_exception(Connection con) throws Exception {
    TestingUtilRegister r = new TestingUtilRegister();
    r.setConnection(con);
    r.sqlViewer = stdSqlViewer();
    
    queryForce(con, "drop table for_update");
    query(con, "create table for_update(id1 int, id2 int, intValue int,"
        + " strValue varchar(10), primary key(id1, id2))");
    
    query(con, "insert into for_update values(1, 2, 123, 'asd')");
    
    ForUpdate u = new ForUpdate();
    u.id1 = 111;
    u.id2 = 233;
    u.intValue = 777;
    u.strValue = "Hello";
    
    //
    //
    //
    r.update("for_update", u);
    //
    //
    //
  }
  
  @Test(dataProvider = CONNECT_PROVIDER)
  public void deleteWhere(Connection con) throws Exception {
    TestingUtilRegister r = new TestingUtilRegister();
    r.setConnection(con);
    r.sqlViewer = stdSqlViewer();
    
    queryForce(con, "drop table for_deleteWhere");
    query(con, "create table for_deleteWhere(id1 int, id2 int, value varchar(10))");
    
    query(con, "insert into for_deleteWhere values(1, 2, 'asd')");
    query(con, "insert into for_deleteWhere values(2, 3, 'dsa')");
    
    //
    //
    //
    r.deleteWhere("for_deleteWhere", "id1 = ? and id2 = ?", 1, 2);
    //
    //
    //
    
    assertThat(count(con, "for_deleteWhere", "id1", 1, "id2", 2)).isZero();
    assertThat(count(con, "for_deleteWhere", "id1", 2, "id2", 3)).isEqualTo(1);
  }
  
  @Test(dataProvider = CONNECT_PROVIDER)
  public void countWhere(Connection con) throws Exception {
    TestingUtilRegister r = new TestingUtilRegister();
    r.setConnection(con);
    r.sqlViewer = stdSqlViewer();
    
    queryForce(con, "drop table for_countWhere");
    query(con, "create table for_countWhere(id1 int, id2 int, value varchar(10))");
    
    query(con, "insert into for_countWhere values(1, 2, 'asd1')");
    query(con, "insert into for_countWhere values(1, 2, 'asd2')");
    query(con, "insert into for_countWhere values(2, 3, 'dsa')");
    
    //
    //
    //
    assertThat(r.countWhere("for_countWhere", "id1 = ? and id2 = ?", 1, 2)).isEqualTo(2);
    //
    //
    //
    
  }
  
  public static class ForExistsKey {
    public int id1, id2;
    public String asd;
  }
  
  @Test(dataProvider = CONNECT_PROVIDER)
  public void existsKey(Connection con) throws Exception {
    TestingUtilRegister r = new TestingUtilRegister();
    r.setConnection(con);
    r.sqlViewer = stdSqlViewer();
    
    queryForce(con, "drop table for_existsKey");
    query(con, "create table for_existsKey(id1 int, id2 int, value varchar(10),"
        + " primary key (id1, id2))");
    
    query(con, "insert into for_existsKey values(1, 2, 'asd')");
    query(con, "insert into for_existsKey values(1, 3, 'dsa')");
    
    ForExistsKey x = new ForExistsKey();
    x.id1 = 1;
    x.id2 = 2;
    
    assertThat(r.existsKey("for_existsKey", x)).isTrue();
    x.id1 = 4;
    assertThat(r.existsKey("for_existsKey", x)).isFalse();
  }
  
  public static class ForSeleList {
    public String id, value;
  }
  
  @Test(dataProvider = CONNECT_PROVIDER)
  public void seleList(Connection con) throws Exception {
    TestingUtilRegister r = new TestingUtilRegister();
    r.setConnection(con);
    r.sqlViewer = stdSqlViewer();
    
    queryForce(con, "drop table for_seleList");
    query(con, "create table for_seleList(id varchar(10), value varchar(10))");
    
    query(con, "insert into for_seleList values('ok', 'value 001')");
    query(con, "insert into for_seleList values('ok', 'value 002')");
    query(con, "insert into for_seleList values('ok', 'value 003')");
    query(con, "insert into for_seleList values('left', 'fdsagfad')");
    query(con, "insert into for_seleList values('left', 'fdgfdsh')");
    query(con, "insert into for_seleList values('fdsf', 'ghfhgh')");
    
    //
    //
    //
    List<ForSeleList> res = r.seleList(ForSeleList.class,
        "select * from for_seleList where id=? order by value", "ok");
    //
    //
    //
    
    assertThat(res).hasSize(3);
    assertThat(res.get(1).id).isEqualTo("ok");
    assertThat(res.get(1).value).isEqualTo("value 002");
  }
  
}
