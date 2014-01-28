package kz.pompei.dao;
import kz.pompei.dbmodel.PkbResultParam;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Insert;
import java.util.Date;
import java.util.List;
public interface PkbResultParamDao {

  @Insert("insert into m_pkbResultParam (pkbResult, name) values (#{pkbResult}, #{name})")
  void ins(PkbResultParam pkbResultParam);
  @Insert("insert into m_pkbResultParam (pkbResult, name) values (#{pkbResult}, #{name})")
  void add(@Param("pkbResult")long pkbResult, @Param("name")String name);
  @Select("select * from v_pkbResultParam where pkbResult = #{pkbResult} and name = #{name}")
  PkbResultParam load(@Param("pkbResult")long pkbResult, @Param("name")String name);
  @Select("select * from v_pkbResultParam where pkbResult = #{pkbResult}")
  List<PkbResultParam> loadList(@Param("pkbResult")long pkbResult);
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.pkbResult,  x.name,  x.createdAt ,x1.value from (select u.* from m_pkbResultParam u, tts where u.createdAt <= tts.ts) x left join( select  aa.pkbResult ,aa.name ,bb.value from ( select a.pkbResult, a.name, max(b.ts) ts from m_pkbResultParam a left join (select x.* from m_pkbResultParam_value x, tts where x.ts <= tts.ts) b on a.pkbResult = b.pkbResult and a.name = b.name ,tts where b.ts <= tts.ts group by  a.pkbResult ,a.name ) aa left join m_pkbResultParam_value bb on aa.pkbResult = bb.pkbResult and aa.name = bb.name and aa.ts = bb.ts) x1 on x1.pkbResult = x.pkbResult and x1.name = x.name) select * from xx where xx.pkbResult = #{pkbResult} and xx.name = #{name}")
  PkbResultParam loadAt(@Param("ts")Date ts, @Param("pkbResult")long pkbResult, @Param("name")String name);
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.pkbResult,  x.name,  x.createdAt ,x1.value from (select u.* from m_pkbResultParam u, tts where u.createdAt <= tts.ts) x left join( select  aa.pkbResult ,aa.name ,bb.value from ( select a.pkbResult, a.name, max(b.ts) ts from m_pkbResultParam a left join (select x.* from m_pkbResultParam_value x, tts where x.ts <= tts.ts) b on a.pkbResult = b.pkbResult and a.name = b.name ,tts where b.ts <= tts.ts group by  a.pkbResult ,a.name ) aa left join m_pkbResultParam_value bb on aa.pkbResult = bb.pkbResult and aa.name = bb.name and aa.ts = bb.ts) x1 on x1.pkbResult = x.pkbResult and x1.name = x.name) select * from xx where xx.pkbResult = #{pkbResult}")
  List<PkbResultParam> loadListAt(@Param("ts")Date ts, @Param("pkbResult")long pkbResult);
  @Insert("insert into m_pkbResultParam_value (pkbResult, name, value) values (#{pkbResult}, #{name}, #{value})")
  void insValue(PkbResultParam pkbResultParam);
  @Insert("insert into m_pkbResultParam_value (pkbResult, name, value) values (#{pkbResult}, #{name}, #{value})")
  void setValue(@Param("pkbResult")long pkbResult, @Param("name")String name, @Param("value")String value);
@Select("select * from v_pkbResultParam order by value, name,")
List<PkbResultParam> selectAll();
}
