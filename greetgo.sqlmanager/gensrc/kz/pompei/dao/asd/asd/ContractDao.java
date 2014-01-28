package kz.pompei.dao.asd.asd;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Insert;
import java.util.Date;
import kz.pompei.dbmodel.asd.asd.Contract;
public interface ContractDao {
  long next();

  @Insert("insert into m_contract (contract) values (#{contract})")
  void ins(Contract contract);
  @Insert("insert into m_contract (contract) values (#{contract})")
  void add(@Param("contract")long contract);
  @Select("select * from v_contract where contract = #{contract}")
  Contract load(@Param("contract")long contract);
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.contract,  x.createdAt ,x1.nomer ,x2.summa ,x3.productType ,x4.client ,x5.lastPkbRequest ,x6.asd from (select u.* from m_contract u, tts where u.createdAt <= tts.ts) x left join( select  aa.contract ,bb.nomer from ( select a.contract, max(b.ts) ts from m_contract a left join (select x.* from m_contract_nomer x, tts where x.ts <= tts.ts) b on a.contract = b.contract ,tts where b.ts <= tts.ts group by  a.contract ) aa left join m_contract_nomer bb on aa.contract = bb.contract and aa.ts = bb.ts) x1 on x1.contract = x.contract left join( select  aa.contract ,bb.summa from ( select a.contract, max(b.ts) ts from m_contract a left join (select x.* from m_contract_summa x, tts where x.ts <= tts.ts) b on a.contract = b.contract ,tts where b.ts <= tts.ts group by  a.contract ) aa left join m_contract_summa bb on aa.contract = bb.contract and aa.ts = bb.ts) x2 on x2.contract = x.contract left join( select  aa.contract ,bb.productType from ( select a.contract, max(b.ts) ts from m_contract a left join (select x.* from m_contract_productType x, tts where x.ts <= tts.ts) b on a.contract = b.contract ,tts where b.ts <= tts.ts group by  a.contract ) aa left join m_contract_productType bb on aa.contract = bb.contract and aa.ts = bb.ts) x3 on x3.contract = x.contract left join( select  aa.contract ,bb.client from ( select a.contract, max(b.ts) ts from m_contract a left join (select x.* from m_contract_client x, tts where x.ts <= tts.ts) b on a.contract = b.contract ,tts where b.ts <= tts.ts group by  a.contract ) aa left join m_contract_client bb on aa.contract = bb.contract and aa.ts = bb.ts) x4 on x4.contract = x.contract left join( select  aa.contract ,bb.lastPkbRequest from ( select a.contract, max(b.ts) ts from m_contract a left join (select x.* from m_contract_lastPkbRequest x, tts where x.ts <= tts.ts) b on a.contract = b.contract ,tts where b.ts <= tts.ts group by  a.contract ) aa left join m_contract_lastPkbRequest bb on aa.contract = bb.contract and aa.ts = bb.ts) x5 on x5.contract = x.contract left join( select  aa.contract ,bb.asd from ( select a.contract, max(b.ts) ts from m_contract a left join (select x.* from m_contract_asd x, tts where x.ts <= tts.ts) b on a.contract = b.contract ,tts where b.ts <= tts.ts group by  a.contract ) aa left join m_contract_asd bb on aa.contract = bb.contract and aa.ts = bb.ts) x6 on x6.contract = x.contract) select * from xx where xx.contract = #{contract}")
  Contract loadAt(@Param("ts")Date ts, @Param("contract")long contract);
  @Insert("insert into m_contract_nomer (contract, nomer) values (#{contract}, #{nomer})")
  void insNomer(Contract contract);
  @Insert("insert into m_contract_nomer (contract, nomer) values (#{contract}, #{nomer})")
  void setNomer(@Param("contract")long contract, @Param("nomer")String nomer);
  @Insert("insert into m_contract_summa (contract, summa) values (#{contract}, #{summa})")
  void insSumma(Contract contract);
  @Insert("insert into m_contract_summa (contract, summa) values (#{contract}, #{summa})")
  void setSumma(@Param("contract")long contract, @Param("summa")Double summa);
  @Insert("insert into m_contract_productType (contract, productType) values (#{contract}, #{productType})")
  void insProductType(Contract contract);
  @Insert("insert into m_contract_productType (contract, productType) values (#{contract}, #{productType})")
  void setProductType(@Param("contract")long contract, @Param("productType")String productType);
  @Insert("insert into m_contract_client (contract, client) values (#{contract}, #{client})")
  void insClient(Contract contract);
  @Insert("insert into m_contract_client (contract, client) values (#{contract}, #{client})")
  void setClient(@Param("contract")long contract, @Param("client")Long client);
  @Insert("insert into m_contract_lastPkbRequest (contract, lastPkbRequest) values (#{contract}, #{lastPkbRequest})")
  void insLastPkbRequest(Contract contract);
  @Insert("insert into m_contract_lastPkbRequest (contract, lastPkbRequest) values (#{contract}, #{lastPkbRequest})")
  void setLastPkbRequest(@Param("contract")long contract, @Param("lastPkbRequest")Long lastPkbRequest);
  @Insert("insert into m_contract_asd (contract, asd) values (#{contract}, #{asd})")
  void insAsd(Contract contract);
  @Insert("insert into m_contract_asd (contract, asd) values (#{contract}, current_timestamp)")
  void insAsdWithNow(Contract contract);
  @Insert("insert into m_contract_asd (contract, asd) values (#{contract}, #{asd})")
  void setAsd(@Param("contract")long contract, @Param("asd")Date asd);
  @Insert("insert into m_contract_asd (contract, asd) values (#{contract}, current_timestamp)")
  void setAsdWithNow(@Param("contract")long contract);
}
