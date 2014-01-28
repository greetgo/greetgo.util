package kz.pompei.dao.asd.asd;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import kz.pompei.dbmodel.asd.asd.Contract;
import org.apache.ibatis.annotations.Options;

public interface ContractDao {
  long next();
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_contract (#{contract})}")
  void ins(Contract contract);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_contract (#{contract})}")
  void add(@Param("contract") long contract);
  
  @Select("select * from v_contract where contract = #{contract}")
  Contract load(@Param("contract") long contract);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.contract,  x.createdAt ,x1.nomer ,x2.summa ,x3.productType ,x4.client ,x5.lastPkbRequest ,x6.asd from (select u.* from m_contract u, tts where u.createdAt <= tts.ts) x left join( select contract, nomer from ( select m.*, row_number() over (partition by m.contract order by m.ts desc ) as rn__ from m_contract_nomer m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.contract = x.contract left join( select contract, summa from ( select m.*, row_number() over (partition by m.contract order by m.ts desc ) as rn__ from m_contract_summa m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.contract = x.contract left join( select contract, productType from ( select m.*, row_number() over (partition by m.contract order by m.ts desc ) as rn__ from m_contract_productType m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x3 on x3.contract = x.contract left join( select contract, client from ( select m.*, row_number() over (partition by m.contract order by m.ts desc ) as rn__ from m_contract_client m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x4 on x4.contract = x.contract left join( select contract, lastPkbRequest from ( select m.*, row_number() over (partition by m.contract order by m.ts desc ) as rn__ from m_contract_lastPkbRequest m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x5 on x5.contract = x.contract left join( select contract, asd from ( select m.*, row_number() over (partition by m.contract order by m.ts desc ) as rn__ from m_contract_asd m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x6 on x6.contract = x.contract) select * from xx where xx.contract = #{contract}")
      Contract loadAt(@Param("ts") Date ts, @Param("contract") long contract);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_contract_nomer (#{contract}, #{nomer})}")
  void insNomer(Contract contract);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_contract_nomer (#{contract}, #{nomer})}")
  void setNomer(@Param("contract") long contract, @Param("nomer") String nomer);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_contract_summa (#{contract}, #{summa})}")
  void insSumma(Contract contract);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_contract_summa (#{contract}, #{summa})}")
  void setSumma(@Param("contract") long contract, @Param("summa") Double summa);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_contract_productType (#{contract}, #{productType})}")
  void insProductType(Contract contract);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_contract_productType (#{contract}, #{productType})}")
  void setProductType(@Param("contract") long contract, @Param("productType") String productType);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_contract_client (#{contract}, #{client})}")
  void insClient(Contract contract);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_contract_client (#{contract}, #{client})}")
  void setClient(@Param("contract") long contract, @Param("client") Long client);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_contract_lastPkbRequest (#{contract}, #{lastPkbRequest})}")
  void insLastPkbRequest(Contract contract);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_contract_lastPkbRequest (#{contract}, #{lastPkbRequest})}")
  void setLastPkbRequest(@Param("contract") long contract,
      @Param("lastPkbRequest") Long lastPkbRequest);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_contract_asd (#{contract}, #{asd})}")
  void insAsd(Contract contract);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_contract_asd (#{contract}, current_timestamp)}")
  void insAsdWithNow(Contract contract);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_contract_asd (#{contract}, #{asd})}")
  void setAsd(@Param("contract") long contract, @Param("asd") Date asd);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_contract_asd (#{contract}, current_timestamp)}")
  void setAsdWithNow(@Param("contract") long contract);
}
