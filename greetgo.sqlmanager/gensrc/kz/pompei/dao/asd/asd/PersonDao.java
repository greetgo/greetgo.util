package kz.pompei.dao.asd.asd;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Insert;
import java.util.Date;
import kz.pompei.dbmodelstru.dsa.dsa.dsa.Person_type;
import kz.pompei.dbmodel.asd.asd.Person;

public interface PersonDao {
  long next();
  
  @Insert("insert into m_person (person) values (#{person})")
  void ins(Person person);
  
  @Insert("insert into m_person (person) values (#{person})")
  void add(@Param("person") long person);
  
  @Select("select * from v_person where person = #{person}")
  Person load(@Param("person") long person);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.person,  x.createdAt ,x1.type ,x2.address from (select u.* from m_person u, tts where u.createdAt <= tts.ts) x left join( select  aa.person ,bb.type from ( select a.person, max(b.ts) ts from m_person a left join (select x.* from m_person_type x, tts where x.ts <= tts.ts) b on a.person = b.person ,tts where b.ts <= tts.ts group by  a.person ) aa left join m_person_type bb on aa.person = bb.person and aa.ts = bb.ts) x1 on x1.person = x.person left join( select  aa.person ,bb.address from ( select a.person, max(b.ts) ts from m_person a left join (select x.* from m_person_address x, tts where x.ts <= tts.ts) b on a.person = b.person ,tts where b.ts <= tts.ts group by  a.person ) aa left join m_person_address bb on aa.person = bb.person and aa.ts = bb.ts) x2 on x2.person = x.person) select * from xx where xx.person = #{person}")
      Person loadAt(@Param("ts") Date ts, @Param("person") long person);
  
  @Insert("insert into m_person_type (person, type) values (#{person}, #{type})")
  void insType(Person person);
  
  @Insert("insert into m_person_type (person, type) values (#{person}, #{type})")
  void setType(@Param("person") long person, @Param("type") Person_type type);
  
  @Insert("insert into m_person_address (person, address) values (#{person}, #{address})")
  void insAddress(Person person);
  
  @Insert("insert into m_person_address (person, address) values (#{person}, #{address})")
  void setAddress(@Param("person") long person, @Param("address") Long address);
}
