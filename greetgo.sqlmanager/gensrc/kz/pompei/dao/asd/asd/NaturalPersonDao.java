package kz.pompei.dao.asd.asd;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.annotations.Options;
import kz.pompei.dbmodel.asd.asd.NaturalPerson;

public interface NaturalPersonDao {
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_naturalPerson (#{naturalPerson})}")
  void ins(NaturalPerson naturalPerson);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_naturalPerson (#{naturalPerson})}")
  void add(@Param("naturalPerson") long naturalPerson);
  
  @Select("select * from v_naturalPerson where naturalPerson = #{naturalPerson}")
  NaturalPerson load(@Param("naturalPerson") long naturalPerson);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.naturalPerson,  x.createdAt ,x1.surname ,x2.name ,x3.birthdate ,x4.iin from (select u.* from m_naturalPerson u, tts where u.createdAt <= tts.ts) x left join( select naturalPerson, surname from ( select m.*, row_number() over (partition by m.naturalPerson order by m.ts desc ) as rn__ from m_naturalPerson_surname m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.naturalPerson = x.naturalPerson left join( select naturalPerson, name from ( select m.*, row_number() over (partition by m.naturalPerson order by m.ts desc ) as rn__ from m_naturalPerson_name m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.naturalPerson = x.naturalPerson left join( select naturalPerson, birthdate from ( select m.*, row_number() over (partition by m.naturalPerson order by m.ts desc ) as rn__ from m_naturalPerson_birthdate m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x3 on x3.naturalPerson = x.naturalPerson left join( select naturalPerson, iin from ( select m.*, row_number() over (partition by m.naturalPerson order by m.ts desc ) as rn__ from m_naturalPerson_iin m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x4 on x4.naturalPerson = x.naturalPerson) select * from xx where xx.naturalPerson = #{naturalPerson}")
      NaturalPerson loadAt(@Param("ts") Date ts, @Param("naturalPerson") long naturalPerson);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_naturalPerson_surname (#{naturalPerson}, #{surname})}")
  void insSurname(NaturalPerson naturalPerson);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_naturalPerson_surname (#{naturalPerson}, #{surname})}")
  void setSurname(@Param("naturalPerson") long naturalPerson, @Param("surname") String surname);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_naturalPerson_name (#{naturalPerson}, #{name})}")
  void insName(NaturalPerson naturalPerson);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_naturalPerson_name (#{naturalPerson}, #{name})}")
  void setName(@Param("naturalPerson") long naturalPerson, @Param("name") String name);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_naturalPerson_birthdate (#{naturalPerson}, #{birthdate})}")
  void insBirthdate(NaturalPerson naturalPerson);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_naturalPerson_birthdate (#{naturalPerson}, current_timestamp)}")
  void insBirthdateWithNow(NaturalPerson naturalPerson);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_naturalPerson_birthdate (#{naturalPerson}, #{birthdate})}")
  void setBirthdate(@Param("naturalPerson") long naturalPerson, @Param("birthdate") Date birthdate);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_naturalPerson_birthdate (#{naturalPerson}, current_timestamp)}")
  void setBirthdateWithNow(@Param("naturalPerson") long naturalPerson);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_naturalPerson_iin (#{naturalPerson}, #{iin})}")
  void insIin(NaturalPerson naturalPerson);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_naturalPerson_iin (#{naturalPerson}, #{iin})}")
  void setIin(@Param("naturalPerson") long naturalPerson, @Param("iin") String iin);
}
