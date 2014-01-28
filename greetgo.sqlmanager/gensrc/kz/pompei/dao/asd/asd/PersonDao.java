package kz.pompei.dao.asd.asd;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import kz.pompei.dbmodelstru.dsa.dsa.dsa.Person_type;
import org.apache.ibatis.annotations.Options;
import kz.pompei.dbmodel.asd.asd.Person;

public interface PersonDao {
  long next();
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_person (#{person})}")
  void ins(Person person);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_person (#{person})}")
  void add(@Param("person") long person);
  
  @Select("select * from v_person where person = #{person}")
  Person load(@Param("person") long person);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.person,  x.createdAt ,x1.type ,x2.address from (select u.* from m_person u, tts where u.createdAt <= tts.ts) x left join( select person, type from ( select m.*, row_number() over (partition by m.person order by m.ts desc ) as rn__ from m_person_type m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.person = x.person left join( select person, address from ( select m.*, row_number() over (partition by m.person order by m.ts desc ) as rn__ from m_person_address m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.person = x.person) select * from xx where xx.person = #{person}")
      Person loadAt(@Param("ts") Date ts, @Param("person") long person);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_person_type (#{person}, #{type})}")
  void insType(Person person);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_person_type (#{person}, #{type})}")
  void setType(@Param("person") long person, @Param("type") Person_type type);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_person_address (#{person}, #{address})}")
  void insAddress(Person person);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_person_address (#{person}, #{address})}")
  void setAddress(@Param("person") long person, @Param("address") Long address);
}
