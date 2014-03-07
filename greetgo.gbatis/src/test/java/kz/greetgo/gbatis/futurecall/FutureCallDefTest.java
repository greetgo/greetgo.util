package kz.greetgo.gbatis.futurecall;

import static org.fest.assertions.api.Assertions.assertThat;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
  }
  
  @Test
  public void last() throws Exception {
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
}
