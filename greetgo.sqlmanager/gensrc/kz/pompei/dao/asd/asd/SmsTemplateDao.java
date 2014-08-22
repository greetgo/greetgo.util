package kz.pompei.dao.asd.asd;
import kz.pompei.dbmodelstru.dsa.dsa.dsa.AnnotationType;
import kz.greetgo.gbatis.t.Prm;
import java.util.Date;
import kz.pompei.dbmodel.asd.asd.SmsTemplate;
import kz.greetgo.gbatis.t.Call;
import kz.greetgo.gbatis.t.Sele;
public interface SmsTemplateDao {
  long next();

  @Call("{call p_smsTemplate (#{smsTemplate})}")
  void ins(SmsTemplate smsTemplate);
  @Call("{call p_smsTemplate (#{smsTemplate})}")
  void add(@Prm("smsTemplate")long smsTemplate);
  @Sele("select * from v_smsTemplate where smsTemplate = #{smsTemplate}")
  SmsTemplate load(@Prm("smsTemplate")long smsTemplate);
  @Sele("with tts as (select #{ts} ts from dual), xx as ( select  x.smsTemplate,  x.createdAt ,x1.templateContent ,x2.phone1 ,x2.phone2 from (select u.* from m_smsTemplate u, tts where u.createdAt <= tts.ts) x left join( select smsTemplate, templateContent from ( select m.*, row_number() over (partition by m.smsTemplate order by m.ts desc ) as rn__ from m_smsTemplate_templateContent m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.smsTemplate = x.smsTemplate left join( select smsTemplate, phone1, phone2 from ( select m.*, row_number() over (partition by m.smsTemplate order by m.ts desc ) as rn__ from m_smsTemplate_phone m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.smsTemplate = x.smsTemplate) select * from xx where xx.smsTemplate = #{smsTemplate}")
  SmsTemplate loadAt(@Prm("ts")Date ts, @Prm("smsTemplate")long smsTemplate);
  @Call("{call p_smsTemplate_templateContent (#{smsTemplate}, #{templateContent})}")
  void insTemplateContent(SmsTemplate smsTemplate);
  @Call("{call p_smsTemplate_templateContent (#{smsTemplate}, #{templateContent})}")
  void setTemplateContent(@Prm("smsTemplate")long smsTemplate, @Prm("templateContent")String templateContent);
  @Call("{call p_smsTemplate_phone (#{smsTemplate}, #{phone1}, #{phone2})}")
  void insPhone(SmsTemplate smsTemplate);
  @Call("{call p_smsTemplate_phone (#{smsTemplate}, #{phone1}, #{phone2})}")
  void setPhone(@Prm("smsTemplate")long smsTemplate, @Prm("phone1")Long phone1, @Prm("phone2")AnnotationType phone2);
}
