package kz.pompei.dao.dsa.dsa.dsa;

import kz.pompei.dbmodelstru.dsa.dsa.dsa.AnnotationType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Insert;
import java.util.Date;
import kz.pompei.dbmodel.dsa.dsa.dsa.PkbResultParamAnnotation;
import java.util.List;
import kz.greetgo.sqlmanager.gen.HelloEnum;

public interface PkbResultParamAnnotationDao {
  
  @Insert("insert into m_pkbResultParamAnnotation (pkbResultParam1, pkbResultParam2, name) values (#{pkbResultParam1}, #{pkbResultParam2}, #{name})")
      void ins(PkbResultParamAnnotation pkbResultParamAnnotation);
  
  @Insert("insert into m_pkbResultParamAnnotation (pkbResultParam1, pkbResultParam2, name) values (#{pkbResultParam1}, #{pkbResultParam2}, #{name})")
      void add(@Param("pkbResultParam1") long pkbResultParam1,
          @Param("pkbResultParam2") String pkbResultParam2, @Param("name") String name);
  
  @Select("select * from v_pkbResultParamAnnotation where pkbResultParam1 = #{pkbResultParam1} and pkbResultParam2 = #{pkbResultParam2} and name = #{name}")
      PkbResultParamAnnotation load(@Param("pkbResultParam1") long pkbResultParam1,
          @Param("pkbResultParam2") String pkbResultParam2, @Param("name") String name);
  
