package kz.pompei.dao.dsa.dsa.dsa;
import kz.pompei.dbmodel.dsa.dsa.dsa.Asd;
import kz.greetgo.gbatis.t.Prm;
import java.util.Date;
import kz.greetgo.gbatis.t.Call;
import kz.greetgo.gbatis.t.Sele;
public interface AsdDao {

  @Call("{call p_asd (#{asd})}")
  void ins(Asd asd);
  @Call("{call p_asd (#{asd})}")
  void add(@Prm("asd")String asd);
  @Sele("select * from v_asd where asd = #{asd}")
  Asd load(@Prm("asd")String asd);
  @Sele("with tts as (select #{ts} ts from dual), xx as ( select  x.asd,  x.createdAt ,x1.name ,x2.wow1 ,x2.wow2 ,x2.wow3 from (select u.* from m_asd u, tts where u.createdAt <= tts.ts) x left join( select asd, name from ( select m.*, row_number() over (partition by m.asd order by m.ts desc ) as rn__ from m_asd_name m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x1 on x1.asd = x.asd left join( select asd, wow1, wow2, wow3 from ( select m.*, row_number() over (partition by m.asd order by m.ts desc ) as rn__ from m_asd_wow m , tts where m.ts <= tts.ts ) x where x.rn__ = 1) x2 on x2.asd = x.asd) select * from xx where xx.asd = #{asd}")
  Asd loadAt(@Prm("ts")Date ts, @Prm("asd")String asd);
  @Call("{call p_asd_name (#{asd}, #{name})}")
  void insName(Asd asd);
  @Call("{call p_asd_name (#{asd}, #{name})}")
  void setName(@Prm("asd")String asd, @Prm("name")String name);
  @Call("{call p_asd_wow (#{asd}, #{wow1}, #{wow2}, #{wow3})}")
  void insWow(Asd asd);
  @Call("{call p_asd_wow (#{asd}, #{wow1}, #{wow2}, #{wow3})}")
  void setWow(@Prm("asd")String asd, @Prm("wow1")Long wow1, @Prm("wow2")String wow2, @Prm("wow3")String wow3);
}
