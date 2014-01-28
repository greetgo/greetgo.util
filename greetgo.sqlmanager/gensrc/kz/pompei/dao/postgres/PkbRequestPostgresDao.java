package kz.pompei.dao.postgres;
import org.apache.ibatis.annotations.Select;
import kz.pompei.dao.PkbRequestDao;
public interface PkbRequestPostgresDao extends PkbRequestDao{
  @Override
  @Select("select nextval('s_pkbRequest')")
  long next();
}
