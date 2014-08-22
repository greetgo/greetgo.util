package kz.pompei.dao.asd.asd;
import kz.greetgo.gbatis.t.Prm;
import java.util.Date;
import kz.greetgo.gbatis.t.Call;
import kz.pompei.dbmodelstru.dsa.dsa.dsa.Person_type;
import kz.greetgo.gbatis.t.Sele;
import kz.pompei.dbmodel.asd.asd.Person;
public interface PersonDao {
  long next();

  @Call("{call p_person (#{person})}")
  void ins(Person person);
  @Call("{call p_person (#{person})}")
  void add(@Prm("person")long person);
  @Sele("select * from v_person where person = #{person}")
  Person load(@Prm("person")long person);
  @Sele("with tts as (select #{ts} ts from dual), xx as ( select  x.person,  x.createdAt ,x1.type ,x2.address from (select u.* from m_person u, tts where u.createdAt <= tts.ts) x left join( select person, type from ( select m.*, row_number() over (partition by m.person order by m.ts desc ) as rn__ from m_person_type m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.person = x.person left join( select person, address from ( select m.*, row_number() over (partition by m.person order by m.ts desc ) as rn__ from m_person_address m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.person = x.person) select * from xx where xx.person = #{person}")
  Person loadAt(@Prm("ts")Date ts, @Prm("person")long person);
  @Call("{call p_person_type (#{person}, #{type})}")
  void insType(Person person);
  @Call("{call p_person_type (#{person}, #{type})}")
  void setType(@Prm("person")long person, @Prm("type")Person_type type);
  @Call("{call p_person_address (#{person}, #{address})}")
  void insAddress(Person person);
  @Call("{call p_person_address (#{person}, #{address})}")
  void setAddress(@Prm("person")long person, @Prm("address")Long address);
}
