package kz.pompei.dao.oracle;

import org.apache.ibatis.annotations.Select;
import kz.pompei.dao.PkbRequestDao;

public interface PkbRequestOracleDao extends PkbRequestDao {
  @Override
  @Select("select s_pkbRequest.nextval from dual")
  long next();
}
