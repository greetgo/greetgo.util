package kz.pompei.dao.dsa.dsa.dsa;
import org.apache.ibatis.annotations.Select;
import kz.pompei.dbmodel.dsa.dsa.dsa.Asd;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Insert;
import java.util.Date;
public interface AsdDao {

  @Insert("insert into m_asd (asd) values (#{asd})")
  void ins(Asd asd);
  @Insert("insert into m_asd (asd) values (#{asd})")
  void add(@Param("asd")String asd);
  @Select("select * from v_asd where asd = #{asd}")
  Asd load(@Param("asd")String asd);
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.asd,  x.createdAt ,x1.name ,x2.wow1 ,x2.wow2 ,x2.wow3 from (select u.* from m_asd u, tts where u.createdAt <= tts.ts) x left join( select  aa.asd ,bb.name from ( select a.asd, max(b.ts) ts from m_asd a left join (select x.* from m_asd_name x, tts where x.ts <= tts.ts) b on a.asd = b.asd ,tts where b.ts <= tts.ts group by  a.asd ) aa left join m_asd_name bb on aa.asd = bb.asd and aa.ts = bb.ts) x1 on x1.asd = x.asd left join( select  aa.asd ,bb.wow1 ,bb.wow2 ,bb.wow3 from ( select a.asd, max(b.ts) ts from m_asd a left join (select x.* from m_asd_wow x, tts where x.ts <= tts.ts) b on a.asd = b.asd ,tts where b.ts <= tts.ts group by  a.asd ) aa left join m_asd_wow bb on aa.asd = bb.asd and aa.ts = bb.ts) x2 on x2.asd = x.asd) select * from xx where xx.asd = #{asd}")
  Asd loadAt(@Param("ts")Date ts, @Param("asd")String asd);
  @Insert("insert into m_asd_name (asd, name) values (#{asd}, #{name})")
  void insName(Asd asd);
  @Insert("insert into m_asd_name (asd, name) values (#{asd}, #{name})")
  void setName(@Param("asd")String asd, @Param("name")String name);
  @Insert("insert into m_asd_wow (asd, wow1, wow2, wow3) values (#{asd}, #{wow1}, #{wow2}, #{wow3})")
  void insWow(Asd asd);
  @Insert("insert into m_asd_wow (asd, wow1, wow2, wow3) values (#{asd}, #{wow1}, #{wow2}, #{wow3})")
  void setWow(@Param("asd")String asd, @Param("wow1")Long wow1, @Param("wow2")String wow2, @Param("wow3")String wow3);
}
