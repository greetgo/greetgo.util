package kz.pompei.dao.asd.asd;
import kz.greetgo.gbatis.t.Prm;
import java.util.Date;
import kz.pompei.dbmodel.asd.asd.ProductType;
import kz.greetgo.gbatis.t.Call;
import kz.greetgo.gbatis.t.Sele;
public interface ProductTypeDao {

  @Call("{call p_productType (#{productType})}")
  void ins(ProductType productType);
  @Call("{call p_productType (#{productType})}")
  void add(@Prm("productType")String productType);
  @Sele("select * from v_productType where productType = #{productType}")
  ProductType load(@Prm("productType")String productType);
  @Sele("with tts as (select #{ts} ts from dual), xx as ( select  x.productType,  x.createdAt ,x1.pkbId ,x2.name from (select u.* from m_productType u, tts where u.createdAt <= tts.ts) x left join( select productType, pkbId from ( select m.*, row_number() over (partition by m.productType order by m.ts desc ) as rn__ from m_productType_pkbId m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.productType = x.productType left join( select productType, name from ( select m.*, row_number() over (partition by m.productType order by m.ts desc ) as rn__ from m_productType_name m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.productType = x.productType) select * from xx where xx.productType = #{productType}")
  ProductType loadAt(@Prm("ts")Date ts, @Prm("productType")String productType);
  @Call("{call p_productType_pkbId (#{productType}, #{pkbId})}")
  void insPkbId(ProductType productType);
  @Call("{call p_productType_pkbId (#{productType}, #{pkbId})}")
  void setPkbId(@Prm("productType")String productType, @Prm("pkbId")Long pkbId);
  @Call("{call p_productType_name (#{productType}, #{name})}")
  void insName(ProductType productType);
  @Call("{call p_productType_name (#{productType}, #{name})}")
  void setName(@Prm("productType")String productType, @Prm("name")String name);
}
