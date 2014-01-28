package kz.pompei.dao;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import kz.pompei.dbmodel.PkbRequest;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.annotations.Options;

public interface PkbRequestDao {
  long next();
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbRequest (#{pkbRequest})}")
  void ins(PkbRequest pkbRequest);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbRequest (#{pkbRequest})}")
  void add(@Param("pkbRequest") long pkbRequest);
  
  @Select("select * from v_pkbRequest where pkbRequest = #{pkbRequest}")
  PkbRequest load(@Param("pkbRequest") long pkbRequest);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.pkbRequest,  x.createdAt ,x1.contract ,x2.idNumber ,x3.reportCode from (select u.* from m_pkbRequest u, tts where u.createdAt <= tts.ts) x left join( select pkbRequest, contract from ( select m.*, row_number() over (partition by m.pkbRequest order by m.ts desc ) as rn__ from m_pkbRequest_contract m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.pkbRequest = x.pkbRequest left join( select pkbRequest, idNumber from ( select m.*, row_number() over (partition by m.pkbRequest order by m.ts desc ) as rn__ from m_pkbRequest_idNumber m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.pkbRequest = x.pkbRequest left join( select pkbRequest, reportCode from ( select m.*, row_number() over (partition by m.pkbRequest order by m.ts desc ) as rn__ from m_pkbRequest_reportCode m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x3 on x3.pkbRequest = x.pkbRequest) select * from xx where xx.pkbRequest = #{pkbRequest}")
      PkbRequest loadAt(@Param("ts") Date ts, @Param("pkbRequest") long pkbRequest);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbRequest_contract (#{pkbRequest}, #{contract})}")
  void insContract(PkbRequest pkbRequest);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbRequest_contract (#{pkbRequest}, #{contract})}")
  void setContract(@Param("pkbRequest") long pkbRequest, @Param("contract") Long contract);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbRequest_idNumber (#{pkbRequest}, #{idNumber})}")
  void insIdNumber(PkbRequest pkbRequest);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbRequest_idNumber (#{pkbRequest}, #{idNumber})}")
  void setIdNumber(@Param("pkbRequest") long pkbRequest, @Param("idNumber") String idNumber);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbRequest_reportCode (#{pkbRequest}, #{reportCode})}")
  void insReportCode(PkbRequest pkbRequest);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_pkbRequest_reportCode (#{pkbRequest}, #{reportCode})}")
  void setReportCode(@Param("pkbRequest") long pkbRequest, @Param("reportCode") String reportCode);
}
