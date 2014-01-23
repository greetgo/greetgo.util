package kz.pompei.dao.asd.asd;

import kz.pompei.dbmodelstru.dsa.dsa.dsa.AnnotationType;
import org.apache.ibatis.annotations.Select;
import kz.pompei.dbmodel.asd.asd.Phone;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Insert;
import java.util.Date;
import java.util.List;

public interface PhoneDao {
  
  @Insert("insert into m_phone (person, annType) values (#{person}, #{annType})")
  void ins(Phone phone);
  
  @Insert("insert into m_phone (person, annType) values (#{person}, #{annType})")
  void add(@Param("person") long person, @Param("annType") AnnotationType annType);
  
  @Select("select * from v_phone where person = #{person} and annType = #{annType}")
  Phone load(@Param("person") long person, @Param("annType") AnnotationType annType);
  
  @Select("select * from v_phone where person = #{person}")
  List<Phone> loadList(@Param("person") long person);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.person,  x.annType,  x.createdAt ,x1.type ,x2.value ,x3.smsTemplate from (select u.* from m_phone u, tts where u.createdAt <= tts.ts) x left join( select  aa.person ,aa.annType ,bb.type from ( select a.person, a.annType, max(b.ts) ts from m_phone a left join (select x.* from m_phone_type x, tts where x.ts <= tts.ts) b on a.person = b.person and a.annType = b.annType ,tts where b.ts <= tts.ts group by  a.person ,a.annType ) aa left join m_phone_type bb on aa.person = bb.person and aa.annType = bb.annType and aa.ts = bb.ts) x1 on x1.person = x.person and x1.annType = x.annType left join( select  aa.person ,aa.annType ,bb.value from ( select a.person, a.annType, max(b.ts) ts from m_phone a left join (select x.* from m_phone_value x, tts where x.ts <= tts.ts) b on a.person = b.person and a.annType = b.annType ,tts where b.ts <= tts.ts group by  a.person ,a.annType ) aa left join m_phone_value bb on aa.person = bb.person and aa.annType = bb.annType and aa.ts = bb.ts) x2 on x2.person = x.person and x2.annType = x.annType left join( select  aa.person ,aa.annType ,bb.smsTemplate from ( select a.person, a.annType, max(b.ts) ts from m_phone a left join (select x.* from m_phone_smsTemplate x, tts where x.ts <= tts.ts) b on a.person = b.person and a.annType = b.annType ,tts where b.ts <= tts.ts group by  a.person ,a.annType ) aa left join m_phone_smsTemplate bb on aa.person = bb.person and aa.annType = bb.annType and aa.ts = bb.ts) x3 on x3.person = x.person and x3.annType = x.annType) select * from xx where xx.person = #{person} and xx.annType = #{annType}")
      Phone loadAt(@Param("ts") Date ts, @Param("person") long person,
          @Param("annType") AnnotationType annType);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.person,  x.annType,  x.createdAt ,x1.type ,x2.value ,x3.smsTemplate from (select u.* from m_phone u, tts where u.createdAt <= tts.ts) x left join( select  aa.person ,aa.annType ,bb.type from ( select a.person, a.annType, max(b.ts) ts from m_phone a left join (select x.* from m_phone_type x, tts where x.ts <= tts.ts) b on a.person = b.person and a.annType = b.annType ,tts where b.ts <= tts.ts group by  a.person ,a.annType ) aa left join m_phone_type bb on aa.person = bb.person and aa.annType = bb.annType and aa.ts = bb.ts) x1 on x1.person = x.person and x1.annType = x.annType left join( select  aa.person ,aa.annType ,bb.value from ( select a.person, a.annType, max(b.ts) ts from m_phone a left join (select x.* from m_phone_value x, tts where x.ts <= tts.ts) b on a.person = b.person and a.annType = b.annType ,tts where b.ts <= tts.ts group by  a.person ,a.annType ) aa left join m_phone_value bb on aa.person = bb.person and aa.annType = bb.annType and aa.ts = bb.ts) x2 on x2.person = x.person and x2.annType = x.annType left join( select  aa.person ,aa.annType ,bb.smsTemplate from ( select a.person, a.annType, max(b.ts) ts from m_phone a left join (select x.* from m_phone_smsTemplate x, tts where x.ts <= tts.ts) b on a.person = b.person and a.annType = b.annType ,tts where b.ts <= tts.ts group by  a.person ,a.annType ) aa left join m_phone_smsTemplate bb on aa.person = bb.person and aa.annType = bb.annType and aa.ts = bb.ts) x3 on x3.person = x.person and x3.annType = x.annType) select * from xx where xx.person = #{person}")
      List<Phone> loadListAt(@Param("ts") Date ts, @Param("person") long person);
  
  @Insert("insert into m_phone_type (person, annType, type) values (#{person}, #{annType}, #{type})")
      void insType(Phone phone);
  
  @Insert("insert into m_phone_type (person, annType, type) values (#{person}, #{annType}, #{type})")
      void setType(@Param("person") long person, @Param("annType") AnnotationType annType,
          @Param("type") String type);
  
  @Insert("insert into m_phone_value (person, annType, value) values (#{person}, #{annType}, #{value})")
      void insValue(Phone phone);
  
  @Insert("insert into m_phone_value (person, annType, value) values (#{person}, #{annType}, #{value})")
      void setValue(@Param("person") long person, @Param("annType") AnnotationType annType,
          @Param("value") String value);
  
  @Insert("insert into m_phone_smsTemplate (person, annType, smsTemplate) values (#{person}, #{annType}, #{smsTemplate})")
      void insSmsTemplate(Phone phone);
  
  @Insert("insert into m_phone_smsTemplate (person, annType, smsTemplate) values (#{person}, #{annType}, #{smsTemplate})")
      void setSmsTemplate(@Param("person") long person, @Param("annType") AnnotationType annType,
          @Param("smsTemplate") Long smsTemplate);
}
