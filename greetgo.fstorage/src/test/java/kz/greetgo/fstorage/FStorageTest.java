package kz.greetgo.fstorage;

import static org.fest.assertions.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import org.testng.annotations.Test;

public class FStorageTest extends MyTestBase {
  
  private static class LocalFileDot {
    long id;
    String filename;
    byte[] data;
  }
  
  private static final String ABC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String abc = "abcdefghijklmnopqrstuvwxyz";
  private static final String DEG = "0123456789";
  private static final String ALL_ENG = ABC + abc + DEG;
  
  private static final String rndStr(int len, Random rnd) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < len; i++) {
      sb.append(ALL_ENG.charAt(rnd.nextInt(ALL_ENG.length())));
    }
    return sb.toString();
  }
  
  private static final byte rndByte(Random rnd) {
    return (byte)rnd.nextInt(256);
  }
  
  @Test(dataProvider = DATA_PROVIDER)
  public void addNewFile_getFile(DataSource dataSource) throws Exception {
    Random rnd = new Random();
    
    final List<LocalFileDot> lfdList = new ArrayList<>();
    
    for (int i = 0; i < 110; i++) {
      LocalFileDot x = new LocalFileDot();
      x.filename = rndStr(10, rnd);
      x.data = new byte[10 + rnd.nextInt(10)];
      for (int j = 0, C = x.data.length; j < C; j++) {
        x.data[j] = rndByte(rnd);
      }
      lfdList.add(x);
    }
    
    FStorageFactory f = new FStorageFactory();
    f.setDataSource(dataSource);
    f.setFieldFilenameLen(100);
    f.setTableCount(10);
    f.setTableName("test_file_table");
    
    dropAllTables(dataSource.getConnection(), "test_file_table", 10);
    
    FStorage fs = f.create();
    
    for (LocalFileDot lfd : lfdList) {
      lfd.id = fs.addNewFile(new FileDot(lfd.filename, lfd.data));
    }
    
    for (LocalFileDot lfd : lfdList) {
      FileDot fd = fs.getFile(lfd.id);
      assertThat(fd).isNotNull();
      assertThat(fd.filename).isEqualTo(lfd.filename);
      assertThat(fd.data).isEqualTo(lfd.data);
    }
  }
  
  private void dropAllTables(Connection con, String tableSuffix, int tableCount)
      throws SQLException {
    
    int size = 0;
    {
      int a = tableCount;
      while (a > 0) {
        size++;
        a = a / 10;
      }
    }
    
    for (int i = 0; i < tableCount; i++) {
      
      String nom = "" + i;
      while (nom.length() < size) {
        nom = "0" + nom;
      }
      
      String tn = tableSuffix + tableCount + '_' + nom;
      
      queryForce(con, "drop table " + tn);
    }
    
    queryForce(con, "drop sequence " + tableSuffix + "_seq");
    
    con.close();
  }
}
