package kz.pompei.dao.dsa.dsa.dsa;
import kz.pompei.dbmodelstru.dsa.dsa.dsa.AnnotationType;
import kz.greetgo.gbatis.t.Prm;
import java.util.Date;
import kz.greetgo.gbatis.t.Call;
import kz.pompei.dbmodel.dsa.dsa.dsa.PkbResultParamAnn;
import java.util.List;
import kz.greetgo.sqlmanager.gen.HelloEnum;
import kz.greetgo.gbatis.t.Sele;
public interface PkbResultParamAnnDao {

  @Call("{call p_pkbResultParamAnn (#{pkbResultParam1}, #{pkbResultParam2}, #{name})}")
  void ins(PkbResultParamAnn pkbResultParamAnn);
  @Call("{call p_pkbResultParamAnn (#{pkbResultParam1}, #{pkbResultParam2}, #{name})}")
  void add(@Prm("pkbResultParam1")long pkbResultParam1, @Prm("pkbResultParam2")String pkbResultParam2, @Prm("name")String name);
  @Sele("select * from v_pkbResultParamAnn where pkbResultParam1 = #{pkbResultParam1} and pkbResultParam2 = #{pkbResultParam2} and name = #{name}")
  PkbResultParamAnn load(@Prm("pkbResultParam1")long pkbResultParam1, @Prm("pkbResultParam2")String pkbResultParam2, @Prm("name")String name);
  @Sele("select * from v_pkbResultParamAnn where pkbResultParam1 = #{pkbResultParam1} and pkbResultParam2 = #{pkbResultParam2}")
  List<PkbResultParamAnn> loadList(@Prm("pkbResultParam1")long pkbResultParam1, @Prm("pkbResultParam2")String pkbResultParam2);
  @Sele("with tts as (select #{ts} ts from dual), xx as ( select  x.pkbResultParam1,  x.pkbResultParam2,  x.name,  x.createdAt ,x1.valueStr ,x2.valueInt ,x3.type ,x4.hello ,x5.goodby from (select u.* from m_pkbResultParamAnn u, tts where u.createdAt <= tts.ts) x left join( select pkbResultParam1, pkbResultParam2, name, valueStr from ( select m.*, row_number() over (partition by m.pkbResultParam1, m.pkbResultParam2, m.name order by m.ts desc ) as rn__ from m_pkbResultParamAnn_valueStr m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.pkbResultParam1 = x.pkbResultParam1 and x1.pkbResultParam2 = x.pkbResultParam2 and x1.name = x.name left join( select pkbResultParam1, pkbResultParam2, name, valueInt from ( select m.*, row_number() over (partition by m.pkbResultParam1, m.pkbResultParam2, m.name order by m.ts desc ) as rn__ from m_pkbResultParamAnn_valueInt m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.pkbResultParam1 = x.pkbResultParam1 and x2.pkbResultParam2 = x.pkbResultParam2 and x2.name = x.name left join( select pkbResultParam1, pkbResultParam2, name, type from ( select m.*, row_number() over (partition by m.pkbResultParam1, m.pkbResultParam2, m.name order by m.ts desc ) as rn__ from m_pkbResultParamAnn_type m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x3 on x3.pkbResultParam1 = x.pkbResultParam1 and x3.pkbResultParam2 = x.pkbResultParam2 and x3.name = x.name left join( select pkbResultParam1, pkbResultParam2, name, hello from ( select m.*, row_number() over (partition by m.pkbResultParam1, m.pkbResultParam2, m.name order by m.ts desc ) as rn__ from m_pkbResultParamAnn_hello m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x4 on x4.pkbResultParam1 = x.pkbResultParam1 and x4.pkbResultParam2 = x.pkbResultParam2 and x4.name = x.name left join( select pkbResultParam1, pkbResultParam2, name, goodby from ( select m.*, row_number() over (partition by m.pkbResultParam1, m.pkbResultParam2, m.name order by m.ts desc ) as rn__ from m_pkbResultParamAnn_goodby m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x5 on x5.pkbResultParam1 = x.pkbResultParam1 and x5.pkbResultParam2 = x.pkbResultParam2 and x5.name = x.name) select * from xx where xx.pkbResultParam1 = #{pkbResultParam1} and xx.pkbResultParam2 = #{pkbResultParam2} and xx.name = #{name}")
  PkbResultParamAnn loadAt(@Prm("ts")Date ts, @Prm("pkbResultParam1")long pkbResultParam1, @Prm("pkbResultParam2")String pkbResultParam2, @Prm("name")String name);
  @Sele("with tts as (select #{ts} ts from dual), xx as ( select  x.pkbResultParam1,  x.pkbResultParam2,  x.name,  x.createdAt ,x1.valueStr ,x2.valueInt ,x3.type ,x4.hello ,x5.goodby from (select u.* from m_pkbResultParamAnn u, tts where u.createdAt <= tts.ts) x left join( select pkbResultParam1, pkbResultParam2, name, valueStr from ( select m.*, row_number() over (partition by m.pkbResultParam1, m.pkbResultParam2, m.name order by m.ts desc ) as rn__ from m_pkbResultParamAnn_valueStr m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.pkbResultParam1 = x.pkbResultParam1 and x1.pkbResultParam2 = x.pkbResultParam2 and x1.name = x.name left join( select pkbResultParam1, pkbResultParam2, name, valueInt from ( select m.*, row_number() over (partition by m.pkbResultParam1, m.pkbResultParam2, m.name order by m.ts desc ) as rn__ from m_pkbResultParamAnn_valueInt m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.pkbResultParam1 = x.pkbResultParam1 and x2.pkbResultParam2 = x.pkbResultParam2 and x2.name = x.name left join( select pkbResultParam1, pkbResultParam2, name, type from ( select m.*, row_number() over (partition by m.pkbResultParam1, m.pkbResultParam2, m.name order by m.ts desc ) as rn__ from m_pkbResultParamAnn_type m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x3 on x3.pkbResultParam1 = x.pkbResultParam1 and x3.pkbResultParam2 = x.pkbResultParam2 and x3.name = x.name left join( select pkbResultParam1, pkbResultParam2, name, hello from ( select m.*, row_number() over (partition by m.pkbResultParam1, m.pkbResultParam2, m.name order by m.ts desc ) as rn__ from m_pkbResultParamAnn_hello m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x4 on x4.pkbResultParam1 = x.pkbResultParam1 and x4.pkbResultParam2 = x.pkbResultParam2 and x4.name = x.name left join( select pkbResultParam1, pkbResultParam2, name, goodby from ( select m.*, row_number() over (partition by m.pkbResultParam1, m.pkbResultParam2, m.name order by m.ts desc ) as rn__ from m_pkbResultParamAnn_goodby m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x5 on x5.pkbResultParam1 = x.pkbResultParam1 and x5.pkbResultParam2 = x.pkbResultParam2 and x5.name = x.name) select * from xx where xx.pkbResultParam1 = #{pkbResultParam1} and xx.pkbResultParam2 = #{pkbResultParam2}")
  List<PkbResultParamAnn> loadListAt(@Prm("ts")Date ts, @Prm("pkbResultParam1")long pkbResultParam1, @Prm("pkbResultParam2")String pkbResultParam2);
  @Call("{call p_pkbResultParamAnn_valueStr (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{valueStr})}")
  void insValueStr(PkbResultParamAnn pkbResultParamAnn);
  @Call("{call p_pkbResultParamAnn_valueStr (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{valueStr})}")
  void setValueStr(@Prm("pkbResultParam1")long pkbResultParam1, @Prm("pkbResultParam2")String pkbResultParam2, @Prm("name")String name, @Prm("valueStr")String valueStr);
  @Call("{call p_pkbResultParamAnn_valueInt (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{valueInt})}")
  void insValueInt(PkbResultParamAnn pkbResultParamAnn);
  @Call("{call p_pkbResultParamAnn_valueInt (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{valueInt})}")
  void setValueInt(@Prm("pkbResultParam1")long pkbResultParam1, @Prm("pkbResultParam2")String pkbResultParam2, @Prm("name")String name, @Prm("valueInt")Integer valueInt);
  @Call("{call p_pkbResultParamAnn_type (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{type})}")
  void insType(PkbResultParamAnn pkbResultParamAnn);
  @Call("{call p_pkbResultParamAnn_type (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{type})}")
  void setType(@Prm("pkbResultParam1")long pkbResultParam1, @Prm("pkbResultParam2")String pkbResultParam2, @Prm("name")String name, @Prm("type")AnnotationType type);
  @Call("{call p_pkbResultParamAnn_hello (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{hello})}")
  void insHello(PkbResultParamAnn pkbResultParamAnn);
  @Call("{call p_pkbResultParamAnn_hello (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{hello})}")
  void setHello(@Prm("pkbResultParam1")long pkbResultParam1, @Prm("pkbResultParam2")String pkbResultParam2, @Prm("name")String name, @Prm("hello")HelloEnum hello);
  @Call("{call p_pkbResultParamAnn_goodby (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{goodby})}")
  void insGoodby(PkbResultParamAnn pkbResultParamAnn);
  @Call("{call p_pkbResultParamAnn_goodby (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, moment())}")
  void insGoodbyWithNow(PkbResultParamAnn pkbResultParamAnn);
  @Call("{call p_pkbResultParamAnn_goodby (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{goodby})}")
  void setGoodby(@Prm("pkbResultParam1")long pkbResultParam1, @Prm("pkbResultParam2")String pkbResultParam2, @Prm("name")String name, @Prm("goodby")Date goodby);
  @Call("{call p_pkbResultParamAnn_goodby (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, moment())}")
  void setGoodbyWithNow(@Prm("pkbResultParam1")long pkbResultParam1, @Prm("pkbResultParam2")String pkbResultParam2, @Prm("name")String name);
@Sele("select * from v_pkbResultParamAnn order by valueStr, valueInt")
List<PkbResultParamAnn> selectAll();
}
