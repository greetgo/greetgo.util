package kz.pompei.dao;

import kz.pompei.dbmodel.PkbResultParam;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import java.util.List;
import org.apache.ibatis.annotations.Options;

public interface PkbResultParamDao {
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbResultParam (#{pkbResult}, #{name})}")
  void ins(PkbResultParam pkbResultParam);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbResultParam (#{pkbResult}, #{name})}")
  void add(@Param("pkbResult") long pkbResult, @Param("name") String name);
  
  @Select("select * from v_pkbResultParam where pkbResult = #{pkbResult} and name = #{name}")
  PkbResultParam load(@Param("pkbResult") long pkbResult, @Param("name") String name);
  
  @Select("select * from v_pkbResultParam where pkbResult = #{pkbResult}")
  List<PkbResultParam> loadList(@Param("pkbResult") long pkbResult);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.pkbResult,  x.name,  x.createdAt ,x1.value from (select u.* from m_pkbResultParam u, tts where u.createdAt <= tts.ts) x left join( select pkbResult, name, value from ( select m.*, row_number() over (partition by m.pkbResult, m.name order by m.ts desc ) as rn__ from m_pkbResultParam_value m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.pkbResult = x.pkbResult and x1.name = x.name) select * from xx where xx.pkbResult = #{pkbResult} and xx.name = #{name}")
      PkbResultParam loadAt(@Param("ts") Date ts, @Param("pkbResult") long pkbResult,
          @Param("name") String name);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.pkbResult,  x.name,  x.createdAt ,x1.value from (select u.* from m_pkbResultParam u, tts where u.createdAt <= tts.ts) x left join( select pkbResult, name, value from ( select m.*, row_number() over (partition by m.pkbResult, m.name order by m.ts desc ) as rn__ from m_pkbResultParam_value m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.pkbResult = x.pkbResult and x1.name = x.name) select * from xx where xx.pkbResult = #{pkbResult}")
      List<PkbResultParam> loadListAt(@Param("ts") Date ts, @Param("pkbResult") long pkbResult);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbResultParam_value (#{pkbResult}, #{name}, #{value})}")
  void insValue(PkbResultParam pkbResultParam);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbResultParam_value (#{pkbResult}, #{name}, #{value})}")
  void setValue(@Param("pkbResult") long pkbResult, @Param("name") String name,
      @Param("value") String value);
  
  @Select("select * from v_pkbResultParam order by value, name,")
  List<PkbResultParam> selectAll();
}
