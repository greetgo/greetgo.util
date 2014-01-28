package kz.pompei.dao;

import kz.pompei.dbmodel.PkbResult;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.annotations.Options;

public interface PkbResultDao {
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbResult (#{pkbResult})}")
  void ins(PkbResult pkbResult);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbResult (#{pkbResult})}")
  void add(@Param("pkbResult") long pkbResult);
  
  @Select("select * from v_pkbResult where pkbResult = #{pkbResult}")
  PkbResult load(@Param("pkbResult") long pkbResult);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.pkbResult,  x.createdAt ,x1.isOK ,x2.xmlContent from (select u.* from m_pkbResult u, tts where u.createdAt <= tts.ts) x left join( select pkbResult, isOK from ( select m.*, row_number() over (partition by m.pkbResult order by m.ts desc ) as rn__ from m_pkbResult_isOK m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.pkbResult = x.pkbResult left join( select pkbResult, xmlContent from ( select m.*, row_number() over (partition by m.pkbResult order by m.ts desc ) as rn__ from m_pkbResult_xmlContent m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.pkbResult = x.pkbResult) select * from xx where xx.pkbResult = #{pkbResult}")
      PkbResult loadAt(@Param("ts") Date ts, @Param("pkbResult") long pkbResult);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbResult_isOK (#{pkbResult}, #{isOKInt})}")
  void insIsOK(PkbResult pkbResult);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbResult_isOK (#{pkbResult}, #{isOKInt})}")
  void setIsOK(@Param("pkbResult") long pkbResult, @Param("isOKInt") int isOKInt);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbResult_xmlContent (#{pkbResult}, #{xmlContent})}")
  void insXmlContent(PkbResult pkbResult);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbResult_xmlContent (#{pkbResult}, #{xmlContent})}")
  void setXmlContent(@Param("pkbResult") long pkbResult, @Param("xmlContent") String xmlContent);
}
