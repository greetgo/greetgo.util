package kz.pompei.dao.asd.asd;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Insert;
import java.util.Date;
import kz.pompei.dbmodel.asd.asd.NaturalPerson;

public interface NaturalPersonDao {
  
  @Insert("insert into m_naturalPerson (naturalPerson) values (#{naturalPerson})")
  void ins(NaturalPerson naturalPerson);
  
  @Insert("insert into m_naturalPerson (naturalPerson) values (#{naturalPerson})")
  void add(@Param("naturalPerson") long naturalPerson);
  
  @Select("select * from v_naturalPerson where naturalPerson = #{naturalPerson}")
  NaturalPerson load(@Param("naturalPerson") long naturalPerson);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.naturalPerson,  x.createdAt ,x1.surname ,x2.name ,x3.birthdate ,x4.iin from (select u.* from m_naturalPerson u, tts where u.createdAt <= tts.ts) x left join( select  aa.naturalPerson ,bb.surname from ( select a.naturalPerson, max(b.ts) ts from m_naturalPerson a left join (select x.* from m_naturalPerson_surname x, tts where x.ts <= tts.ts) b on a.naturalPerson = b.naturalPerson ,tts where b.ts <= tts.ts group by  a.naturalPerson ) aa left join m_naturalPerson_surname bb on aa.naturalPerson = bb.naturalPerson and aa.ts = bb.ts) x1 on x1.naturalPerson = x.naturalPerson left join( select  aa.naturalPerson ,bb.name from ( select a.naturalPerson, max(b.ts) ts from m_naturalPerson a left join (select x.* from m_naturalPerson_name x, tts where x.ts <= tts.ts) b on a.naturalPerson = b.naturalPerson ,tts where b.ts <= tts.ts group by  a.naturalPerson ) aa left join m_naturalPerson_name bb on aa.naturalPerson = bb.naturalPerson and aa.ts = bb.ts) x2 on x2.naturalPerson = x.naturalPerson left join( select  aa.naturalPerson ,bb.birthdate from ( select a.naturalPerson, max(b.ts) ts from m_naturalPerson a left join (select x.* from m_naturalPerson_birthdate x, tts where x.ts <= tts.ts) b on a.naturalPerson = b.naturalPerson ,tts where b.ts <= tts.ts group by  a.naturalPerson ) aa left join m_naturalPerson_birthdate bb on aa.naturalPerson = bb.naturalPerson and aa.ts = bb.ts) x3 on x3.naturalPerson = x.naturalPerson left join( select  aa.naturalPerson ,bb.iin from ( select a.naturalPerson, max(b.ts) ts from m_naturalPerson a left join (select x.* from m_naturalPerson_iin x, tts where x.ts <= tts.ts) b on a.naturalPerson = b.naturalPerson ,tts where b.ts <= tts.ts group by  a.naturalPerson ) aa left join m_naturalPerson_iin bb on aa.naturalPerson = bb.naturalPerson and aa.ts = bb.ts) x4 on x4.naturalPerson = x.naturalPerson) select * from xx where xx.naturalPerson = #{naturalPerson}")
      NaturalPerson loadAt(@Param("ts") Date ts, @Param("naturalPerson") long naturalPerson);
  
  @Insert("insert into m_naturalPerson_surname (naturalPerson, surname) values (#{naturalPerson}, #{surname})")
      void insSurname(NaturalPerson naturalPerson);
  
  @Insert("insert into m_naturalPerson_surname (naturalPerson, surname) values (#{naturalPerson}, #{surname})")
      void setSurname(@Param("naturalPerson") long naturalPerson, @Param("surname") String surname);
  
  @Insert("insert into m_naturalPerson_name (naturalPerson, name) values (#{naturalPerson}, #{name})")
      void insName(NaturalPerson naturalPerson);
  
  @Insert("insert into m_naturalPerson_name (naturalPerson, name) values (#{naturalPerson}, #{name})")
      void setName(@Param("naturalPerson") long naturalPerson, @Param("name") String name);
  
  @Insert("insert into m_naturalPerson_birthdate (naturalPerson, birthdate) values (#{naturalPerson}, #{birthdate})")
      void insBirthdate(NaturalPerson naturalPerson);
  
  @Insert("insert into m_naturalPerson_birthdate (naturalPerson, birthdate) values (#{naturalPerson}, current_timestamp)")
      void insBirthdateWithNow(NaturalPerson naturalPerson);
  
  @Insert("insert into m_naturalPerson_birthdate (naturalPerson, birthdate) values (#{naturalPerson}, #{birthdate})")
      void setBirthdate(@Param("naturalPerson") long naturalPerson,
          @Param("birthdate") Date birthdate);
  
  @Insert("insert into m_naturalPerson_birthdate (naturalPerson, birthdate) values (#{naturalPerson}, current_timestamp)")
      void setBirthdateWithNow(@Param("naturalPerson") long naturalPerson);
  
  @Insert("insert into m_naturalPerson_iin (naturalPerson, iin) values (#{naturalPerson}, #{iin})")
  void insIin(NaturalPerson naturalPerson);
  
  @Insert("insert into m_naturalPerson_iin (naturalPerson, iin) values (#{naturalPerson}, #{iin})")
  void setIin(@Param("naturalPerson") long naturalPerson, @Param("iin") String iin);
}
