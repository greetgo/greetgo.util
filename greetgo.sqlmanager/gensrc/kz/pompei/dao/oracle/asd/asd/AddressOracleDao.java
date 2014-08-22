package kz.pompei.dao.oracle.asd.asd;
import kz.greetgo.gbatis.t.Autoimpl;
import kz.greetgo.gbatis.t.Sele;
import kz.pompei.dao.asd.asd.AddressDao;
@Autoimpl
public interface AddressOracleDao extends AddressDao{
  @Override
  @Sele("select s_address.nextval from dual")
  long next();
}
