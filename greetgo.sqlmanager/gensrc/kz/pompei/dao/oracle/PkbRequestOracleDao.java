package kz.pompei.dao.oracle;
import kz.greetgo.gbatis.t.Autoimpl;
import kz.pompei.dao.PkbRequestDao;
import kz.greetgo.gbatis.t.Sele;
@Autoimpl
public interface PkbRequestOracleDao extends PkbRequestDao{
  @Override
  @Sele("select s_pkbRequest.nextval from dual")
  long next();
}
