package kz.greetgo.util;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Method;

import org.testng.annotations.Test;

public class ServerUtilTest {
  
  @Test
  public void extractPackage_1() throws Exception {
    final String aPackage = ServerUtil.extractPackage("asd.dsa.Asd");
    assertThat(aPackage).isEqualTo("asd.dsa");
  }
  
  @Test
  public void extractPackage_2() throws Exception {
    final String aPackage = ServerUtil.extractPackage("Asd");
    assertThat(aPackage).isNull();
  }
  
  @Test
  public void resolveFile_1() throws Exception {
    final File file = ServerUtil.resolveFile("some/dir", "asd.dsa.Asd", null);
    assertThat(file.getPath()).isEqualTo("some/dir/asd/dsa/Asd.java");
  }
  
  @Test
  public void resolveFile_2() throws Exception {
    final File file = ServerUtil.resolveFile("some/dir", "asd.dsa.Asd", ".java");
    assertThat(file.getPath()).isEqualTo("some/dir/asd/dsa/Asd.java");
  }
  
  @Test
  public void extractName() throws Exception {
    final String name = ServerUtil.extractName("asd.dsa.Asd");
    assertThat(name).isEqualTo("Asd");
  }
  
  private static class TestClass implements Serializable {
    int intField;
    String strField;
  }
  
  @Test
  public void java_serialize_deserialize() throws Exception {
    TestClass original = new TestClass();
    original.intField = RND.plusInt(1_000_000);
    original.strField = RND.str(10);
    
    //
    //
    byte[] bytes = ServerUtil.javaSerialize(original);
    //
    //
    
    assertThat(bytes).isNotNull();
    
    //
    //
    TestClass actual = ServerUtil.javaDeserialize(bytes);
    //
    //
    
    assertThat(actual).isNotNull();
    assertThat(actual.intField).isEqualTo(original.intField);
    assertThat(actual.strField).isEqualTo(original.strField);
  }
  
  @Test
  public void trimLeft() throws Exception {
    assertThat(ServerUtil.trimLeft("   asd  ")).isEqualTo("asd  ");
  }
  
  @Test
  public void trimRight() throws Exception {
    assertThat(ServerUtil.trimRight("   asd     ")).isEqualTo("   asd");
  }
  
  @Test
  public void fnn_001() throws Exception {
    assertThat(ServerUtil.fnn(null, "asd", "dsa")).isEqualTo("asd");
  }
  
  @Test
  public void fnn_002() throws Exception {
    assertThat(ServerUtil.fnn(null, null, null)).isNull();
  }
  
  @Test
  public void getAnnotation() throws Exception {
    
    class ParentAsd {
      @Test
      public void asd() {}
    }
    
    class ChildAsd extends ParentAsd {
      public void asd() {}
      
      @SuppressWarnings("unused")
      public void noIt() {}
      
      @Test
      public void iHave() {}
    }
    
    {
      Method method = ChildAsd.class.getMethod("asd");
      
      Test test = ServerUtil.getAnnotation(method, Test.class);
      
      assertThat(test).isNotNull();
    }
    {
      Method method = ChildAsd.class.getMethod("noIt");
      
      Test test = ServerUtil.getAnnotation(method, Test.class);
      
      assertThat(test).isNull();
    }
    {
      Method method = ChildAsd.class.getMethod("iHave");
      
      Test test = ServerUtil.getAnnotation(method, Test.class);
      
      assertThat(test).isNotNull();
    }
  }
}
