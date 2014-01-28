package kz.pompei.dao.asd.asd;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import kz.pompei.dbmodel.asd.asd.ProductType;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.annotations.Options;

public interface ProductTypeDao {
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_productType (#{productType})}")
  void ins(ProductType productType);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_productType (#{productType})}")
  void add(@Param("productType") String productType);
  
  @Select("select * from v_productType where productType = #{productType}")
  ProductType load(@Param("productType") String productType);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.productType,  x.createdAt ,x1.pkbId ,x2.name from (select u.* from m_productType u, tts where u.createdAt <= tts.ts) x left join( select productType, pkbId from ( select m.*, row_number() over (partition by m.productType order by m.ts desc ) as rn__ from m_productType_pkbId m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.productType = x.productType left join( select productType, name from ( select m.*, row_number() over (partition by m.productType order by m.ts desc ) as rn__ from m_productType_name m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.productType = x.productType) select * from xx where xx.productType = #{productType}")
      ProductType loadAt(@Param("ts") Date ts, @Param("productType") String productType);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_productType_pkbId (#{productType}, #{pkbId})}")
  void insPkbId(ProductType productType);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_productType_pkbId (#{productType}, #{pkbId})}")
  void setPkbId(@Param("productType") String productType, @Param("pkbId") Long pkbId);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_productType_name (#{productType}, #{name})}")
  void insName(ProductType productType);
  
  @Options(statementType = StatementType.CALLABLE)
  @Update("{call p_productType_name (#{productType}, #{name})}")
  void setName(@Param("productType") String productType, @Param("name") String name);
}
