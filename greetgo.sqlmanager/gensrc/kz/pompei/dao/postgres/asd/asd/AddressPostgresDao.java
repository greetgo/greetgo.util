package kz.pompei.dao.postgres.asd.asd;
import kz.greetgo.gbatis.t.Autoimpl;
import kz.greetgo.gbatis.t.Sele;
import kz.pompei.dao.asd.asd.AddressDao;
@Autoimpl
public interface AddressPostgresDao extends AddressDao{
  @Override
  @Sele("select nextval('s_address')")
  long next();
}
