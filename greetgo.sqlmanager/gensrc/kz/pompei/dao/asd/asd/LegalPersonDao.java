package kz.pompei.dao.asd.asd;

import kz.pompei.dbmodel.asd.asd.LegalPerson;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.annotations.Options;

public interface LegalPersonDao {
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_legalPerson (#{legalPerson})}")
  void ins(LegalPerson legalPerson);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_legalPerson (#{legalPerson})}")
  void add(@Param("legalPerson") long legalPerson);
  
  @Select("select * from v_legalPerson where legalPerson = #{legalPerson}")
  LegalPerson load(@Param("legalPerson") long legalPerson);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.legalPerson,  x.createdAt ,x1.name ,x2.fullname ,x3.bin from (select u.* from m_legalPerson u, tts where u.createdAt <= tts.ts) x left join( select legalPerson, name from ( select m.*, row_number() over (partition by m.legalPerson order by m.ts desc ) as rn__ from m_legalPerson_name m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.legalPerson = x.legalPerson left join( select legalPerson, fullname from ( select m.*, row_number() over (partition by m.legalPerson order by m.ts desc ) as rn__ from m_legalPerson_fullname m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.legalPerson = x.legalPerson left join( select legalPerson, bin from ( select m.*, row_number() over (partition by m.legalPerson order by m.ts desc ) as rn__ from m_legalPerson_bin m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x3 on x3.legalPerson = x.legalPerson) select * from xx where xx.legalPerson = #{legalPerson}")
      LegalPerson loadAt(@Param("ts") Date ts, @Param("legalPerson") long legalPerson);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_legalPerson_name (#{legalPerson}, #{name})}")
  void insName(LegalPerson legalPerson);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_legalPerson_name (#{legalPerson}, #{name})}")
  void setName(@Param("legalPerson") long legalPerson, @Param("name") String name);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_legalPerson_fullname (#{legalPerson}, #{fullname})}")
  void insFullname(LegalPerson legalPerson);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_legalPerson_fullname (#{legalPerson}, #{fullname})}")
  void setFullname(@Param("legalPerson") long legalPerson, @Param("fullname") String fullname);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_legalPerson_bin (#{legalPerson}, #{bin})}")
  void insBin(LegalPerson legalPerson);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_legalPerson_bin (#{legalPerson}, #{bin})}")
  void setBin(@Param("legalPerson") long legalPerson, @Param("bin") String bin);
}
