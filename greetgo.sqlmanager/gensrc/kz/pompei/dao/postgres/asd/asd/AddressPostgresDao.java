package kz.pompei.dao.postgres.asd.asd;
import org.apache.ibatis.annotations.Select;
import kz.pompei.dao.asd.asd.AddressDao;
public interface AddressPostgresDao extends AddressDao{
  @Override
  @Select("select nextval('s_address')")
  long next();
}
