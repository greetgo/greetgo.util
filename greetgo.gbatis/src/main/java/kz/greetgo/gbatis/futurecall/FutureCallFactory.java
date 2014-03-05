package kz.greetgo.gbatis.futurecall;

import kz.greetgo.gbatis.model.Request;
import kz.greetgo.sqlmanager.gen.Conf;
import kz.greetgo.sqlmanager.model.Stru;

import org.springframework.jdbc.core.JdbcTemplate;

public class FutureCallFactory {
  private Conf conf;
  private Stru stru;
  private JdbcTemplate jdbc;
  
  public FutureCallFactory(Conf conf, Stru stru, JdbcTemplate jdbc) {
    this.conf = conf;
    this.stru = stru;
    this.jdbc = jdbc;
  }
  
  public <T> FutureCallDef<T> requestToFutureCall(Request request, Object[] args) {
    return new FutureCallDef<T>(conf, stru, jdbc, request, args);
  }
  
}
