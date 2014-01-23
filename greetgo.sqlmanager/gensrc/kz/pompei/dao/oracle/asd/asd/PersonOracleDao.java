package kz.pompei.dao.oracle.asd.asd;

import org.apache.ibatis.annotations.Select;
import kz.pompei.dao.asd.asd.PersonDao;

public interface PersonOracleDao extends PersonDao {
  @Override
  @Select("select s_person.nextval from dual")
  long next();
}
