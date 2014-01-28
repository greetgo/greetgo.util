package kz.pompei.dao.dsa.dsa.dsa;

import kz.pompei.dbmodelstru.dsa.dsa.dsa.AnnotationType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import kz.pompei.dbmodel.dsa.dsa.dsa.PkbResultParamAnn;
import java.util.List;
import org.apache.ibatis.annotations.Options;
import kz.greetgo.sqlmanager.gen.HelloEnum;

public interface PkbResultParamAnnDao {
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbResultParamAnn (#{pkbResultParam1}, #{pkbResultParam2}, #{name})}")
  void ins(PkbResultParamAnn pkbResultParamAnn);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbResultParamAnn (#{pkbResultParam1}, #{pkbResultParam2}, #{name})}")
  void add(@Param("pkbResultParam1") long pkbResultParam1,
      @Param("pkbResultParam2") String pkbResultParam2, @Param("name") String name);
  
  @Select("select * from v_pkbResultParamAnn where pkbResultParam1 = #{pkbResultParam1} and pkbResultParam2 = #{pkbResultParam2} and name = #{name}")
      PkbResultParamAnn load(@Param("pkbResultParam1") long pkbResultParam1,
          @Param("pkbResultParam2") String pkbResultParam2, @Param("name") String name);
  
  @Select("select * from v_pkbResultParamAnn where pkbResultParam1 = #{pkbResultParam1} and pkbResultParam2 = #{pkbResultParam2}")
      List<PkbResultParamAnn> loadList(@Param("pkbResultParam1") long pkbResultParam1,
          @Param("pkbResultParam2") String pkbResultParam2);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.pkbResultParam1,  x.pkbResultParam2,  x.name,  x.createdAt ,x1.valueStr ,x2.valueInt ,x3.type ,x4.hello ,x5.goodby from (select u.* from m_pkbResultParamAnn u, tts where u.createdAt <= tts.ts) x left join( select pkbResultParam1, pkbResultParam2, name, valueStr from ( select m.*, row_number() over (partition by m.pkbResultParam1, m.pkbResultParam2, m.name order by m.ts desc ) as rn__ from m_pkbResultParamAnn_valueStr m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.pkbResultParam1 = x.pkbResultParam1 and x1.pkbResultParam2 = x.pkbResultParam2 and x1.name = x.name left join( select pkbResultParam1, pkbResultParam2, name, valueInt from ( select m.*, row_number() over (partition by m.pkbResultParam1, m.pkbResultParam2, m.name order by m.ts desc ) as rn__ from m_pkbResultParamAnn_valueInt m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.pkbResultParam1 = x.pkbResultParam1 and x2.pkbResultParam2 = x.pkbResultParam2 and x2.name = x.name left join( select pkbResultParam1, pkbResultParam2, name, type from ( select m.*, row_number() over (partition by m.pkbResultParam1, m.pkbResultParam2, m.name order by m.ts desc ) as rn__ from m_pkbResultParamAnn_type m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x3 on x3.pkbResultParam1 = x.pkbResultParam1 and x3.pkbResultParam2 = x.pkbResultParam2 and x3.name = x.name left join( select pkbResultParam1, pkbResultParam2, name, hello from ( select m.*, row_number() over (partition by m.pkbResultParam1, m.pkbResultParam2, m.name order by m.ts desc ) as rn__ from m_pkbResultParamAnn_hello m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x4 on x4.pkbResultParam1 = x.pkbResultParam1 and x4.pkbResultParam2 = x.pkbResultParam2 and x4.name = x.name left join( select pkbResultParam1, pkbResultParam2, name, goodby from ( select m.*, row_number() over (partition by m.pkbResultParam1, m.pkbResultParam2, m.name order by m.ts desc ) as rn__ from m_pkbResultParamAnn_goodby m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x5 on x5.pkbResultParam1 = x.pkbResultParam1 and x5.pkbResultParam2 = x.pkbResultParam2 and x5.name = x.name) select * from xx where xx.pkbResultParam1 = #{pkbResultParam1} and xx.pkbResultParam2 = #{pkbResultParam2} and xx.name = #{name}")
      PkbResultParamAnn loadAt(@Param("ts") Date ts,
          @Param("pkbResultParam1") long pkbResultParam1,
          @Param("pkbResultParam2") String pkbResultParam2, @Param("name") String name);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.pkbResultParam1,  x.pkbResultParam2,  x.name,  x.createdAt ,x1.valueStr ,x2.valueInt ,x3.type ,x4.hello ,x5.goodby from (select u.* from m_pkbResultParamAnn u, tts where u.createdAt <= tts.ts) x left join( select pkbResultParam1, pkbResultParam2, name, valueStr from ( select m.*, row_number() over (partition by m.pkbResultParam1, m.pkbResultParam2, m.name order by m.ts desc ) as rn__ from m_pkbResultParamAnn_valueStr m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.pkbResultParam1 = x.pkbResultParam1 and x1.pkbResultParam2 = x.pkbResultParam2 and x1.name = x.name left join( select pkbResultParam1, pkbResultParam2, name, valueInt from ( select m.*, row_number() over (partition by m.pkbResultParam1, m.pkbResultParam2, m.name order by m.ts desc ) as rn__ from m_pkbResultParamAnn_valueInt m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.pkbResultParam1 = x.pkbResultParam1 and x2.pkbResultParam2 = x.pkbResultParam2 and x2.name = x.name left join( select pkbResultParam1, pkbResultParam2, name, type from ( select m.*, row_number() over (partition by m.pkbResultParam1, m.pkbResultParam2, m.name order by m.ts desc ) as rn__ from m_pkbResultParamAnn_type m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x3 on x3.pkbResultParam1 = x.pkbResultParam1 and x3.pkbResultParam2 = x.pkbResultParam2 and x3.name = x.name left join( select pkbResultParam1, pkbResultParam2, name, hello from ( select m.*, row_number() over (partition by m.pkbResultParam1, m.pkbResultParam2, m.name order by m.ts desc ) as rn__ from m_pkbResultParamAnn_hello m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x4 on x4.pkbResultParam1 = x.pkbResultParam1 and x4.pkbResultParam2 = x.pkbResultParam2 and x4.name = x.name left join( select pkbResultParam1, pkbResultParam2, name, goodby from ( select m.*, row_number() over (partition by m.pkbResultParam1, m.pkbResultParam2, m.name order by m.ts desc ) as rn__ from m_pkbResultParamAnn_goodby m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x5 on x5.pkbResultParam1 = x.pkbResultParam1 and x5.pkbResultParam2 = x.pkbResultParam2 and x5.name = x.name) select * from xx where xx.pkbResultParam1 = #{pkbResultParam1} and xx.pkbResultParam2 = #{pkbResultParam2}")
      List<PkbResultParamAnn> loadListAt(@Param("ts") Date ts,
          @Param("pkbResultParam1") long pkbResultParam1,
          @Param("pkbResultParam2") String pkbResultParam2);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbResultParamAnn_valueStr (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{valueStr})}")
      void insValueStr(PkbResultParamAnn pkbResultParamAnn);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbResultParamAnn_valueStr (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{valueStr})}")
      void setValueStr(@Param("pkbResultParam1") long pkbResultParam1,
          @Param("pkbResultParam2") String pkbResultParam2, @Param("name") String name,
          @Param("valueStr") String valueStr);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbResultParamAnn_valueInt (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{valueInt})}")
      void insValueInt(PkbResultParamAnn pkbResultParamAnn);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbResultParamAnn_valueInt (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{valueInt})}")
      void setValueInt(@Param("pkbResultParam1") long pkbResultParam1,
          @Param("pkbResultParam2") String pkbResultParam2, @Param("name") String name,
          @Param("valueInt") Integer valueInt);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbResultParamAnn_type (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{type})}")
      void insType(PkbResultParamAnn pkbResultParamAnn);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbResultParamAnn_type (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{type})}")
      void setType(@Param("pkbResultParam1") long pkbResultParam1,
          @Param("pkbResultParam2") String pkbResultParam2, @Param("name") String name,
          @Param("type") AnnotationType type);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbResultParamAnn_hello (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{hello})}")
      void insHello(PkbResultParamAnn pkbResultParamAnn);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbResultParamAnn_hello (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{hello})}")
      void setHello(@Param("pkbResultParam1") long pkbResultParam1,
          @Param("pkbResultParam2") String pkbResultParam2, @Param("name") String name,
          @Param("hello") HelloEnum hello);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbResultParamAnn_goodby (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{goodby})}")
      void insGoodby(PkbResultParamAnn pkbResultParamAnn);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbResultParamAnn_goodby (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, current_timestamp)}")
      void insGoodbyWithNow(PkbResultParamAnn pkbResultParamAnn);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbResultParamAnn_goodby (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{goodby})}")
      void setGoodby(@Param("pkbResultParam1") long pkbResultParam1,
          @Param("pkbResultParam2") String pkbResultParam2, @Param("name") String name,
          @Param("goodby") Date goodby);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbResultParamAnn_goodby (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, current_timestamp)}")
      void setGoodbyWithNow(@Param("pkbResultParam1") long pkbResultParam1,
          @Param("pkbResultParam2") String pkbResultParam2, @Param("name") String name);
  
  @Select("select * from v_pkbResultParamAnn order by valueStr, valueInt")
  List<PkbResultParamAnn> selectAll();
}
