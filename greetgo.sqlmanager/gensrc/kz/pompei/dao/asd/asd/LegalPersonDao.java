package kz.pompei.dao.asd.asd;
import kz.pompei.dbmodel.asd.asd.LegalPerson;
import kz.greetgo.gbatis.t.Prm;
import java.util.Date;
import kz.greetgo.gbatis.t.Call;
import kz.greetgo.gbatis.t.Sele;
public interface LegalPersonDao {

  @Call("{call p_legalPerson (#{legalPerson})}")
  void ins(LegalPerson legalPerson);
  @Call("{call p_legalPerson (#{legalPerson})}")
  void add(@Prm("legalPerson")long legalPerson);
  @Sele("select * from v_legalPerson where legalPerson = #{legalPerson}")
  LegalPerson load(@Prm("legalPerson")long legalPerson);
  @Sele("with tts as (select #{ts} ts from dual), xx as ( select  x.legalPerson,  x.createdAt ,x1.name ,x2.fullname ,x3.bin from (select u.* from m_legalPerson u, tts where u.createdAt <= tts.ts) x left join( select legalPerson, name from ( select m.*, row_number() over (partition by m.legalPerson order by m.ts desc ) as rn__ from m_legalPerson_name m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.legalPerson = x.legalPerson left join( select legalPerson, fullname from ( select m.*, row_number() over (partition by m.legalPerson order by m.ts desc ) as rn__ from m_legalPerson_fullname m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.legalPerson = x.legalPerson left join( select legalPerson, bin from ( select m.*, row_number() over (partition by m.legalPerson order by m.ts desc ) as rn__ from m_legalPerson_bin m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x3 on x3.legalPerson = x.legalPerson) select * from xx where xx.legalPerson = #{legalPerson}")
  LegalPerson loadAt(@Prm("ts")Date ts, @Prm("legalPerson")long legalPerson);
  @Call("{call p_legalPerson_name (#{legalPerson}, #{name})}")
  void insName(LegalPerson legalPerson);
  @Call("{call p_legalPerson_name (#{legalPerson}, #{name})}")
  void setName(@Prm("legalPerson")long legalPerson, @Prm("name")String name);
  @Call("{call p_legalPerson_fullname (#{legalPerson}, #{fullname})}")
  void insFullname(LegalPerson legalPerson);
  @Call("{call p_legalPerson_fullname (#{legalPerson}, #{fullname})}")
  void setFullname(@Prm("legalPerson")long legalPerson, @Prm("fullname")String fullname);
  @Call("{call p_legalPerson_bin (#{legalPerson}, #{bin})}")
  void insBin(LegalPerson legalPerson);
  @Call("{call p_legalPerson_bin (#{legalPerson}, #{bin})}")
  void setBin(@Prm("legalPerson")long legalPerson, @Prm("bin")String bin);
}
