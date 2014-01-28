package kz.pompei.dao.asd.asd;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Insert;
import java.util.Date;
import kz.pompei.dbmodel.asd.asd.ProductType;
public interface ProductTypeDao {

  @Insert("insert into m_productType (productType) values (#{productType})")
  void ins(ProductType productType);
  @Insert("insert into m_productType (productType) values (#{productType})")
  void add(@Param("productType")String productType);
  @Select("select * from v_productType where productType = #{productType}")
  ProductType load(@Param("productType")String productType);
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.productType,  x.createdAt ,x1.pkbId ,x2.name from (select u.* from m_productType u, tts where u.createdAt <= tts.ts) x left join( select  aa.productType ,bb.pkbId from ( select a.productType, max(b.ts) ts from m_productType a left join (select x.* from m_productType_pkbId x, tts where x.ts <= tts.ts) b on a.productType = b.productType ,tts where b.ts <= tts.ts group by  a.productType ) aa left join m_productType_pkbId bb on aa.productType = bb.productType and aa.ts = bb.ts) x1 on x1.productType = x.productType left join( select  aa.productType ,bb.name from ( select a.productType, max(b.ts) ts from m_productType a left join (select x.* from m_productType_name x, tts where x.ts <= tts.ts) b on a.productType = b.productType ,tts where b.ts <= tts.ts group by  a.productType ) aa left join m_productType_name bb on aa.productType = bb.productType and aa.ts = bb.ts) x2 on x2.productType = x.productType) select * from xx where xx.productType = #{productType}")
  ProductType loadAt(@Param("ts")Date ts, @Param("productType")String productType);
  @Insert("insert into m_productType_pkbId (productType, pkbId) values (#{productType}, #{pkbId})")
  void insPkbId(ProductType productType);
  @Insert("insert into m_productType_pkbId (productType, pkbId) values (#{productType}, #{pkbId})")
  void setPkbId(@Param("productType")String productType, @Param("pkbId")Long pkbId);
  @Insert("insert into m_productType_name (productType, name) values (#{productType}, #{name})")
  void insName(ProductType productType);
  @Insert("insert into m_productType_name (productType, name) values (#{productType}, #{name})")
  void setName(@Param("productType")String productType, @Param("name")String name);
}
