package kz.pompei.dao.postgres.asd.asd;
import kz.pompei.dao.asd.asd.ContractDao;
import kz.greetgo.gbatis.t.Autoimpl;
import kz.greetgo.gbatis.t.Sele;
@Autoimpl
public interface ContractPostgresDao extends ContractDao{
  @Override
  @Sele("select nextval('s_contract')")
  long next();
}
