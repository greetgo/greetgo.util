package kz.pompei.dao.postgres.asd.asd;
import kz.pompei.dao.asd.asd.SmsTemplateDao;
import kz.greetgo.gbatis.t.Autoimpl;
import kz.greetgo.gbatis.t.Sele;
@Autoimpl
public interface SmsTemplatePostgresDao extends SmsTemplateDao{
  @Override
  @Sele("select nextval('s_smsTemplate')")
  long next();
}
