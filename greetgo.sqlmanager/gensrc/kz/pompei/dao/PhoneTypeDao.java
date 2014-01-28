package kz.pompei.dao;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.annotations.Options;
import kz.pompei.dbmodel.PhoneType;

public interface PhoneTypeDao {
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_phoneType (#{phoneType})}")
  void ins(PhoneType phoneType);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_phoneType (#{phoneType})}")
  void add(@Param("phoneType") String phoneType);
  
  @Select("select * from v_phoneType where phoneType = #{phoneType}")
  PhoneType load(@Param("phoneType") String phoneType);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.phoneType,  x.createdAt ,x1.name from (select u.* from m_phoneType u, tts where u.createdAt <= tts.ts) x left join( select phoneType, name from ( select m.*, row_number() over (partition by m.phoneType order by m.ts desc ) as rn__ from m_phoneType_name m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.phoneType = x.phoneType) select * from xx where xx.phoneType = #{phoneType}")
      PhoneType loadAt(@Param("ts") Date ts, @Param("phoneType") String phoneType);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_phoneType_name (#{phoneType}, #{name})}")
  void insName(PhoneType phoneType);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_phoneType_name (#{phoneType}, #{name})}")
  void setName(@Param("phoneType") String phoneType, @Param("name") String name);
}
