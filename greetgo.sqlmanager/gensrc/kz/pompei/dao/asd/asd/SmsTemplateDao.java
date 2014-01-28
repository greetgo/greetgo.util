package kz.pompei.dao.asd.asd;

import kz.pompei.dbmodelstru.dsa.dsa.dsa.AnnotationType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import kz.pompei.dbmodel.asd.asd.SmsTemplate;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.annotations.Options;

public interface SmsTemplateDao {
  long next();
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_smsTemplate (#{smsTemplate})}")
  void ins(SmsTemplate smsTemplate);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_smsTemplate (#{smsTemplate})}")
  void add(@Param("smsTemplate") long smsTemplate);
  
  @Select("select * from v_smsTemplate where smsTemplate = #{smsTemplate}")
  SmsTemplate load(@Param("smsTemplate") long smsTemplate);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.smsTemplate,  x.createdAt ,x1.templateContent ,x2.phone1 ,x2.phone2 from (select u.* from m_smsTemplate u, tts where u.createdAt <= tts.ts) x left join( select smsTemplate, templateContent from ( select m.*, row_number() over (partition by m.smsTemplate order by m.ts desc ) as rn__ from m_smsTemplate_templateContent m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.smsTemplate = x.smsTemplate left join( select smsTemplate, phone1, phone2 from ( select m.*, row_number() over (partition by m.smsTemplate order by m.ts desc ) as rn__ from m_smsTemplate_phone m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.smsTemplate = x.smsTemplate) select * from xx where xx.smsTemplate = #{smsTemplate}")
      SmsTemplate loadAt(@Param("ts") Date ts, @Param("smsTemplate") long smsTemplate);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_smsTemplate_templateContent (#{smsTemplate}, #{templateContent})}")
  void insTemplateContent(SmsTemplate smsTemplate);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_smsTemplate_templateContent (#{smsTemplate}, #{templateContent})}")
  void setTemplateContent(@Param("smsTemplate") long smsTemplate,
      @Param("templateContent") String templateContent);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_smsTemplate_phone (#{smsTemplate}, #{phone1}, #{phone2})}")
  void insPhone(SmsTemplate smsTemplate);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_smsTemplate_phone (#{smsTemplate}, #{phone1}, #{phone2})}")
  void setPhone(@Param("smsTemplate") long smsTemplate, @Param("phone1") Long phone1,
      @Param("phone2") AnnotationType phone2);
}
