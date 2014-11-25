package kz.greetgo.teamcity.soundir.configs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FromDb {
  
  private static Connection createCon() throws Exception {
    Class.forName("org.postgresql.Driver");
    return DriverManager.getConnection("jdbc:postgresql://192.168.0.1:5432/com", "greetgo",
        "greetgo");
  }
  
  public static Map<String, List<BuildTypeEmployeeMessage>> loadMessageMap() {
    Map<String, List<BuildTypeEmployeeMessage>> ret = new HashMap<>();
    
    try {
      fillMessageMap(ret);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    
    return ret;
  }
  
  public static Map<String, List<BuildTypeEmployeeMessage>> loadTotalMessageMap() {
    Map<String, List<BuildTypeEmployeeMessage>> ret = new HashMap<>();
    
    try {
      fillTotalMessageMap(ret);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    
    return ret;
  }
  
  private static void fillMessageMap(Map<String, List<BuildTypeEmployeeMessage>> ret)
      throws Exception {
    Connection con = createCon();
    try {
      fillMassageMapFromCon(ret, con);
    } finally {
      con.close();
    }
  }
  
  private static void fillTotalMessageMap(Map<String, List<BuildTypeEmployeeMessage>> ret)
      throws Exception {
    Connection con = createCon();
    try {
      fillTotalMassageMapFromCon(ret, con);
    } finally {
      con.close();
    }
  }
  
  private static void fillMassageMapFromCon(Map<String, List<BuildTypeEmployeeMessage>> ret,
      Connection con) throws Exception {
    
    StringBuilder sql = new StringBuilder();
    sql.append("                                                                                 ");
    sql.append(" with                                                                            ");
    sql.append("   btm as (select buildType, prj, message from buildtype_message where act = 1)  ");
    sql.append(" ,  ep  as (select employee, prj from v_employee_prj where act = 1)              ");
    sql.append(" ,  e   as (select * from employee where act = 1)                                ");
    sql.append(" ,  rp  as (select distinct prj, room from ep natural join e)                    ");
    sql.append(" ,  re  as (select room, employee, email from employee                           ");
    sql.append("              where act = 1 and has_sound = 1)                                   ");
    sql.append(" ,  pe  as (select prj, employee, email from re natural join rp)                 ");
    sql.append("                                                                                 ");
    sql.append(" select buildType, employee, email, message from btm natural join pe             ");
    sql.append("                                                                                 ");
    
    PreparedStatement ps = con.prepareStatement(sql.toString());
    try {
      ResultSet rs = ps.executeQuery();
      try {
        while (rs.next()) {
          BuildTypeEmployeeMessage x = new BuildTypeEmployeeMessage();
          x.buildType = rs.getString("buildType");
          x.employee = rs.getString("employee");
          x.email = rs.getString("email");
          x.message = rs.getString("message");
          
          List<BuildTypeEmployeeMessage> list = ret.get(x.buildType);
          if (list == null) {
            list = new ArrayList<>();
            ret.put(x.buildType, list);
          }
          list.add(x);
        }
      } finally {
        rs.close();
      }
    } finally {
      ps.close();
    }
  }
  
  private static void fillTotalMassageMapFromCon(Map<String, List<BuildTypeEmployeeMessage>> ret,
      Connection con) throws Exception {
    
    StringBuilder sql = new StringBuilder();
    sql.append("                                                                                 ");
    sql.append(" with                                                                            ");
    sql.append("    btm as (select buildType, prj, message from buildtype_message where act = 1) ");
    sql.append(" ,  ep  as (select employee, prj from v_employee_prj where act = 1)              ");
    sql.append(" ,  e   as (select * from employee where act = 1)                                ");
    sql.append(" ,  rp  as (select prj.prj, room.room from prj, room                             ");
    sql.append("               where prj.act = 1 and room.act = 1)                               ");
    sql.append(" ,  re  as (select room, employee, email from employee                           ");
    sql.append("               where act = 1 and has_sound = 1)                                  ");
    sql.append(" ,  pe  as (select prj, employee, email from re natural join rp)                 ");
    sql.append("                                                                                 ");
    sql.append(" select buildType, employee, email, message from btm natural join pe             ");
    sql.append("                                                                                 ");
    
    PreparedStatement ps = con.prepareStatement(sql.toString());
    try {
      ResultSet rs = ps.executeQuery();
      try {
        while (rs.next()) {
          BuildTypeEmployeeMessage x = new BuildTypeEmployeeMessage();
          x.buildType = rs.getString("buildType");
          x.employee = rs.getString("employee");
          x.email = rs.getString("email");
          x.message = rs.getString("message");
          
          List<BuildTypeEmployeeMessage> list = ret.get(x.buildType);
          if (list == null) {
            list = new ArrayList<>();
            ret.put(x.buildType, list);
          }
          list.add(x);
        }
      } finally {
        rs.close();
      }
    } finally {
      ps.close();
    }
  }
  
  public static Map<String, List<BuildTypeEmployeeMessage>> getAssignedMessageMap() {
    Map<String, List<BuildTypeEmployeeMessage>> small = loadMessageMap();
    Map<String, List<BuildTypeEmployeeMessage>> total = loadTotalMessageMap();
    
    for (Entry<String, List<BuildTypeEmployeeMessage>> e : total.entrySet()) {
      if (!small.containsKey(e.getKey())) {
        small.put(e.getKey(), e.getValue());
      }
    }
    
    return small;
  }
}
