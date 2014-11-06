package kz.greetgo.gbatis.util.impl;

import static org.fest.assertions.api.Assertions.assertThat;

import java.sql.Connection;
import java.util.List;

import kz.greetgo.gbatis.futurecall.SqlViewer;
import kz.greetgo.gbatis.util.test.TestBase;

import org.testng.annotations.Test;

public class AbstractUtilRegisterTest extends TestBase {
  
  @Override
  protected String dbSchema() {
    return "gbatis_util";
  }
  
  @Test(dataProvider = "connectProvider")
  public void cleanTables(Connection con) throws Exception {
    TestingUtilRegister r = new TestingUtilRegister();
    r.setConnection(con);
    r.sqlViewer = new SqlViewer() {
      
      @Override
      public void view(String sql, List<Object> params, long delay) {
        System.out.println(sql);
      }
      
      @Override
      public boolean needView() {
        return true;
      }
    };
    
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
}
