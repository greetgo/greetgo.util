package kz.greetgo.gbatis.futurecall;

import static org.fest.assertions.api.Assertions.assertThat;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import kz.greetgo.gbatis.model.Param;
import kz.greetgo.gbatis.model.Request;
import kz.greetgo.gbatis.model.RequestType;
import kz.greetgo.gbatis.model.ResultType;
import kz.greetgo.gbatis.util.AbstractWithDbTest;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FutureCallDefTest extends AbstractWithDbTest {
  @Override
  protected URL getStruUrl() {
    return getClass().getResource("stru.nf3");
  }
  
  @BeforeMethod
  public void setup() throws Exception {
    prepareConf();
    prepareSG();
    setupJdbcTemplate();
  }
  
  @AfterMethod
  public void teardown() throws Exception {
    dataSource.close();
    dataSource = null;
    jdbc = null;
  }
  
  public static class Client {
    public long id;
    public String surname, name, patronymic;
    
    @Override
    public String toString() {
      return "Client [id=" + id + ", surname=" + surname + ", name=" + name + ", patronymic="
          + patronymic + "]";
    }
    
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + (int)(id ^ (id >>> 32));
      return result;
    }
    
    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      Client other = (Client)obj;
      if (id != other.id) return false;
      return true;
    }
  }
  
  @Test
  public void last_sele() throws Exception {
    {
      List<String> sqls = new ArrayList<>();
      
      sqls.add("insert into m_client (client) values (11)");
      sqls.add("insert into m_client_surname (client, surname) values (11, 'Колпаков')");
      sqls.add("insert into m_client_name (client, name) values (11, 'Евгений')");
      sqls.add("insert into m_client_patronymic (client, patronymic) values (11, 'Анатольевич')");
      sqls.add("insert into m_client_age (client, age) values (11, 30)");
      
      sqls.add("insert into m_client (client) values (21)");
      sqls.add("insert into m_client_surname (client, surname) values (21, 'Берсенев')");
      sqls.add("insert into m_client_name (client, name) values (21, 'Олег')");
      sqls.add("insert into m_client_patronymic (client, patronymic) values (21, 'Алексеевич')");
      sqls.add("insert into m_client_age (client, age) values (21, 30)");
      
      sqls.add("insert into m_client (client) values (13)");
      sqls.add("insert into m_client_surname (client, surname) values (13, 'Иванов')");
      sqls.add("insert into m_client_name (client, name) values (13, 'Иван')");
      sqls.add("insert into m_client_patronymic (client, patronymic) values (13, 'Иванович')");
      sqls.add("insert into m_client_age (client, age) values (13, 14)");
      
      executeSqls(sqls);
    }
    
    Request req = new Request();
    req.callNow = true;
    req.paramList.add(new Param(Integer.TYPE, "age"));
    req.resultDataClass = Client.class;
    req.resultType = ResultType.LIST;
    req.type = RequestType.Sele;
    req.sql = "select client as id, surname, name, patronymic"
        + " from v_client where age = #{age} order by client";
    
    FutureCallDef<List<Client>> fc = new FutureCallDef<>(conf, sg.stru, jdbc, req,
        new Object[] { 30 });
    
    List<Client> list = fc.last();
    
    for (Client cl : list) {
      System.out.println(cl);
    }
    
    assertThat(list).hasSize(2);
    assertThat(list.get(0).id).isEqualTo(11);
    assertThat(list.get(0).surname).isEqualTo("Колпаков");
    assertThat(list.get(0).name).isEqualTo("Евгений");
    assertThat(list.get(0).patronymic).isEqualTo("Анатольевич");
    
    assertThat(list.get(1).id).isEqualTo(21);
    assertThat(list.get(1).surname).isEqualTo("Берсенев");
    assertThat(list.get(1).name).isEqualTo("Олег");
    assertThat(list.get(1).patronymic).isEqualTo("Алексеевич");
    
  }
  
  @Test
  public void last_modi() throws Exception {
    {
      List<String> sqls = new ArrayList<>();
      
      sqls.add("insert into m_client (client) values (11)");
      sqls.add("insert into m_client_age (client, age) values (11, 30)");
      
      sqls.add("insert into m_client (client) values (21)");
      sqls.add("insert into m_client_age (client, age) values (21, 30)");
      
      sqls.add("insert into m_client (client) values (13)");
      sqls.add("insert into m_client_age (client, age) values (13, 14)");
      
      executeSqls(sqls);
    }
    Request req = new Request();
    req.callNow = true;
    req.paramList.add(new Param(Integer.TYPE, "age"));
    req.paramList.add(new Param(Date.class, "atTime"));
    req.resultDataClass = Integer.TYPE;
    req.resultType = ResultType.SIMPLE;
    req.type = RequestType.Modi;
    req.sql = "update m_client_age set ts = #{atTime} where age = #{age}";
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    Object[] args = new Object[] { 30, sdf.parse("2014-01-01 11:00:00") };
    FutureCallDef<Integer> fc = new FutureCallDef<>(conf, sg.stru, jdbc, req, args);
    
    Integer lastRet = fc.last();
    
    assertThat(lastRet).isEqualTo(2);
    
  }
  
  @Test
  public void last_int() throws Exception {
    {
      List<String> sqls = new ArrayList<>();
      
      sqls.add("insert into m_client (client) values (11)");
      sqls.add("insert into m_client_surname (client, surname) values (11, 'Колпаков')");
      sqls.add("insert into m_client_name (client, name) values (11, 'Евгений')");
      sqls.add("insert into m_client_patronymic (client, patronymic) values (11, 'Анатольевич')");
      sqls.add("insert into m_client_age (client, age) values (11, 30)");
      
      sqls.add("insert into m_client (client) values (21)");
      sqls.add("insert into m_client_surname (client, surname) values (21, 'Берсенев')");
      sqls.add("insert into m_client_name (client, name) values (21, 'Олег')");
      sqls.add("insert into m_client_patronymic (client, patronymic) values (21, 'Алексеевич')");
      sqls.add("insert into m_client_age (client, age) values (21, 30)");
      
      sqls.add("insert into m_client (client) values (13)");
      sqls.add("insert into m_client_surname (client, surname) values (13, 'Иванов')");
      sqls.add("insert into m_client_name (client, name) values (13, 'Иван')");
      sqls.add("insert into m_client_patronymic (client, patronymic) values (13, 'Иванович')");
      sqls.add("insert into m_client_age (client, age) values (13, 14)");
      
      executeSqls(sqls);
    }
    
    Request req = new Request();
    req.callNow = true;
    req.paramList.add(new Param(Integer.TYPE, "age"));
    req.resultDataClass = Long.class;
    req.resultType = ResultType.SIMPLE;
    req.type = RequestType.Sele;
    req.sql = "select client from v_client where age = #{age}";
    
    FutureCallDef<Long> fc = new FutureCallDef<>(conf, sg.stru, jdbc, req, new Object[] { 14 });
    
    Long id = fc.last();
    
    assertThat(id).isEqualTo(13);
    
  }
  
  @Test
  public void last_sele_set() throws Exception {
    {
      List<String> sqls = new ArrayList<>();
      
      sqls.add("insert into m_client (client) values (11)");
      sqls.add("insert into m_client_surname (client, surname) values (11, 'Колпаков')");
      sqls.add("insert into m_client_name (client, name) values (11, 'Евгений')");
      sqls.add("insert into m_client_patronymic (client, patronymic) values (11, 'Анатольевич')");
      sqls.add("insert into m_client_age (client, age) values (11, 30)");
      
      sqls.add("insert into m_client (client) values (21)");
      sqls.add("insert into m_client_surname (client, surname) values (21, 'Берсенев')");
      sqls.add("insert into m_client_name (client, name) values (21, 'Олег')");
      sqls.add("insert into m_client_patronymic (client, patronymic) values (21, 'Алексеевич')");
      sqls.add("insert into m_client_age (client, age) values (21, 30)");
      
      sqls.add("insert into m_client (client) values (13)");
      sqls.add("insert into m_client_surname (client, surname) values (13, 'Иванов')");
      sqls.add("insert into m_client_name (client, name) values (13, 'Иван')");
      sqls.add("insert into m_client_patronymic (client, patronymic) values (13, 'Иванович')");
      sqls.add("insert into m_client_age (client, age) values (13, 14)");
      
      executeSqls(sqls);
    }
    
    Request req = new Request();
    req.callNow = true;
    req.paramList.add(new Param(Integer.TYPE, "age"));
    req.resultDataClass = Client.class;
    req.resultType = ResultType.SET;
    req.type = RequestType.Sele;
    req.sql = "select client as id, surname, name, patronymic"
        + " from v_client where age = #{age} order by client";
    
    FutureCallDef<Set<Client>> fc = new FutureCallDef<>(conf, sg.stru, jdbc, req,
        new Object[] { 30 });
    
    Set<Client> list = fc.last();
    
    for (Client cl : list) {
      System.out.println(cl);
    }
    
    assertThat(list).hasSize(2);
    
  }
  
}
