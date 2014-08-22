package kz.pompei.dao.asd.asd;
import kz.pompei.dbmodelstru.dsa.dsa.dsa.AnnotationType;
import kz.pompei.dbmodel.asd.asd.Phone;
import kz.greetgo.gbatis.t.Prm;
import java.util.Date;
import kz.greetgo.gbatis.t.Call;
import java.util.List;
import kz.greetgo.gbatis.t.Sele;
public interface PhoneDao {

  @Call("{call p_phone (#{person}, #{annType})}")
  void ins(Phone phone);
  @Call("{call p_phone (#{person}, #{annType})}")
  void add(@Prm("person")long person, @Prm("annType")AnnotationType annType);
  @Sele("select * from v_phone where person = #{person} and annType = #{annType}")
  Phone load(@Prm("person")long person, @Prm("annType")AnnotationType annType);
  @Sele("select * from v_phone where person = #{person}")
  List<Phone> loadList(@Prm("person")long person);
  @Sele("with tts as (select #{ts} ts from dual), xx as ( select  x.person,  x.annType,  x.createdAt ,x1.type ,x2.value ,x3.smsTemplate from (select u.* from m_phone u, tts where u.createdAt <= tts.ts) x left join( select person, annType, type from ( select m.*, row_number() over (partition by m.person, m.annType order by m.ts desc ) as rn__ from m_phone_type m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.person = x.person and x1.annType = x.annType left join( select person, annType, value from ( select m.*, row_number() over (partition by m.person, m.annType order by m.ts desc ) as rn__ from m_phone_value m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.person = x.person and x2.annType = x.annType left join( select person, annType, smsTemplate from ( select m.*, row_number() over (partition by m.person, m.annType order by m.ts desc ) as rn__ from m_phone_smsTemplate m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x3 on x3.person = x.person and x3.annType = x.annType) select * from xx where xx.person = #{person} and xx.annType = #{annType}")
  Phone loadAt(@Prm("ts")Date ts, @Prm("person")long person, @Prm("annType")AnnotationType annType);
  @Sele("with tts as (select #{ts} ts from dual), xx as ( select  x.person,  x.annType,  x.createdAt ,x1.type ,x2.value ,x3.smsTemplate from (select u.* from m_phone u, tts where u.createdAt <= tts.ts) x left join( select person, annType, type from ( select m.*, row_number() over (partition by m.person, m.annType order by m.ts desc ) as rn__ from m_phone_type m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.person = x.person and x1.annType = x.annType left join( select person, annType, value from ( select m.*, row_number() over (partition by m.person, m.annType order by m.ts desc ) as rn__ from m_phone_value m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.person = x.person and x2.annType = x.annType left join( select person, annType, smsTemplate from ( select m.*, row_number() over (partition by m.person, m.annType order by m.ts desc ) as rn__ from m_phone_smsTemplate m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x3 on x3.person = x.person and x3.annType = x.annType) select * from xx where xx.person = #{person}")
  List<Phone> loadListAt(@Prm("ts")Date ts, @Prm("person")long person);
  @Call("{call p_phone_type (#{person}, #{annType}, #{type})}")
  void insType(Phone phone);
  @Call("{call p_phone_type (#{person}, #{annType}, #{type})}")
  void setType(@Prm("person")long person, @Prm("annType")AnnotationType annType, @Prm("type")String type);
  @Call("{call p_phone_value (#{person}, #{annType}, #{value})}")
  void insValue(Phone phone);
  @Call("{call p_phone_value (#{person}, #{annType}, #{value})}")
  void setValue(@Prm("person")long person, @Prm("annType")AnnotationType annType, @Prm("value")String value);
  @Call("{call p_phone_smsTemplate (#{person}, #{annType}, #{smsTemplate})}")
  void insSmsTemplate(Phone phone);
  @Call("{call p_phone_smsTemplate (#{person}, #{annType}, #{smsTemplate})}")
  void setSmsTemplate(@Prm("person")long person, @Prm("annType")AnnotationType annType, @Prm("smsTemplate")Long smsTemplate);
}
