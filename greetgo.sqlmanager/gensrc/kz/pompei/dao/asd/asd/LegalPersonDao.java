package kz.pompei.dao.asd.asd;

import kz.pompei.dbmodel.asd.asd.LegalPerson;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Insert;
import java.util.Date;

public interface LegalPersonDao {
  
  @Insert("insert into m_legalPerson (legalPerson) values (#{legalPerson})")
  void ins(LegalPerson legalPerson);
  
  @Insert("insert into m_legalPerson (legalPerson) values (#{legalPerson})")
  void add(@Param("legalPerson") long legalPerson);
  
  @Select("select * from v_legalPerson where legalPerson = #{legalPerson}")
  LegalPerson load(@Param("legalPerson") long legalPerson);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.legalPerson,  x.createdAt ,x1.name ,x2.fullname ,x3.bin from (select u.* from m_legalPerson u, tts where u.createdAt <= tts.ts) x left join( select  aa.legalPerson ,bb.name from ( select a.legalPerson, max(b.ts) ts from m_legalPerson a left join (select x.* from m_legalPerson_name x, tts where x.ts <= tts.ts) b on a.legalPerson = b.legalPerson ,tts where b.ts <= tts.ts group by  a.legalPerson ) aa left join m_legalPerson_name bb on aa.legalPerson = bb.legalPerson and aa.ts = bb.ts) x1 on x1.legalPerson = x.legalPerson left join( select  aa.legalPerson ,bb.fullname from ( select a.legalPerson, max(b.ts) ts from m_legalPerson a left join (select x.* from m_legalPerson_fullname x, tts where x.ts <= tts.ts) b on a.legalPerson = b.legalPerson ,tts where b.ts <= tts.ts group by  a.legalPerson ) aa left join m_legalPerson_fullname bb on aa.legalPerson = bb.legalPerson and aa.ts = bb.ts) x2 on x2.legalPerson = x.legalPerson left join( select  aa.legalPerson ,bb.bin from ( select a.legalPerson, max(b.ts) ts from m_legalPerson a left join (select x.* from m_legalPerson_bin x, tts where x.ts <= tts.ts) b on a.legalPerson = b.legalPerson ,tts where b.ts <= tts.ts group by  a.legalPerson ) aa left join m_legalPerson_bin bb on aa.legalPerson = bb.legalPerson and aa.ts = bb.ts) x3 on x3.legalPerson = x.legalPerson) select * from xx where xx.legalPerson = #{legalPerson}")
      LegalPerson loadAt(@Param("ts") Date ts, @Param("legalPerson") long legalPerson);
  
  @Insert("insert into m_legalPerson_name (legalPerson, name) values (#{legalPerson}, #{name})")
  void insName(LegalPerson legalPerson);
  
  @Insert("insert into m_legalPerson_name (legalPerson, name) values (#{legalPerson}, #{name})")
  void setName(@Param("legalPerson") long legalPerson, @Param("name") String name);
  
  @Insert("insert into m_legalPerson_fullname (legalPerson, fullname) values (#{legalPerson}, #{fullname})")
      void insFullname(LegalPerson legalPerson);
  
  @Insert("insert into m_legalPerson_fullname (legalPerson, fullname) values (#{legalPerson}, #{fullname})")
      void setFullname(@Param("legalPerson") long legalPerson, @Param("fullname") String fullname);
  
  @Insert("insert into m_legalPerson_bin (legalPerson, bin) values (#{legalPerson}, #{bin})")
  void insBin(LegalPerson legalPerson);
  
  @Insert("insert into m_legalPerson_bin (legalPerson, bin) values (#{legalPerson}, #{bin})")
  void setBin(@Param("legalPerson") long legalPerson, @Param("bin") String bin);
}
