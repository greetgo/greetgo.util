package kz.greetgo.util;

import static org.fest.assertions.api.Assertions.assertThat;

import org.testng.annotations.Test;

public class StrReplacerTest {
  @Test
  public void test1() throws Exception {
    StrReplacer sr = new StrReplacer();
    sr.add("Привет всем {asd} fdsfdsf {no} qwerty");
    sr.prm("asd", "WOW");
    
    assertThat(sr.toString()).isEqualTo("Привет всем WOW fdsfdsf {no} qwerty");
  }
  
  @Test
  public void test2() throws Exception {
    StrReplacer sr = new StrReplacer();
    sr.add("Привет всем {asd}");
    sr.prm("asd", "WOW");
    
    assertThat(sr.toString()).isEqualTo("Привет всем WOW");
  }
  
  @Test
  public void test3() throws Exception {
    StrReplacer sr = new StrReplacer();
    sr.add("Привет всем {asd}");
    
    assertThat(sr.toString()).isEqualTo("Привет всем {asd}");
  }
}
