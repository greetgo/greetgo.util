package kz.pompei.dao.oracle.asd.asd;
import kz.pompei.dao.asd.asd.ContractDao;
import kz.greetgo.gbatis.t.Autoimpl;
import kz.greetgo.gbatis.t.Sele;
@Autoimpl
public interface ContractOracleDao extends ContractDao{
  @Override
  @Sele("select s_contract.nextval from dual")
  long next();
}
