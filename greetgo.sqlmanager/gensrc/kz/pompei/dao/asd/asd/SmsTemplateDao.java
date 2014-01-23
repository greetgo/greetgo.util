package kz.pompei.dao.asd.asd;

import kz.pompei.dbmodelstru.dsa.dsa.dsa.AnnotationType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Insert;
import java.util.Date;
import kz.pompei.dbmodel.asd.asd.SmsTemplate;

public interface SmsTemplateDao {
  long next();
  
  @Insert("insert into m_smsTemplate (smsTemplate) values (#{smsTemplate})")
  void ins(SmsTemplate smsTemplate);
  
  @Insert("insert into m_smsTemplate (smsTemplate) values (#{smsTemplate})")
  void add(@Param("smsTemplate") long smsTemplate);
  
  @Select("select * from v_smsTemplate where smsTemplate = #{smsTemplate}")
  SmsTemplate load(@Param("smsTemplate") long smsTemplate);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.smsTemplate,  x.createdAt ,x1.templateContent ,x2.phone1 ,x2.phone2 from (select u.* from m_smsTemplate u, tts where u.createdAt <= tts.ts) x left join( select  aa.smsTemplate ,bb.templateContent from ( select a.smsTemplate, max(b.ts) ts from m_smsTemplate a left join (select x.* from m_smsTemplate_templateContent x, tts where x.ts <= tts.ts) b on a.smsTemplate = b.smsTemplate ,tts where b.ts <= tts.ts group by  a.smsTemplate ) aa left join m_smsTemplate_templateContent bb on aa.smsTemplate = bb.smsTemplate and aa.ts = bb.ts) x1 on x1.smsTemplate = x.smsTemplate left join( select  aa.smsTemplate ,bb.phone1 ,bb.phone2 from ( select a.smsTemplate, max(b.ts) ts from m_smsTemplate a left join (select x.* from m_smsTemplate_phone x, tts where x.ts <= tts.ts) b on a.smsTemplate = b.smsTemplate ,tts where b.ts <= tts.ts group by  a.smsTemplate ) aa left join m_smsTemplate_phone bb on aa.smsTemplate = bb.smsTemplate and aa.ts = bb.ts) x2 on x2.smsTemplate = x.smsTemplate) select * from xx where xx.smsTemplate = #{smsTemplate}")
      SmsTemplate loadAt(@Param("ts") Date ts, @Param("smsTemplate") long smsTemplate);
  
  @Insert("insert into m_smsTemplate_templateContent (smsTemplate, templateContent) values (#{smsTemplate}, #{templateContent})")
      void insTemplateContent(SmsTemplate smsTemplate);
  
  @Insert("insert into m_smsTemplate_templateContent (smsTemplate, templateContent) values (#{smsTemplate}, #{templateContent})")
      void setTemplateContent(@Param("smsTemplate") long smsTemplate,
          @Param("templateContent") String templateContent);
  
  @Insert("insert into m_smsTemplate_phone (smsTemplate, phone1, phone2) values (#{smsTemplate}, #{phone1}, #{phone2})")
      void insPhone(SmsTemplate smsTemplate);
  
  @Insert("insert into m_smsTemplate_phone (smsTemplate, phone1, phone2) values (#{smsTemplate}, #{phone1}, #{phone2})")
      void setPhone(@Param("smsTemplate") long smsTemplate, @Param("phone1") Long phone1,
          @Param("phone2") AnnotationType phone2);
}
