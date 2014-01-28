package kz.pompei.dao;
import kz.pompei.dbmodel.PkbResult;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Insert;
import java.util.Date;
public interface PkbResultDao {

  @Insert("insert into m_pkbResult (pkbResult) values (#{pkbResult})")
  void ins(PkbResult pkbResult);
  @Insert("insert into m_pkbResult (pkbResult) values (#{pkbResult})")
  void add(@Param("pkbResult")long pkbResult);
  @Select("select * from v_pkbResult where pkbResult = #{pkbResult}")
  PkbResult load(@Param("pkbResult")long pkbResult);
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.pkbResult,  x.createdAt ,x1.isOK ,x2.xmlContent from (select u.* from m_pkbResult u, tts where u.createdAt <= tts.ts) x left join( select  aa.pkbResult ,bb.isOK from ( select a.pkbResult, max(b.ts) ts from m_pkbResult a left join (select x.* from m_pkbResult_isOK x, tts where x.ts <= tts.ts) b on a.pkbResult = b.pkbResult ,tts where b.ts <= tts.ts group by  a.pkbResult ) aa left join m_pkbResult_isOK bb on aa.pkbResult = bb.pkbResult and aa.ts = bb.ts) x1 on x1.pkbResult = x.pkbResult left join( select  aa.pkbResult ,bb.xmlContent from ( select a.pkbResult, max(b.ts) ts from m_pkbResult a left join (select x.* from m_pkbResult_xmlContent x, tts where x.ts <= tts.ts) b on a.pkbResult = b.pkbResult ,tts where b.ts <= tts.ts group by  a.pkbResult ) aa left join m_pkbResult_xmlContent bb on aa.pkbResult = bb.pkbResult and aa.ts = bb.ts) x2 on x2.pkbResult = x.pkbResult) select * from xx where xx.pkbResult = #{pkbResult}")
  PkbResult loadAt(@Param("ts")Date ts, @Param("pkbResult")long pkbResult);
  @Insert("insert into m_pkbResult_isOK (pkbResult, isOK) values (#{pkbResult}, #{isOKInt})")
  void insIsOK(PkbResult pkbResult);
  @Insert("insert into m_pkbResult_isOK (pkbResult, isOK) values (#{pkbResult}, #{isOKInt})")
  void setIsOK(@Param("pkbResult")long pkbResult, @Param("isOKInt") int isOKInt);
  @Insert("insert into m_pkbResult_xmlContent (pkbResult, xmlContent) values (#{pkbResult}, #{xmlContent})")
  void insXmlContent(PkbResult pkbResult);
  @Insert("insert into m_pkbResult_xmlContent (pkbResult, xmlContent) values (#{pkbResult}, #{xmlContent})")
  void setXmlContent(@Param("pkbResult")long pkbResult, @Param("xmlContent")String xmlContent);
}
