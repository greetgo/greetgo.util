package kz.pompei.dao;
import kz.pompei.dbmodel.PkbResultParam;
import kz.greetgo.gbatis.t.Prm;
import java.util.Date;
import kz.greetgo.gbatis.t.Call;
import java.util.List;
import kz.greetgo.gbatis.t.Sele;
public interface PkbResultParamDao {

  @Call("{call p_pkbResultParam (#{pkbResult}, #{name})}")
  void ins(PkbResultParam pkbResultParam);
  @Call("{call p_pkbResultParam (#{pkbResult}, #{name})}")
  void add(@Prm("pkbResult")long pkbResult, @Prm("name")String name);
  @Sele("select * from v_pkbResultParam where pkbResult = #{pkbResult} and name = #{name}")
  PkbResultParam load(@Prm("pkbResult")long pkbResult, @Prm("name")String name);
  @Sele("select * from v_pkbResultParam where pkbResult = #{pkbResult}")
  List<PkbResultParam> loadList(@Prm("pkbResult")long pkbResult);
  @Sele("with tts as (select #{ts} ts from dual), xx as ( select  x.pkbResult,  x.name,  x.createdAt ,x1.value from (select u.* from m_pkbResultParam u, tts where u.createdAt <= tts.ts) x left join( select pkbResult, name, value from ( select m.*, row_number() over (partition by m.pkbResult, m.name order by m.ts desc ) as rn__ from m_pkbResultParam_value m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.pkbResult = x.pkbResult and x1.name = x.name) select * from xx where xx.pkbResult = #{pkbResult} and xx.name = #{name}")
  PkbResultParam loadAt(@Prm("ts")Date ts, @Prm("pkbResult")long pkbResult, @Prm("name")String name);
  @Sele("with tts as (select #{ts} ts from dual), xx as ( select  x.pkbResult,  x.name,  x.createdAt ,x1.value from (select u.* from m_pkbResultParam u, tts where u.createdAt <= tts.ts) x left join( select pkbResult, name, value from ( select m.*, row_number() over (partition by m.pkbResult, m.name order by m.ts desc ) as rn__ from m_pkbResultParam_value m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.pkbResult = x.pkbResult and x1.name = x.name) select * from xx where xx.pkbResult = #{pkbResult}")
  List<PkbResultParam> loadListAt(@Prm("ts")Date ts, @Prm("pkbResult")long pkbResult);
  @Call("{call p_pkbResultParam_value (#{pkbResult}, #{name}, #{value})}")
  void insValue(PkbResultParam pkbResultParam);
  @Call("{call p_pkbResultParam_value (#{pkbResult}, #{name}, #{value})}")
  void setValue(@Prm("pkbResult")long pkbResult, @Prm("name")String name, @Prm("value")String value);
@Sele("select * from v_pkbResultParam order by value, name,")
List<PkbResultParam> selectAll();
}
