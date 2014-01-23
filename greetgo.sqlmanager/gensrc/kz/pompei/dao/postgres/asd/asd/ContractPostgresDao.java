package kz.pompei.dao.postgres.asd.asd;

import org.apache.ibatis.annotations.Select;
import kz.pompei.dao.asd.asd.ContractDao;

public interface ContractPostgresDao extends ContractDao {
  @Override
  @Select("select nextval('s_contract')")
  long next();
}
