package kz.pompei.dao.dsa.dsa.dsa;

import org.apache.ibatis.annotations.Select;
import kz.pompei.dbmodel.dsa.dsa.dsa.Asd;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.annotations.Options;

public interface AsdDao {
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_asd (#{asd})}")
  void ins(Asd asd);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_asd (#{asd})}")
  void add(@Param("asd") String asd);
  
  @Select("select * from v_asd where asd = #{asd}")
  Asd load(@Param("asd") String asd);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.asd,  x.createdAt ,x1.name ,x2.wow1 ,x2.wow2 ,x2.wow3 from (select u.* from m_asd u, tts where u.createdAt <= tts.ts) x left join( select asd, name from ( select m.*, row_number() over (partition by m.asd order by m.ts desc ) as rn__ from m_asd_name m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.asd = x.asd left join( select asd, wow1, wow2, wow3 from ( select m.*, row_number() over (partition by m.asd order by m.ts desc ) as rn__ from m_asd_wow m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.asd = x.asd) select * from xx where xx.asd = #{asd}")
      Asd loadAt(@Param("ts") Date ts, @Param("asd") String asd);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_asd_name (#{asd}, #{name})}")
  void insName(Asd asd);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_asd_name (#{asd}, #{name})}")
  void setName(@Param("asd") String asd, @Param("name") String name);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_asd_wow (#{asd}, #{wow1}, #{wow2}, #{wow3})}")
  void insWow(Asd asd);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_asd_wow (#{asd}, #{wow1}, #{wow2}, #{wow3})}")
  void setWow(@Param("asd") String asd, @Param("wow1") Long wow1, @Param("wow2") String wow2,
      @Param("wow3") String wow3);
}
