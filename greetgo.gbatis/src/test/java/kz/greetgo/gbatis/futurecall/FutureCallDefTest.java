package kz.greetgo.gbatis.futurecall;

import static org.fest.assertions.api.Assertions.assertThat;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import kz.greetgo.gbatis.model.Param;
import kz.greetgo.gbatis.model.Request;
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
  
  static class Client {
    long id;
    String surname, name, patronymic;
    
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
      
      sqls.add("insert into m_client (client) values (1)");
      sqls.add("insert into m_client_surname (client, surname) values (1, 'Колпаков')");
      sqls.add("insert into m_client_name (client, name) values (1, 'Евгений')");
      sqls.add("insert into m_client_patronymic (client, patronymic) values (1, 'Анатольевич')");
      sqls.add("insert into m_client_age (client, age) values (1, 30)");
      
      sqls.add("insert into m_client (client) values (2)");
      sqls.add("insert into m_client_surname (client, surname) values (2, 'Берсенев')");
      sqls.add("insert into m_client_name (client, name) values (2, 'Олег')");
      sqls.add("insert into m_client_patronymic (client, patronymic) values (2, 'Алексеевич')");
      sqls.add("insert into m_client_age (client, age) values (2, 30)");
      
      sqls.add("insert into m_client (client) values (3)");
      sqls.add("insert into m_client_surname (client, surname) values (3, 'Иванов')");
      sqls.add("insert into m_client_name (client, name) values (3, 'Иван')");
      sqls.add("insert into m_client_patronymic (client, patronymic) values (3, 'Иванович')");
      sqls.add("insert into m_client_age (client, age) values (3, 13)");
      
      executeSqls(sqls);
    }
    
    Request req = new Request();
    req.callNow = true;
    req.paramList.add(new Param(Integer.TYPE, "age"));
    req.resultDataClass = Client.class;
    req.resultType = ResultType.LIST;
    req.sql = "select * from v_client where age = #{age} order by client";
    
    FutureCallDef<List<Client>> fc = new FutureCallDef<>(conf, sg.stru, jdbc, req,
        new Object[] { 30 });
    
    List<Client> list = fc.last();
    
    for (Client cl : list) {
      System.out.println(cl);
    }
    
    assertThat(1);
  }
}
