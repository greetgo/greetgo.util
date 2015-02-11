package kz.greetgo.teamcity.soundir.probes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import kz.greetgo.teamcity.soundir.configs.FromDb;

public class ProbeStoredProcedure {
  public static void main(String[] args) throws Exception {
    new ProbeStoredProcedure().run();
  }
  
  private void run() throws Exception {
    Connection con = FromDb.createCon();
    try {
      runCon(con);
    } finally {
      con.close();
    }
  }
  
  private void runCon(Connection con) throws Exception {
    
    CallableStatement cs = con.prepareCall("{call probe(?)}");
    
    try {
      cs.setString(1, "DDDDDDDDD ");
      ResultSet rs = cs.executeQuery();
      try {
        while (rs.next()) {
          int id = rs.getInt(1);
          String name = rs.getString(2);
          System.out.println("id = " + id + ", name = " + name);
        }
      } finally {
        rs.close();
      }
    } finally {
      cs.close();
    }
    
  }
}
