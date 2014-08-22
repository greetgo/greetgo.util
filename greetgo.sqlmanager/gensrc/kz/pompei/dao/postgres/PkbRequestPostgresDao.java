package kz.pompei.dao.postgres;
import kz.greetgo.gbatis.t.Autoimpl;
import kz.pompei.dao.PkbRequestDao;
import kz.greetgo.gbatis.t.Sele;
@Autoimpl
public interface PkbRequestPostgresDao extends PkbRequestDao{
  @Override
  @Sele("select nextval('s_pkbRequest')")
  long next();
}
