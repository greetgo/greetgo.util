package kz.pompei.dao.postgres.asd.asd;
import kz.greetgo.gbatis.t.Autoimpl;
import kz.pompei.dao.asd.asd.PersonDao;
import kz.greetgo.gbatis.t.Sele;
@Autoimpl
public interface PersonPostgresDao extends PersonDao{
  @Override
  @Sele("select nextval('s_person')")
  long next();
}