  @Select("select * from v_pkbResultParamAnnotation where pkbResultParam1 = #{pkbResultParam1} and pkbResultParam2 = #{pkbResultParam2}")
      List<PkbResultParamAnnotation> loadList(@Param("pkbResultParam1") long pkbResultParam1,
          @Param("pkbResultParam2") String pkbResultParam2);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.pkbResultParam1,  x.pkbResultParam2,  x.name,  x.createdAt ,x1.valueStr ,x2.valueInt ,x3.type ,x4.hello ,x5.goodby from (select u.* from m_pkbResultParamAnnotation u, tts where u.createdAt <= tts.ts) x left join( select  aa.pkbResultParam1 ,aa.pkbResultParam2 ,aa.name ,bb.valueStr from ( select a.pkbResultParam1, a.pkbResultParam2, a.name, max(b.ts) ts from m_pkbResultParamAnnotation a left join (select x.* from m_pkbResultParamAnnotation_valueStr x, tts where x.ts <= tts.ts) b on a.pkbResultParam1 = b.pkbResultParam1 and a.pkbResultParam2 = b.pkbResultParam2 and a.name = b.name ,tts where b.ts <= tts.ts group by  a.pkbResultParam1 ,a.pkbResultParam2 ,a.name ) aa left join m_pkbResultParamAnnotation_valueStr bb on aa.pkbResultParam1 = bb.pkbResultParam1 and aa.pkbResultParam2 = bb.pkbResultParam2 and aa.name = bb.name and aa.ts = bb.ts) x1 on x1.pkbResultParam1 = x.pkbResultParam1 and x1.pkbResultParam2 = x.pkbResultParam2 and x1.name = x.name left join( select  aa.pkbResultParam1 ,aa.pkbResultParam2 ,aa.name ,bb.valueInt from ( select a.pkbResultParam1, a.pkbResultParam2, a.name, max(b.ts) ts from m_pkbResultParamAnnotation a left join (select x.* from m_pkbResultParamAnnotation_valueInt x, tts where x.ts <= tts.ts) b on a.pkbResultParam1 = b.pkbResultParam1 and a.pkbResultParam2 = b.pkbResultParam2 and a.name = b.name ,tts where b.ts <= tts.ts group by  a.pkbResultParam1 ,a.pkbResultParam2 ,a.name ) aa left join m_pkbResultParamAnnotation_valueInt bb on aa.pkbResultParam1 = bb.pkbResultParam1 and aa.pkbResultParam2 = bb.pkbResultParam2 and aa.name = bb.name and aa.ts = bb.ts) x2 on x2.pkbResultParam1 = x.pkbResultParam1 and x2.pkbResultParam2 = x.pkbResultParam2 and x2.name = x.name left join( select  aa.pkbResultParam1 ,aa.pkbResultParam2 ,aa.name ,bb.type from ( select a.pkbResultParam1, a.pkbResultParam2, a.name, max(b.ts) ts from m_pkbResultParamAnnotation a left join (select x.* from m_pkbResultParamAnnotation_type x, tts where x.ts <= tts.ts) b on a.pkbResultParam1 = b.pkbResultParam1 and a.pkbResultParam2 = b.pkbResultParam2 and a.name = b.name ,tts where b.ts <= tts.ts group by  a.pkbResultParam1 ,a.pkbResultParam2 ,a.name ) aa left join m_pkbResultParamAnnotation_type bb on aa.pkbResultParam1 = bb.pkbResultParam1 and aa.pkbResultParam2 = bb.pkbResultParam2 and aa.name = bb.name and aa.ts = bb.ts) x3 on x3.pkbResultParam1 = x.pkbResultParam1 and x3.pkbResultParam2 = x.pkbResultParam2 and x3.name = x.name left join( select  aa.pkbResultParam1 ,aa.pkbResultParam2 ,aa.name ,bb.hello from ( select a.pkbResultParam1, a.pkbResultParam2, a.name, max(b.ts) ts from m_pkbResultParamAnnotation a left join (select x.* from m_pkbResultParamAnnotation_hello x, tts where x.ts <= tts.ts) b on a.pkbResultParam1 = b.pkbResultParam1 and a.pkbResultParam2 = b.pkbResultParam2 and a.name = b.name ,tts where b.ts <= tts.ts group by  a.pkbResultParam1 ,a.pkbResultParam2 ,a.name ) aa left join m_pkbResultParamAnnotation_hello bb on aa.pkbResultParam1 = bb.pkbResultParam1 and aa.pkbResultParam2 = bb.pkbResultParam2 and aa.name = bb.name and aa.ts = bb.ts) x4 on x4.pkbResultParam1 = x.pkbResultParam1 and x4.pkbResultParam2 = x.pkbResultParam2 and x4.name = x.name left join( select  aa.pkbResultParam1 ,aa.pkbResultParam2 ,aa.name ,bb.goodby from ( select a.pkbResultParam1, a.pkbResultParam2, a.name, max(b.ts) ts from m_pkbResultParamAnnotation a left join (select x.* from m_pkbResultParamAnnotation_goodby x, tts where x.ts <= tts.ts) b on a.pkbResultParam1 = b.pkbResultParam1 and a.pkbResultParam2 = b.pkbResultParam2 and a.name = b.name ,tts where b.ts <= tts.ts group by  a.pkbResultParam1 ,a.pkbResultParam2 ,a.name ) aa left join m_pkbResultParamAnnotation_goodby bb on aa.pkbResultParam1 = bb.pkbResultParam1 and aa.pkbResultParam2 = bb.pkbResultParam2 and aa.name = bb.name and aa.ts = bb.ts) x5 on x5.pkbResultParam1 = x.pkbResultParam1 and x5.pkbResultParam2 = x.pkbResultParam2 and x5.name = x.name) select * from xx where xx.pkbResultParam1 = #{pkbResultParam1} and xx.pkbResultParam2 = #{pkbResultParam2} and xx.name = #{name}")
      PkbResultParamAnnotation loadAt(@Param("ts") Date ts,
          @Param("pkbResultParam1") long pkbResultParam1,
          @Param("pkbResultParam2") String pkbResultParam2, @Param("name") String name);
  
  @Select("with tts as (select #{ts} ts from dual), xx as ( select  x.pkbResultParam1,  x.pkbResultParam2,  x.name,  x.createdAt ,x1.valueStr ,x2.valueInt ,x3.type ,x4.hello ,x5.goodby from (select u.* from m_pkbResultParamAnnotation u, tts where u.createdAt <= tts.ts) x left join( select  aa.pkbResultParam1 ,aa.pkbResultParam2 ,aa.name ,bb.valueStr from ( select a.pkbResultParam1, a.pkbResultParam2, a.name, max(b.ts) ts from m_pkbResultParamAnnotation a left join (select x.* from m_pkbResultParamAnnotation_valueStr x, tts where x.ts <= tts.ts) b on a.pkbResultParam1 = b.pkbResultParam1 and a.pkbResultParam2 = b.pkbResultParam2 and a.name = b.name ,tts where b.ts <= tts.ts group by  a.pkbResultParam1 ,a.pkbResultParam2 ,a.name ) aa left join m_pkbResultParamAnnotation_valueStr bb on aa.pkbResultParam1 = bb.pkbResultParam1 and aa.pkbResultParam2 = bb.pkbResultParam2 and aa.name = bb.name and aa.ts = bb.ts) x1 on x1.pkbResultParam1 = x.pkbResultParam1 and x1.pkbResultParam2 = x.pkbResultParam2 and x1.name = x.name left join( select  aa.pkbResultParam1 ,aa.pkbResultParam2 ,aa.name ,bb.valueInt from ( select a.pkbResultParam1, a.pkbResultParam2, a.name, max(b.ts) ts from m_pkbResultParamAnnotation a left join (select x.* from m_pkbResultParamAnnotation_valueInt x, tts where x.ts <= tts.ts) b on a.pkbResultParam1 = b.pkbResultParam1 and a.pkbResultParam2 = b.pkbResultParam2 and a.name = b.name ,tts where b.ts <= tts.ts group by  a.pkbResultParam1 ,a.pkbResultParam2 ,a.name ) aa left join m_pkbResultParamAnnotation_valueInt bb on aa.pkbResultParam1 = bb.pkbResultParam1 and aa.pkbResultParam2 = bb.pkbResultParam2 and aa.name = bb.name and aa.ts = bb.ts) x2 on x2.pkbResultParam1 = x.pkbResultParam1 and x2.pkbResultParam2 = x.pkbResultParam2 and x2.name = x.name left join( select  aa.pkbResultParam1 ,aa.pkbResultParam2 ,aa.name ,bb.type from ( select a.pkbResultParam1, a.pkbResultParam2, a.name, max(b.ts) ts from m_pkbResultParamAnnotation a left join (select x.* from m_pkbResultParamAnnotation_type x, tts where x.ts <= tts.ts) b on a.pkbResultParam1 = b.pkbResultParam1 and a.pkbResultParam2 = b.pkbResultParam2 and a.name = b.name ,tts where b.ts <= tts.ts group by  a.pkbResultParam1 ,a.pkbResultParam2 ,a.name ) aa left join m_pkbResultParamAnnotation_type bb on aa.pkbResultParam1 = bb.pkbResultParam1 and aa.pkbResultParam2 = bb.pkbResultParam2 and aa.name = bb.name and aa.ts = bb.ts) x3 on x3.pkbResultParam1 = x.pkbResultParam1 and x3.pkbResultParam2 = x.pkbResultParam2 and x3.name = x.name left join( select  aa.pkbResultParam1 ,aa.pkbResultParam2 ,aa.name ,bb.hello from ( select a.pkbResultParam1, a.pkbResultParam2, a.name, max(b.ts) ts from m_pkbResultParamAnnotation a left join (select x.* from m_pkbResultParamAnnotation_hello x, tts where x.ts <= tts.ts) b on a.pkbResultParam1 = b.pkbResultParam1 and a.pkbResultParam2 = b.pkbResultParam2 and a.name = b.name ,tts where b.ts <= tts.ts group by  a.pkbResultParam1 ,a.pkbResultParam2 ,a.name ) aa left join m_pkbResultParamAnnotation_hello bb on aa.pkbResultParam1 = bb.pkbResultParam1 and aa.pkbResultParam2 = bb.pkbResultParam2 and aa.name = bb.name and aa.ts = bb.ts) x4 on x4.pkbResultParam1 = x.pkbResultParam1 and x4.pkbResultParam2 = x.pkbResultParam2 and x4.name = x.name left join( select  aa.pkbResultParam1 ,aa.pkbResultParam2 ,aa.name ,bb.goodby from ( select a.pkbResultParam1, a.pkbResultParam2, a.name, max(b.ts) ts from m_pkbResultParamAnnotation a left join (select x.* from m_pkbResultParamAnnotation_goodby x, tts where x.ts <= tts.ts) b on a.pkbResultParam1 = b.pkbResultParam1 and a.pkbResultParam2 = b.pkbResultParam2 and a.name = b.name ,tts where b.ts <= tts.ts group by  a.pkbResultParam1 ,a.pkbResultParam2 ,a.name ) aa left join m_pkbResultParamAnnotation_goodby bb on aa.pkbResultParam1 = bb.pkbResultParam1 and aa.pkbResultParam2 = bb.pkbResultParam2 and aa.name = bb.name and aa.ts = bb.ts) x5 on x5.pkbResultParam1 = x.pkbResultParam1 and x5.pkbResultParam2 = x.pkbResultParam2 and x5.name = x.name) select * from xx where xx.pkbResultParam1 = #{pkbResultParam1} and xx.pkbResultParam2 = #{pkbResultParam2}")
      List<PkbResultParamAnnotation> loadListAt(@Param("ts") Date ts,
          @Param("pkbResultParam1") long pkbResultParam1,
          @Param("pkbResultParam2") String pkbResultParam2);
  
  @Insert("insert into m_pkbResultParamAnnotation_valueStr (pkbResultParam1, pkbResultParam2, name, valueStr) values (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{valueStr})")
      void insValueStr(PkbResultParamAnnotation pkbResultParamAnnotation);
  
  @Insert("insert into m_pkbResultParamAnnotation_valueStr (pkbResultParam1, pkbResultParam2, name, valueStr) values (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{valueStr})")
      void setValueStr(@Param("pkbResultParam1") long pkbResultParam1,
          @Param("pkbResultParam2") String pkbResultParam2, @Param("name") String name,
          @Param("valueStr") String valueStr);
  
  @Insert("insert into m_pkbResultParamAnnotation_valueInt (pkbResultParam1, pkbResultParam2, name, valueInt) values (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{valueInt})")
      void insValueInt(PkbResultParamAnnotation pkbResultParamAnnotation);
  
  @Insert("insert into m_pkbResultParamAnnotation_valueInt (pkbResultParam1, pkbResultParam2, name, valueInt) values (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{valueInt})")
      void setValueInt(@Param("pkbResultParam1") long pkbResultParam1,
          @Param("pkbResultParam2") String pkbResultParam2, @Param("name") String name,
          @Param("valueInt") Integer valueInt);
  
  @Insert("insert into m_pkbResultParamAnnotation_type (pkbResultParam1, pkbResultParam2, name, type) values (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{type})")
      void insType(PkbResultParamAnnotation pkbResultParamAnnotation);
  
  @Insert("insert into m_pkbResultParamAnnotation_type (pkbResultParam1, pkbResultParam2, name, type) values (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{type})")
      void setType(@Param("pkbResultParam1") long pkbResultParam1,
          @Param("pkbResultParam2") String pkbResultParam2, @Param("name") String name,
          @Param("type") AnnotationType type);
  
  @Insert("insert into m_pkbResultParamAnnotation_hello (pkbResultParam1, pkbResultParam2, name, hello) values (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{hello})")
      void insHello(PkbResultParamAnnotation pkbResultParamAnnotation);
  
  @Insert("insert into m_pkbResultParamAnnotation_hello (pkbResultParam1, pkbResultParam2, name, hello) values (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{hello})")
      void setHello(@Param("pkbResultParam1") long pkbResultParam1,
          @Param("pkbResultParam2") String pkbResultParam2, @Param("name") String name,
          @Param("hello") HelloEnum hello);
  
  @Insert("insert into m_pkbResultParamAnnotation_goodby (pkbResultParam1, pkbResultParam2, name, goodby) values (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{goodby})")
      void insGoodby(PkbResultParamAnnotation pkbResultParamAnnotation);
  
  @Insert("insert into m_pkbResultParamAnnotation_goodby (pkbResultParam1, pkbResultParam2, name, goodby) values (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, current_timestamp)")
      void insGoodbyWithNow(PkbResultParamAnnotation pkbResultParamAnnotation);
  
  @Insert("insert into m_pkbResultParamAnnotation_goodby (pkbResultParam1, pkbResultParam2, name, goodby) values (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, #{goodby})")
      void setGoodby(@Param("pkbResultParam1") long pkbResultParam1,
          @Param("pkbResultParam2") String pkbResultParam2, @Param("name") String name,
          @Param("goodby") Date goodby);
  
  @Insert("insert into m_pkbResultParamAnnotation_goodby (pkbResultParam1, pkbResultParam2, name, goodby) values (#{pkbResultParam1}, #{pkbResultParam2}, #{name}, current_timestamp)")
      void setGoodbyWithNow(@Param("pkbResultParam1") long pkbResultParam1,
          @Param("pkbResultParam2") String pkbResultParam2, @Param("name") String name);
  
  @Select("select * from v_pkbResultParamAnnotation order by valueStr, valueInt")
  List<PkbResultParamAnnotation> selectAll();
}
