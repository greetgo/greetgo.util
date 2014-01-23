package kz.pompei.dao;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Insert;
import java.util.Date;
import kz.pompei.dbmodel.PhoneType;

public interface PhoneTypeDao {
  
  @Insert("insert into m_phoneType (phoneType) values (#{phoneType})")
  void ins(PhoneType phoneType);
  
  @Insert("insert into m_phoneType (phoneType) values (#{phoneType})")
  void add(@Param("phoneType") String phoneType);
  
  @Select("select * from v_phoneType where phoneType = #{phoneType}")
  PhoneType load(@Param("phoneType") String phoneType);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.phoneType,  x.createdAt ,x1.name from (select u.* from m_phoneType u, tts where u.createdAt <= tts.ts) x left join( select  aa.phoneType ,bb.name from ( select a.phoneType, max(b.ts) ts from m_phoneType a left join (select x.* from m_phoneType_name x, tts where x.ts <= tts.ts) b on a.phoneType = b.phoneType ,tts where b.ts <= tts.ts group by  a.phoneType ) aa left join m_phoneType_name bb on aa.phoneType = bb.phoneType and aa.ts = bb.ts) x1 on x1.phoneType = x.phoneType) select * from xx where xx.phoneType = #{phoneType}")
      PhoneType loadAt(@Param("ts") Date ts, @Param("phoneType") String phoneType);
  
  @Insert("insert into m_phoneType_name (phoneType, name) values (#{phoneType}, #{name})")
  void insName(PhoneType phoneType);
  
  @Insert("insert into m_phoneType_name (phoneType, name) values (#{phoneType}, #{name})")
  void setName(@Param("phoneType") String phoneType, @Param("name") String name);
}
