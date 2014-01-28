package kz.pompei.dao.postgres.asd.asd;
import kz.pompei.dao.asd.asd.SmsTemplateDao;
import org.apache.ibatis.annotations.Select;
public interface SmsTemplatePostgresDao extends SmsTemplateDao{
  @Override
  @Select("select nextval('s_smsTemplate')")
  long next();
}
