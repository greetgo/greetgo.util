package kz.pompei.dao.asd.asd;
import kz.greetgo.gbatis.t.Prm;
import java.util.Date;
import kz.greetgo.gbatis.t.Call;
import kz.pompei.dbmodel.asd.asd.Contract;
import kz.greetgo.gbatis.t.Sele;
public interface ContractDao {
  long next();

  @Call("{call p_contract (#{contract})}")
  void ins(Contract contract);
  @Call("{call p_contract (#{contract})}")
  void add(@Prm("contract")long contract);
  @Sele("select * from v_contract where contract = #{contract}")
  Contract load(@Prm("contract")long contract);
  @Sele("with tts as (select #{ts} ts from dual), xx as ( select  x.contract,  x.createdAt ,x1.nomer ,x2.summa ,x3.productType ,x4.client ,x5.lastPkbRequest ,x6.asd from (select u.* from m_contract u, tts where u.createdAt <= tts.ts) x left join( select contract, nomer from ( select m.*, row_number() over (partition by m.contract order by m.ts desc ) as rn__ from m_contract_nomer m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.contract = x.contract left join( select contract, summa from ( select m.*, row_number() over (partition by m.contract order by m.ts desc ) as rn__ from m_contract_summa m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.contract = x.contract left join( select contract, productType from ( select m.*, row_number() over (partition by m.contract order by m.ts desc ) as rn__ from m_contract_productType m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x3 on x3.contract = x.contract left join( select contract, client from ( select m.*, row_number() over (partition by m.contract order by m.ts desc ) as rn__ from m_contract_client m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x4 on x4.contract = x.contract left join( select contract, lastPkbRequest from ( select m.*, row_number() over (partition by m.contract order by m.ts desc ) as rn__ from m_contract_lastPkbRequest m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x5 on x5.contract = x.contract left join( select contract, asd from ( select m.*, row_number() over (partition by m.contract order by m.ts desc ) as rn__ from m_contract_asd m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x6 on x6.contract = x.contract) select * from xx where xx.contract = #{contract}")
  Contract loadAt(@Prm("ts")Date ts, @Prm("contract")long contract);
  @Call("{call p_contract_nomer (#{contract}, #{nomer})}")
  void insNomer(Contract contract);
  @Call("{call p_contract_nomer (#{contract}, #{nomer})}")
  void setNomer(@Prm("contract")long contract, @Prm("nomer")String nomer);
  @Call("{call p_contract_summa (#{contract}, #{summa})}")
  void insSumma(Contract contract);
  @Call("{call p_contract_summa (#{contract}, #{summa})}")
  void setSumma(@Prm("contract")long contract, @Prm("summa")Double summa);
  @Call("{call p_contract_productType (#{contract}, #{productType})}")
  void insProductType(Contract contract);
  @Call("{call p_contract_productType (#{contract}, #{productType})}")
  void setProductType(@Prm("contract")long contract, @Prm("productType")String productType);
  @Call("{call p_contract_client (#{contract}, #{client})}")
  void insClient(Contract contract);
  @Call("{call p_contract_client (#{contract}, #{client})}")
  void setClient(@Prm("contract")long contract, @Prm("client")Long client);
  @Call("{call p_contract_lastPkbRequest (#{contract}, #{lastPkbRequest})}")
  void insLastPkbRequest(Contract contract);
  @Call("{call p_contract_lastPkbRequest (#{contract}, #{lastPkbRequest})}")
  void setLastPkbRequest(@Prm("contract")long contract, @Prm("lastPkbRequest")Long lastPkbRequest);
  @Call("{call p_contract_asd (#{contract}, #{asd})}")
  void insAsd(Contract contract);
  @Call("{call p_contract_asd (#{contract}, moment())}")
  void insAsdWithNow(Contract contract);
  @Call("{call p_contract_asd (#{contract}, #{asd})}")
  void setAsd(@Prm("contract")long contract, @Prm("asd")Date asd);
  @Call("{call p_contract_asd (#{contract}, moment())}")
  void setAsdWithNow(@Prm("contract")long contract);
}
