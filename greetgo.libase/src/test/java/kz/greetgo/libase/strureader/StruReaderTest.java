package kz.greetgo.libase.strureader;

import java.sql.Connection;
import java.sql.DriverManager;

import kz.greetgo.libase.model.DbStru;
import kz.greetgo.libase.model.Relation;
import kz.greetgo.libase.model.Sequence;
import kz.greetgo.libase.model.StoreFunc;
import kz.greetgo.libase.model.Trigger;
import kz.greetgo.libase.model.View;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class StruReaderTest {
  
  private Connection connection;
  
  @BeforeClass
  private void beforeClass() throws Exception {
    Class.forName("org.postgresql.Driver");
    
    connection = DriverManager.getConnection("jdbc:postgresql:scoring_diff", "scoring_diff", "");
  }
  
  @AfterClass
  private void afterClass() throws Exception {
    connection.close();
    connection = null;
  }
  
  @Test
  public void read() throws Exception {
    RowReaderPostgres rowReader = new RowReaderPostgres(connection);
    DbStru stru = StruReader.read(rowReader);
    for (Relation r : stru.relations.values()) {
      if (r instanceof View) {
        System.out.println(r);
      }
    }
    for (Sequence s : stru.sequences) {
      System.out.println(s);
    }
    
    for (StoreFunc sf : stru.funcs.values()) {
      System.out.println(sf);
    }
    
    for (Trigger t : stru.triggers.values()) {
      System.out.println(t);
    }
  }
}
