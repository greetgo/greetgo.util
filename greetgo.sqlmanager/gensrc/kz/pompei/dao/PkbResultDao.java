package kz.pompei.dao;
import kz.pompei.dbmodel.PkbResult;
import kz.greetgo.gbatis.t.Prm;
import java.util.Date;
import kz.greetgo.gbatis.t.Call;
import kz.greetgo.gbatis.t.Sele;
public interface PkbResultDao {

  @Call("{call p_pkbResult (#{pkbResult})}")
  void ins(PkbResult pkbResult);
  @Call("{call p_pkbResult (#{pkbResult})}")
  void add(@Prm("pkbResult")long pkbResult);
  @Sele("select * from v_pkbResult where pkbResult = #{pkbResult}")
  PkbResult load(@Prm("pkbResult")long pkbResult);
  @Sele("with tts as (select #{ts} ts from dual), xx as ( select  x.pkbResult,  x.createdAt ,x1.isOK ,x2.xmlContent from (select u.* from m_pkbResult u, tts where u.createdAt <= tts.ts) x left join( select pkbResult, isOK from ( select m.*, row_number() over (partition by m.pkbResult order by m.ts desc ) as rn__ from m_pkbResult_isOK m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.pkbResult = x.pkbResult left join( select pkbResult, xmlContent from ( select m.*, row_number() over (partition by m.pkbResult order by m.ts desc ) as rn__ from m_pkbResult_xmlContent m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.pkbResult = x.pkbResult) select * from xx where xx.pkbResult = #{pkbResult}")
  PkbResult loadAt(@Prm("ts")Date ts, @Prm("pkbResult")long pkbResult);
  @Call("{call p_pkbResult_isOK (#{pkbResult}, #{isOKInt})}")
  void insIsOK(PkbResult pkbResult);
  @Call("{call p_pkbResult_isOK (#{pkbResult}, #{isOKInt})}")
  void setIsOK(@Prm("pkbResult")long pkbResult, @Prm("isOKInt") int isOKInt);
  @Call("{call p_pkbResult_xmlContent (#{pkbResult}, #{xmlContent})}")
  void insXmlContent(PkbResult pkbResult);
  @Call("{call p_pkbResult_xmlContent (#{pkbResult}, #{xmlContent})}")
  void setXmlContent(@Prm("pkbResult")long pkbResult, @Prm("xmlContent")String xmlContent);
}
