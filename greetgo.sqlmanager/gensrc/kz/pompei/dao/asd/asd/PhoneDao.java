package kz.pompei.dao.asd.asd;

import kz.pompei.dbmodelstru.dsa.dsa.dsa.AnnotationType;
import org.apache.ibatis.annotations.Select;
import kz.pompei.dbmodel.asd.asd.Phone;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import java.util.List;
import org.apache.ibatis.annotations.Options;

public interface PhoneDao {
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_phone (#{person}, #{annType})}")
  void ins(Phone phone);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_phone (#{person}, #{annType})}")
  void add(@Param("person") long person, @Param("annType") AnnotationType annType);
  
  @Select("select * from v_phone where person = #{person} and annType = #{annType}")
  Phone load(@Param("person") long person, @Param("annType") AnnotationType annType);
  
  @Select("select * from v_phone where person = #{person}")
  List<Phone> loadList(@Param("person") long person);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.person,  x.annType,  x.createdAt ,x1.type ,x2.value ,x3.smsTemplate from (select u.* from m_phone u, tts where u.createdAt <= tts.ts) x left join( select person, annType, type from ( select m.*, row_number() over (partition by m.person, m.annType order by m.ts desc ) as rn__ from m_phone_type m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.person = x.person and x1.annType = x.annType left join( select person, annType, value from ( select m.*, row_number() over (partition by m.person, m.annType order by m.ts desc ) as rn__ from m_phone_value m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.person = x.person and x2.annType = x.annType left join( select person, annType, smsTemplate from ( select m.*, row_number() over (partition by m.person, m.annType order by m.ts desc ) as rn__ from m_phone_smsTemplate m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x3 on x3.person = x.person and x3.annType = x.annType) select * from xx where xx.person = #{person} and xx.annType = #{annType}")
      Phone loadAt(@Param("ts") Date ts, @Param("person") long person,
          @Param("annType") AnnotationType annType);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.person,  x.annType,  x.createdAt ,x1.type ,x2.value ,x3.smsTemplate from (select u.* from m_phone u, tts where u.createdAt <= tts.ts) x left join( select person, annType, type from ( select m.*, row_number() over (partition by m.person, m.annType order by m.ts desc ) as rn__ from m_phone_type m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.person = x.person and x1.annType = x.annType left join( select person, annType, value from ( select m.*, row_number() over (partition by m.person, m.annType order by m.ts desc ) as rn__ from m_phone_value m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.person = x.person and x2.annType = x.annType left join( select person, annType, smsTemplate from ( select m.*, row_number() over (partition by m.person, m.annType order by m.ts desc ) as rn__ from m_phone_smsTemplate m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x3 on x3.person = x.person and x3.annType = x.annType) select * from xx where xx.person = #{person}")
      List<Phone> loadListAt(@Param("ts") Date ts, @Param("person") long person);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_phone_type (#{person}, #{annType}, #{type})}")
  void insType(Phone phone);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_phone_type (#{person}, #{annType}, #{type})}")
  void setType(@Param("person") long person, @Param("annType") AnnotationType annType,
      @Param("type") String type);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_phone_value (#{person}, #{annType}, #{value})}")
  void insValue(Phone phone);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_phone_value (#{person}, #{annType}, #{value})}")
  void setValue(@Param("person") long person, @Param("annType") AnnotationType annType,
      @Param("value") String value);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_phone_smsTemplate (#{person}, #{annType}, #{smsTemplate})}")
  void insSmsTemplate(Phone phone);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_phone_smsTemplate (#{person}, #{annType}, #{smsTemplate})}")
  void setSmsTemplate(@Param("person") long person, @Param("annType") AnnotationType annType,
      @Param("smsTemplate") Long smsTemplate);
}
