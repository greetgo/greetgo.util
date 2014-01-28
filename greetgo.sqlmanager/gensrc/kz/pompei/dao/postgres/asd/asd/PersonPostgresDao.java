package kz.pompei.dao.postgres.asd.asd;
import org.apache.ibatis.annotations.Select;
import kz.pompei.dao.asd.asd.PersonDao;
public interface PersonPostgresDao extends PersonDao{
  @Override
  @Select("select nextval('s_person')")
  long next();
}
