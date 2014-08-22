package kz.pompei.dao;
import kz.greetgo.gbatis.t.Prm;
import java.util.Date;
import kz.pompei.dbmodel.PkbRequest;
import kz.greetgo.gbatis.t.Call;
import kz.greetgo.gbatis.t.Sele;
public interface PkbRequestDao {
  long next();

  @Call("{call p_pkbRequest (#{pkbRequest})}")
  void ins(PkbRequest pkbRequest);
  @Call("{call p_pkbRequest (#{pkbRequest})}")
  void add(@Prm("pkbRequest")long pkbRequest);
  @Sele("select * from v_pkbRequest where pkbRequest = #{pkbRequest}")
  PkbRequest load(@Prm("pkbRequest")long pkbRequest);
  @Sele("with tts as (select #{ts} ts from dual), xx as ( select  x.pkbRequest,  x.createdAt ,x1.contract ,x2.idNumber ,x3.reportCode from (select u.* from m_pkbRequest u, tts where u.createdAt <= tts.ts) x left join( select pkbRequest, contract from ( select m.*, row_number() over (partition by m.pkbRequest order by m.ts desc ) as rn__ from m_pkbRequest_contract m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.pkbRequest = x.pkbRequest left join( select pkbRequest, idNumber from ( select m.*, row_number() over (partition by m.pkbRequest order by m.ts desc ) as rn__ from m_pkbRequest_idNumber m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.pkbRequest = x.pkbRequest left join( select pkbRequest, reportCode from ( select m.*, row_number() over (partition by m.pkbRequest order by m.ts desc ) as rn__ from m_pkbRequest_reportCode m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x3 on x3.pkbRequest = x.pkbRequest) select * from xx where xx.pkbRequest = #{pkbRequest}")
  PkbRequest loadAt(@Prm("ts")Date ts, @Prm("pkbRequest")long pkbRequest);
  @Call("{call p_pkbRequest_contract (#{pkbRequest}, #{contract})}")
  void insContract(PkbRequest pkbRequest);
  @Call("{call p_pkbRequest_contract (#{pkbRequest}, #{contract})}")
  void setContract(@Prm("pkbRequest")long pkbRequest, @Prm("contract")Long contract);
  @Call("{call p_pkbRequest_idNumber (#{pkbRequest}, #{idNumber})}")
  void insIdNumber(PkbRequest pkbRequest);
  @Call("{call p_pkbRequest_idNumber (#{pkbRequest}, #{idNumber})}")
  void setIdNumber(@Prm("pkbRequest")long pkbRequest, @Prm("idNumber")String idNumber);
  @Call("{call p_pkbRequest_reportCode (#{pkbRequest}, #{reportCode})}")
  void insReportCode(PkbRequest pkbRequest);
  @Call("{call p_pkbRequest_reportCode (#{pkbRequest}, #{reportCode})}")
  void setReportCode(@Prm("pkbRequest")long pkbRequest, @Prm("reportCode")String reportCode);
}
