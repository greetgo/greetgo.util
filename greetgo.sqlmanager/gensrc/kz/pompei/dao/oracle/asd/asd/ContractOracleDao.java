package kz.pompei.dao.oracle.asd.asd;

import org.apache.ibatis.annotations.Select;
import kz.pompei.dao.asd.asd.ContractDao;

public interface ContractOracleDao extends ContractDao {
  @Override
  @Select("select s_contract.nextval from dual")
  long next();
}
