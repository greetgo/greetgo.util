package kz.pompei.dao.asd.asd;
import kz.greetgo.gbatis.t.Prm;
import java.util.Date;
import kz.greetgo.gbatis.t.Call;
import kz.greetgo.gbatis.t.Sele;
import kz.pompei.dbmodel.asd.asd.NaturalPerson;
public interface NaturalPersonDao {

  @Call("{call p_naturalPerson (#{naturalPerson})}")
  void ins(NaturalPerson naturalPerson);
  @Call("{call p_naturalPerson (#{naturalPerson})}")
  void add(@Prm("naturalPerson")long naturalPerson);
  @Sele("select * from v_naturalPerson where naturalPerson = #{naturalPerson}")
  NaturalPerson load(@Prm("naturalPerson")long naturalPerson);
  @Sele("with tts as (select #{ts} ts from dual), xx as ( select  x.naturalPerson,  x.createdAt ,x1.surname ,x2.name ,x3.birthdate ,x4.iin from (select u.* from m_naturalPerson u, tts where u.createdAt <= tts.ts) x left join( select naturalPerson, surname from ( select m.*, row_number() over (partition by m.naturalPerson order by m.ts desc ) as rn__ from m_naturalPerson_surname m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.naturalPerson = x.naturalPerson left join( select naturalPerson, name from ( select m.*, row_number() over (partition by m.naturalPerson order by m.ts desc ) as rn__ from m_naturalPerson_name m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.naturalPerson = x.naturalPerson left join( select naturalPerson, birthdate from ( select m.*, row_number() over (partition by m.naturalPerson order by m.ts desc ) as rn__ from m_naturalPerson_birthdate m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x3 on x3.naturalPerson = x.naturalPerson left join( select naturalPerson, iin from ( select m.*, row_number() over (partition by m.naturalPerson order by m.ts desc ) as rn__ from m_naturalPerson_iin m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x4 on x4.naturalPerson = x.naturalPerson) select * from xx where xx.naturalPerson = #{naturalPerson}")
  NaturalPerson loadAt(@Prm("ts")Date ts, @Prm("naturalPerson")long naturalPerson);
  @Call("{call p_naturalPerson_surname (#{naturalPerson}, #{surname})}")
  void insSurname(NaturalPerson naturalPerson);
  @Call("{call p_naturalPerson_surname (#{naturalPerson}, #{surname})}")
  void setSurname(@Prm("naturalPerson")long naturalPerson, @Prm("surname")String surname);
  @Call("{call p_naturalPerson_name (#{naturalPerson}, #{name})}")
  void insName(NaturalPerson naturalPerson);
  @Call("{call p_naturalPerson_name (#{naturalPerson}, #{name})}")
  void setName(@Prm("naturalPerson")long naturalPerson, @Prm("name")String name);
  @Call("{call p_naturalPerson_birthdate (#{naturalPerson}, #{birthdate})}")
  void insBirthdate(NaturalPerson naturalPerson);
  @Call("{call p_naturalPerson_birthdate (#{naturalPerson}, moment())}")
  void insBirthdateWithNow(NaturalPerson naturalPerson);
  @Call("{call p_naturalPerson_birthdate (#{naturalPerson}, #{birthdate})}")
  void setBirthdate(@Prm("naturalPerson")long naturalPerson, @Prm("birthdate")Date birthdate);
  @Call("{call p_naturalPerson_birthdate (#{naturalPerson}, moment())}")
  void setBirthdateWithNow(@Prm("naturalPerson")long naturalPerson);
  @Call("{call p_naturalPerson_iin (#{naturalPerson}, #{iin})}")
  void insIin(NaturalPerson naturalPerson);
  @Call("{call p_naturalPerson_iin (#{naturalPerson}, #{iin})}")
  void setIin(@Prm("naturalPerson")long naturalPerson, @Prm("iin")String iin);
}
