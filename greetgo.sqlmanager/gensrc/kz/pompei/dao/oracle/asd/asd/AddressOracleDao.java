package kz.pompei.dao.oracle.asd.asd;
import org.apache.ibatis.annotations.Select;
import kz.pompei.dao.asd.asd.AddressDao;
public interface AddressOracleDao extends AddressDao{
  @Override
  @Select("select s_address.nextval from dual")
  long next();
}
