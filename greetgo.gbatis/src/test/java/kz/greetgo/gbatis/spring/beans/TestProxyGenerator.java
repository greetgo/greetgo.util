package kz.greetgo.gbatis.spring.beans;

import java.util.ArrayList;
import java.util.List;

import kz.greetgo.gbatis.spring.AbstractProxyGenerator;
import kz.greetgo.sqlmanager.gen.Conf;
import kz.greetgo.sqlmanager.model.Stru;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TestProxyGenerator extends AbstractProxyGenerator {
  
  public Conf conf;
  public Stru stru;
  
  @Override
  protected Conf getConf() {
    return conf;
  }
  
  @Override
  protected Stru getStru() {
    return stru;
  }
  
  @Override
  protected JdbcTemplate getJdbcTemplate() {
    return getAppContext().getBean(JdbcTemplate.class);
  }
  
  @Override
  protected List<String> getBasePackageList() {
    List<String> ret = new ArrayList<>();
    ret.add("kz.greetgo.gbatis.spring.daos");
    return ret;
  }
  
}
