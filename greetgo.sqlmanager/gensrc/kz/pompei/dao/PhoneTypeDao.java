package kz.pompei.dao;
import kz.greetgo.gbatis.t.Prm;
import java.util.Date;
import kz.greetgo.gbatis.t.Call;
import kz.pompei.dbmodel.PhoneType;
import kz.greetgo.gbatis.t.Sele;
public interface PhoneTypeDao {

  @Call("{call p_phoneType (#{phoneType})}")
  void ins(PhoneType phoneType);
  @Call("{call p_phoneType (#{phoneType})}")
  void add(@Prm("phoneType")String phoneType);
  @Sele("select * from v_phoneType where phoneType = #{phoneType}")
  PhoneType load(@Prm("phoneType")String phoneType);
  @Sele("with tts as (select #{ts} ts from dual), xx as ( select  x.phoneType,  x.createdAt ,x1.name from (select u.* from m_phoneType u, tts where u.createdAt <= tts.ts) x left join( select phoneType, name from ( select m.*, row_number() over (partition by m.phoneType order by m.ts desc ) as rn__ from m_phoneType_name m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.phoneType = x.phoneType) select * from xx where xx.phoneType = #{phoneType}")
  PhoneType loadAt(@Prm("ts")Date ts, @Prm("phoneType")String phoneType);
  @Call("{call p_phoneType_name (#{phoneType}, #{name})}")
  void insName(PhoneType phoneType);
  @Call("{call p_phoneType_name (#{phoneType}, #{name})}")
  void setName(@Prm("phoneType")String phoneType, @Prm("name")String name);
}
