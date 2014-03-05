package kz.greetgo.gbatis.futurecall;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kz.greetgo.conf.SysParams;
import kz.greetgo.gbatis.model.Param;
import kz.greetgo.gbatis.model.Request;
import kz.greetgo.gbatis.model.ResultType;
import kz.greetgo.sqlmanager.gen.Conf;
import kz.greetgo.sqlmanager.gen.Nf6Generator;
import kz.greetgo.sqlmanager.gen.Nf6GeneratorPostgres;
import kz.greetgo.sqlmanager.parser.StruGenerator;

import org.postgresql.util.PSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.PooledDataSource;

public class FutureCallDefTest {
  private String userid = "gbatis";
  
  private Conf conf;
  private StruGenerator sg;
  
  private void setupSG() throws Exception {
    URL url = getClass().getResource("stru.nf3");
    sg = new StruGenerator();
    sg.printPStru = false;
    sg.parse(url);
  }
  
  private void setupConf() {
    conf = new Conf();
    conf.separator = ";;";
    conf.tabPrefix = "m_";
    conf.seqPrefix = "s_";
    conf.vPrefix = "v_";
    conf.withPrefix = "x_";
    conf.ts = "ts";
    conf.cre = "createdAt";
    conf.bigQuote = "big_quote";
    conf._ins_ = "ins_";
    conf._p_ = "p_";
    conf._value_ = "__value__";
    conf.daoSuffix = "Dao";
  }
  
  private static String changeDb(String url, String userid) {
    int idx = url.lastIndexOf('/');
    if (idx < 0) return url + "/" + userid;
    return url.substring(0, idx + 1) + userid;
  }
  
  private void setupJdbcTemplate() throws Exception {
    Class.forName("org.postgresql.Driver");
    
    {
      Connection con = DriverManager.getConnection(SysParams.pgAdminUrl(),
          SysParams.pgAdminUserid(), SysParams.pgAdminPassword());
      try {
        
        try {
          PreparedStatement ps = con.prepareStatement("drop database " + userid);
          ps.executeUpdate();
          ps.close();
        } catch (PSQLException e) {
          System.err.println(e.getMessage());
        }
        try {
          PreparedStatement ps = con.prepareStatement("drop user " + userid);
          ps.executeUpdate();
          ps.close();
        } catch (PSQLException e) {
          System.err.println(e.getMessage());
        }
        
        {
          PreparedStatement ps = con.prepareStatement("create database " + userid);
          ps.executeUpdate();
          ps.close();
        }
        
        {
          PreparedStatement ps = con.prepareStatement("create user " + userid
              + " encrypted password '" + userid + "'");
          ps.executeUpdate();
          ps.close();
        }
        
        {
          PreparedStatement ps = con.prepareStatement("grant all on database " + userid + " to "
              + userid);
          ps.executeUpdate();
          ps.close();
        }
        
      } finally {
        con.close();
      }
    }
    
    {
      Connection con = DriverManager.getConnection(changeDb(SysParams.pgAdminUrl(), userid),
          userid, userid);
      try {
        Nf6Generator nf6generator = new Nf6GeneratorPostgres(sg);
        {
          ByteArrayOutputStream bout = new ByteArrayOutputStream();
          PrintStream out = new PrintStream(bout, false, "UTF-8");
          nf6generator.printSqls(out);
          nf6generator.printPrograms(out);
          out.flush();
          
          {
            Statement st = con.createStatement();
            for (String sql : new String(bout.toByteArray(), "UTF-8").split(";;")) {
              st.addBatch(sql);
            }
            st.executeBatch();
            st.close();
          }
        }
        
      } finally {
        con.close();
      }
    }
    
    {
      ComboPooledDataSource ds = new ComboPooledDataSource();
      ds.setDriverClass("org.postgresql.Driver");
      ds.setJdbcUrl(changeDb(SysParams.pgAdminUrl(), userid));
      ds.setUser(userid);
      ds.setPassword(userid);
      
      ds.setAcquireIncrement(1);
      ds.setMinPoolSize(1);
      ds.setMaxPoolSize(3);
      ds.setMaxIdleTime(120);
      
      dataSource = ds;
      
      jdbc = new JdbcTemplate(dataSource);
    }
  }
  
  private JdbcTemplate jdbc;
  private PooledDataSource dataSource;
  
  @BeforeMethod
  public void setup() throws Exception {
    setupConf();
    setupSG();
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
  
  private void executeSqls(List<String> sqls) throws SQLException {
    Connection con = dataSource.getConnection();
    try {
      Statement st = con.createStatement();
      try {
        for (String sql : sqls) {
          st.addBatch(sql);
        }
        st.executeBatch();
      } finally {
        st.close();
      }
    } finally {
      con.close();
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
