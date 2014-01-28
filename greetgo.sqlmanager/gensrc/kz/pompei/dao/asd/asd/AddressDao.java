package kz.pompei.dao.asd.asd;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Insert;
import java.util.Date;
import kz.pompei.dbmodel.asd.asd.Address;
public interface AddressDao {
  long next();

  @Insert("insert into m_address (address) values (#{address})")
  void ins(Address address);
  @Insert("insert into m_address (address) values (#{address})")
  void add(@Param("address")long address);
  @Select("select * from v_address where address = #{address}")
  Address load(@Param("address")long address);
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.address,  x.createdAt ,x1.street ,x2.flat from (select u.* from m_address u, tts where u.createdAt <= tts.ts) x left join( select  aa.address ,bb.street from ( select a.address, max(b.ts) ts from m_address a left join (select x.* from m_address_street x, tts where x.ts <= tts.ts) b on a.address = b.address ,tts where b.ts <= tts.ts group by  a.address ) aa left join m_address_street bb on aa.address = bb.address and aa.ts = bb.ts) x1 on x1.address = x.address left join( select  aa.address ,bb.flat from ( select a.address, max(b.ts) ts from m_address a left join (select x.* from m_address_flat x, tts where x.ts <= tts.ts) b on a.address = b.address ,tts where b.ts <= tts.ts group by  a.address ) aa left join m_address_flat bb on aa.address = bb.address and aa.ts = bb.ts) x2 on x2.address = x.address) select * from xx where xx.address = #{address}")
  Address loadAt(@Param("ts")Date ts, @Param("address")long address);
  @Insert("insert into m_address_street (address, street) values (#{address}, #{street})")
  void insStreet(Address address);
  @Insert("insert into m_address_street (address, street) values (#{address}, #{street})")
  void setStreet(@Param("address")long address, @Param("street")String street);
  @Insert("insert into m_address_flat (address, flat) values (#{address}, #{flat})")
  void insFlat(Address address);
  @Insert("insert into m_address_flat (address, flat) values (#{address}, #{flat})")
  void setFlat(@Param("address")long address, @Param("flat")String flat);
}
