package kz.pompei.dao.oracle.asd.asd;

import kz.pompei.dao.asd.asd.SmsTemplateDao;
import org.apache.ibatis.annotations.Select;

public interface SmsTemplateOracleDao extends SmsTemplateDao {
  @Override
  @Select("select s_smsTemplate.nextval from dual")
  long next();
}
