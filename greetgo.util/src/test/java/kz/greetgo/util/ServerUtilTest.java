package kz.greetgo.util;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;

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
}
