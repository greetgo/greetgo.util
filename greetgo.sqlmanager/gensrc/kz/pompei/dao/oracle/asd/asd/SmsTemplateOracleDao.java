package kz.pompei.dao.oracle.asd.asd;
import kz.pompei.dao.asd.asd.SmsTemplateDao;
import kz.greetgo.gbatis.t.Autoimpl;
import kz.greetgo.gbatis.t.Sele;
@Autoimpl
public interface SmsTemplateOracleDao extends SmsTemplateDao{
  @Override
  @Sele("select s_smsTemplate.nextval from dual")
  long next();
}
