package kz.greetgo.gbatis.spring;

import static org.fest.assertions.api.Assertions.assertThat;

import java.net.URL;
import java.util.Date;
import java.util.List;

import kz.greetgo.gbatis.spring.beans.TestProxyGenerator;
import kz.greetgo.gbatis.spring.beans.UsingDao;
import kz.greetgo.gbatis.spring.daos.Client;
import kz.greetgo.gbatis.spring.daos.ClientDao6;
import kz.greetgo.gbatis.util.AbstractWithDbTest;

import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AbstractProxyGeneratorTest extends AbstractWithDbTest {
  @Override
  protected URL getStruUrl() {
    return getClass().getResource("stru.nf3");
  }
  
  @BeforeMethod
  public void setup() throws Exception {
    prepareConf();
    prepareSG();
    prepareDb();
  }
  
  @Test
  public void lastTest() throws Exception {
    AbstractXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
        "classpath:kz/greetgo/gbatis/spring/config.xml");
    
    {
      TestProxyGenerator x = ctx.getBean(TestProxyGenerator.class);
      x.conf = conf;
      x.stru = sg.stru;
    }
    
    ctx.getBean(UsingDao.class).prepareClients();
    
    {
      ClientDao6 clientDao6 = ctx.getBean(ClientDao6.class);
      
      System.out.println("##################################");
      List<Client> youngClients = clientDao6.youngClients(14).last();
      
      for (Client client : youngClients) {
        System.out.println(client);
      }
      assertThat(youngClients).hasSize(5);
      System.out.println("##################################");
    }
    
    ctx.close();
  }
  
  @Test
  public void atTest() throws Exception {
    AbstractXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
        "classpath:kz/greetgo/gbatis/spring/config.xml");
    
    {
      TestProxyGenerator x = ctx.getBean(TestProxyGenerator.class);
      x.conf = conf;
      x.stru = sg.stru;
    }
    
    UsingDao usingDao = ctx.getBean(UsingDao.class);
    usingDao.prepareClients();
    
    ClientDao6 clientDao6 = ctx.getBean(ClientDao6.class);
    
    Thread.sleep(500);
    
    Date beforeOlding = clientDao6.now();
    System.out.println("beforeOlding = " + beforeOlding);
    
    usingDao.makeOlds();
    
    List<Client> allClients1 = clientDao6.allClients().last();
    System.out.println(allClients1);
    
    List<Client> allClients2 = clientDao6.allClients().on(beforeOlding);
    System.out.println(allClients2);
    
    List<Client> youngClientsLast = clientDao6.youngClients(13).last();
    
    List<Client> youngClientsAt = clientDao6.youngClients(13).on(beforeOlding);
    
    System.out.println("youngClientsLast.size = " + youngClientsLast.size());
    System.out.println("youngClientsAt.size = " + youngClientsAt.size());
    
    ctx.close();
  }
  
}
