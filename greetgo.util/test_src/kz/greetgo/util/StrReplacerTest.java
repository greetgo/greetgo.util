package kz.greetgo.util;

import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class StrReplacerTest {
  @Test
  public void using_prm_and_or_set_prm() throws Exception {
    StrReplacer sr = new StrReplacer();
    sr.add("Привет всем {asd} fds1fds2f {no} qwerty");
    sr.prm("asd", "WOW!");

    assertThat(sr.toString()).isEqualTo("Привет всем WOW fds1fds2f {no} qwerty");
  }

  @Test
  public void using_prm() throws Exception {
    StrReplacer sr = new StrReplacer();
    sr.add("Привет всем {asd}");
    sr.prm("asd", "WOW");

    assertThat(sr.toString()).isEqualTo("Привет всем WOW");
  }

  @Test
  public void not_using_prm() throws Exception {
    StrReplacer sr = new StrReplacer();
    sr.add("Привет всем {asd}");

    assertThat(sr.toString()).isEqualTo("Привет всем {asd}");
  }

  @Test
  public void wrap() throws Exception {
    StrReplacer sr = StrReplacer.wrap("Привет {asd}");
    sr.prm("asd", "HELLO");
    assertThat(sr.toString()).isEqualTo("Привет HELLO");
  }

  @Test
  public void add_start_end() throws Exception {
    {
      StrReplacer sr = new StrReplacer(1000);
      sr.add("hello world", 6, 6 + 5);
      assertThat(sr.toString()).isEqualTo("world");
    }
    {
      StrReplacer sr = new StrReplacer(1000);
      sr.add("Good by world", 0, 4);
      assertThat(sr.toString()).isEqualTo("Good");
    }
  }

  @Test
  public void add_int() throws Exception {
    StrReplacer sr = new StrReplacer();
    sr.add("hello ");
    sr.add(34);
    sr.add(' ');
    sr.add(75);
    sr.add('-');
    sr.add(81);
    assertThat(sr.toString()).isEqualTo("hello 34 75-81");
  }

  @Test
  public void add_Integer() throws Exception {
    StrReplacer sr = new StrReplacer();
    sr.add("hello ");
    sr.add(new Integer(34));
    sr.add(' ');
    sr.add(new Integer(75));
    sr.add('-');
    sr.add(new Integer(81));
    assertThat(sr.toString()).isEqualTo("hello 34 75-81");
  }

  @Test
  public void add_charArray_offset_len() throws Exception {
    char[] toAdd = "Hello world - good by world".toCharArray();

    StrReplacer sr = new StrReplacer();
    sr.add(toAdd, 0, 11);
    sr.add(" and ");
    sr.add(toAdd, 14, 7);

    assertThat(sr.toString()).isEqualTo("Hello world and good by");
  }

  @Test
  public void add_charArray() throws Exception {
    char[] sum = "The Sum".toCharArray();
    char[] moon = "the Moon".toCharArray();

    StrReplacer sr = new StrReplacer();
    sr.add(sum);
    sr.add(" and ");
    sr.add(moon);

    assertThat(sr.toString()).isEqualTo("The Sum and the Moon");
  }

  @Test
  public void add_long() throws Exception {
    StrReplacer sr = new StrReplacer();
    sr.add(123L);
    sr.add(" and ");
    sr.add(4321L);

    assertThat(sr.toString()).isEqualTo("123 and 4321");
  }

  @Test
  public void add_Long() throws Exception {
    StrReplacer sr = new StrReplacer();
    sr.add(new Long(123L));
    sr.add(" and ");
    sr.add(new Long(4321L));

    assertThat(sr.toString()).isEqualTo("123 and 4321");
  }

  @Test
  public void add_StringBuilder() throws Exception {

    StringBuilder sb1 = new StringBuilder("sb 1");
    StringBuilder sb2 = new StringBuilder("sb 2");

    StringBuffer sb3 = new StringBuffer("sb 3");
    StringBuffer sb4 = new StringBuffer("sb 4");

    StrReplacer sr5 = StrReplacer.wrap("sr 4");
    StrReplacer sr6 = StrReplacer.wrap("sr 5");

    StrReplacer sr = new StrReplacer();
    sr.add(sb1).add(", ");
    sr.add(sb2).add(", ");
    sr.add(sb3).add(", ");
    sr.add(sb4).add(", ");
    sr.add(sr5).add(", ");
    sr.add(sr6).add(", ");

    sr.decLen(2);

    assertThat(sr.toString()).isEqualTo("sb 1, sb 2, sb 3, sb 4, sr 4, sr 5");
  }

  @Test
  public void delLen() throws Exception {
    StrReplacer sr = StrReplacer.wrap("Hello world, communism, capitalism");

    sr.decLen("capitalism".length() + 2);

    assertThat(sr.toString()).isEqualTo("Hello world, communism");

    sr.decLen("communism".length() + 2);

    assertThat(sr.toString()).isEqualTo("Hello world");

  }

  @Test
  public void setLen() throws Exception {

    StrReplacer sr = StrReplacer.wrap("Hello world, communism, capitalism");

    sr.setLen("Hello world".length());

    assertThat(sr.toString()).isEqualTo("Hello world");
  }

  @Test
  public void getLen() throws Exception {

    StrReplacer sr = StrReplacer.wrap("Hello world, communism, capitalism");

    assertThat(sr.getLen()).isEqualTo("Hello world, communism, capitalism".length());

    sr.decLen("capitalism".length() + 2);//Deep philosophy... Isn't it?

    assertThat(sr.getLen()).isEqualTo("Hello world, communism".length());//More better ;)
  }
}
