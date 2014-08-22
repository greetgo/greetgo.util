package kz.pompei.dao.asd.asd;
import kz.greetgo.gbatis.t.Prm;
import java.util.Date;
import kz.greetgo.gbatis.t.Call;
import kz.pompei.dbmodel.asd.asd.Address;
import kz.greetgo.gbatis.t.Sele;
public interface AddressDao {
  long next();

  @Call("{call p_address (#{address})}")
  void ins(Address address);
  @Call("{call p_address (#{address})}")
  void add(@Prm("address")long address);
  @Sele("select * from v_address where address = #{address}")
  Address load(@Prm("address")long address);
  @Sele("with tts as (select #{ts} ts from dual), xx as ( select  x.address,  x.createdAt ,x1.street ,x2.flat from (select u.* from m_address u, tts where u.createdAt <= tts.ts) x left join( select address, street from ( select m.*, row_number() over (partition by m.address order by m.ts desc ) as rn__ from m_address_street m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.address = x.address left join( select address, flat from ( select m.*, row_number() over (partition by m.address order by m.ts desc ) as rn__ from m_address_flat m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.address = x.address) select * from xx where xx.address = #{address}")
  Address loadAt(@Prm("ts")Date ts, @Prm("address")long address);
  @Call("{call p_address_street (#{address}, #{street})}")
  void insStreet(Address address);
  @Call("{call p_address_street (#{address}, #{street})}")
  void setStreet(@Prm("address")long address, @Prm("street")String street);
  @Call("{call p_address_flat (#{address}, #{flat})}")
  void insFlat(Address address);
  @Call("{call p_address_flat (#{address}, #{flat})}")
  void setFlat(@Prm("address")long address, @Prm("flat")String flat);
}
