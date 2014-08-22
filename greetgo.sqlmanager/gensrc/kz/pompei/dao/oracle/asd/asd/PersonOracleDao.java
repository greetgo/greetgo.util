package kz.pompei.dao.oracle.asd.asd;
import kz.greetgo.gbatis.t.Autoimpl;
import kz.pompei.dao.asd.asd.PersonDao;
import kz.greetgo.gbatis.t.Sele;
@Autoimpl
public interface PersonOracleDao extends PersonDao{
  @Override
  @Sele("select s_person.nextval from dual")
  long next();
}
