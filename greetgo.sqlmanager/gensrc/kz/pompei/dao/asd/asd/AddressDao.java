package kz.pompei.dao.asd.asd;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import kz.pompei.dbmodel.asd.asd.Address;
import org.apache.ibatis.annotations.Options;

public interface AddressDao {
  long next();
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_address (#{address})}")
  void ins(Address address);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_address (#{address})}")
  void add(@Param("address") long address);
  
  @Select("select * from v_address where address = #{address}")
  Address load(@Param("address") long address);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.address,  x.createdAt ,x1.street ,x2.flat from (select u.* from m_address u, tts where u.createdAt <= tts.ts) x left join( select address, street from ( select m.*, row_number() over (partition by m.address order by m.ts desc ) as rn__ from m_address_street m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.address = x.address left join( select address, flat from ( select m.*, row_number() over (partition by m.address order by m.ts desc ) as rn__ from m_address_flat m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.address = x.address) select * from xx where xx.address = #{address}")
      Address loadAt(@Param("ts") Date ts, @Param("address") long address);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_address_street (#{address}, #{street})}")
  void insStreet(Address address);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_address_street (#{address}, #{street})}")
  void setStreet(@Param("address") long address, @Param("street") String street);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_address_flat (#{address}, #{flat})}")
  void insFlat(Address address);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_address_flat (#{address}, #{flat})}")
  void setFlat(@Param("address") long address, @Param("flat") String flat);
}
