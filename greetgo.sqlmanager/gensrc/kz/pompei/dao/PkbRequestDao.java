package kz.pompei.dao;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Insert;
import java.util.Date;
import kz.pompei.dbmodel.PkbRequest;
public interface PkbRequestDao {
  long next();

  @Insert("insert into m_pkbRequest (pkbRequest) values (#{pkbRequest})")
  void ins(PkbRequest pkbRequest);
  @Insert("insert into m_pkbRequest (pkbRequest) values (#{pkbRequest})")
  void add(@Param("pkbRequest")long pkbRequest);
  @Select("select * from v_pkbRequest where pkbRequest = #{pkbRequest}")
  PkbRequest load(@Param("pkbRequest")long pkbRequest);
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.pkbRequest,  x.createdAt ,x1.contract ,x2.idNumber ,x3.reportCode from (select u.* from m_pkbRequest u, tts where u.createdAt <= tts.ts) x left join( select  aa.pkbRequest ,bb.contract from ( select a.pkbRequest, max(b.ts) ts from m_pkbRequest a left join (select x.* from m_pkbRequest_contract x, tts where x.ts <= tts.ts) b on a.pkbRequest = b.pkbRequest ,tts where b.ts <= tts.ts group by  a.pkbRequest ) aa left join m_pkbRequest_contract bb on aa.pkbRequest = bb.pkbRequest and aa.ts = bb.ts) x1 on x1.pkbRequest = x.pkbRequest left join( select  aa.pkbRequest ,bb.idNumber from ( select a.pkbRequest, max(b.ts) ts from m_pkbRequest a left join (select x.* from m_pkbRequest_idNumber x, tts where x.ts <= tts.ts) b on a.pkbRequest = b.pkbRequest ,tts where b.ts <= tts.ts group by  a.pkbRequest ) aa left join m_pkbRequest_idNumber bb on aa.pkbRequest = bb.pkbRequest and aa.ts = bb.ts) x2 on x2.pkbRequest = x.pkbRequest left join( select  aa.pkbRequest ,bb.reportCode from ( select a.pkbRequest, max(b.ts) ts from m_pkbRequest a left join (select x.* from m_pkbRequest_reportCode x, tts where x.ts <= tts.ts) b on a.pkbRequest = b.pkbRequest ,tts where b.ts <= tts.ts group by  a.pkbRequest ) aa left join m_pkbRequest_reportCode bb on aa.pkbRequest = bb.pkbRequest and aa.ts = bb.ts) x3 on x3.pkbRequest = x.pkbRequest) select * from xx where xx.pkbRequest = #{pkbRequest}")
  PkbRequest loadAt(@Param("ts")Date ts, @Param("pkbRequest")long pkbRequest);
  @Insert("insert into m_pkbRequest_contract (pkbRequest, contract) values (#{pkbRequest}, #{contract})")
  void insContract(PkbRequest pkbRequest);
  @Insert("insert into m_pkbRequest_contract (pkbRequest, contract) values (#{pkbRequest}, #{contract})")
  void setContract(@Param("pkbRequest")long pkbRequest, @Param("contract")Long contract);
  @Insert("insert into m_pkbRequest_idNumber (pkbRequest, idNumber) values (#{pkbRequest}, #{idNumber})")
  void insIdNumber(PkbRequest pkbRequest);
  @Insert("insert into m_pkbRequest_idNumber (pkbRequest, idNumber) values (#{pkbRequest}, #{idNumber})")
  void setIdNumber(@Param("pkbRequest")long pkbRequest, @Param("idNumber")String idNumber);
  @Insert("insert into m_pkbRequest_reportCode (pkbRequest, reportCode) values (#{pkbRequest}, #{reportCode})")
  void insReportCode(PkbRequest pkbRequest);
  @Insert("insert into m_pkbRequest_reportCode (pkbRequest, reportCode) values (#{pkbRequest}, #{reportCode})")
  void setReportCode(@Param("pkbRequest")long pkbRequest, @Param("reportCode")String reportCode);
}
