package kz.greetgo.gbatis.spring;

import static org.fest.assertions.api.Assertions.assertThat;

import java.net.URL;

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
  public void abstractProxyGeneratorTest() throws Exception {
    AbstractXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
        "classpath:kz/greetgo/gbatis/spring/config.xml");
    
    {
      TestProxyGenerator x = ctx.getBean(TestProxyGenerator.class);
      x.conf = conf;
      x.stru = sg.stru;
    }
    
    ctx.getBean(UsingDao.class).prepareDbData();
    
    {
      ClientDao6 clientDao6 = ctx.getBean(ClientDao6.class);
      
      System.out.println("##################################");
      for (Client client : clientDao6.youngClients(14)) {
        System.out.println(client);
      }
      System.out.println("##################################");
    }
    
    ctx.close();
    assertThat(1);
  }
  
}
