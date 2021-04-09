package kz.greetgo.util;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;


public class RNDTest {

  @Test
  public void strEng() {
    assertThat(RND.strEng(10)).hasSize(10);
    assertThat(RND.strEng(13)).isNotEqualTo(RND.strEng(13));

    String s = RND.strEng(1300);
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if ('0' <= c && c <= '9') { continue; }
      if ('a' <= c && c <= 'z') { continue; }
      if ('A' <= c && c <= 'Z') { continue; }
      Assertions.fail("Left char '" + c + "'");
    }

  }

  @Test
  public void str() {
    assertThat(RND.str(10)).hasSize(10);
    assertThat(RND.str(13)).isNotEqualTo(RND.str(13));

    Set<Character> strChars = new HashSet<>();
    for (char c : RND.ALL_CHARS) {
      strChars.add(c);
    }

    String s = RND.str(1301);
    for (int i = 0; i < s.length(); i++) {
      if (strChars.contains(s.charAt(i))) { continue; }
      Assertions.fail("Left char '" + s.charAt(i) + "'");
    }
  }

  @Test
  public void strInt() {
    assertThat(RND.strInt(10)).hasSize(10);
    assertThat(RND.strInt(13)).isNotEqualTo(RND.strInt(13));

    String s = RND.strInt(1307);
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if ('0' <= c && c <= '9') { continue; }
      Assertions.fail("Left char '" + s.charAt(i) + "'");
    }
  }

  @Test
  public void dateYears() {

    for (int i = 0; i < 10_000; i++) {

      Date date = RND.dateYears(-100, -10);
      Calendar calendar = new GregorianCalendar();
      int nowYear = calendar.get(Calendar.YEAR);
      calendar.setTime(date);
      int year = calendar.get(Calendar.YEAR);
      assertThat(year).isGreaterThanOrEqualTo(nowYear - 100);
      assertThat(year).isLessThanOrEqualTo(nowYear - 10);

    }
  }

  private static long getBigDay(Date date) {
    long time = (date == null ? new Date() : date).getTime();
    return time / (1000L * 60L * 60L * 24L);
  }

  @Test
  public void dateDays() {
    int from = -10, to = -3;

    long bigDayFrom, bigDayTo;

    {
      Calendar calendar = new GregorianCalendar();
      calendar.add(Calendar.DAY_OF_YEAR, from);
      bigDayFrom = getBigDay(calendar.getTime());
    }
    {
      Calendar calendar = new GregorianCalendar();
      calendar.add(Calendar.DAY_OF_YEAR, to);
      bigDayTo = getBigDay(calendar.getTime());
    }

    String description = "bigDayFrom = " + bigDayFrom + ", bigDayTo = " + bigDayTo;

    for (int i = 0; i < 10_000; i++) {

      Date date = RND.dateDays(from, to);

      long bigDay = getBigDay(date);
      long bigDayNow = getBigDay(null);

      assertThat(bigDay).describedAs(description).isGreaterThanOrEqualTo(bigDayNow + from);
      assertThat(bigDay).describedAs(description).isLessThanOrEqualTo(bigDayNow + to);

    }
  }

  @Test
  public void bool() {
    for (long u = 0; u < 1000L; u++) {

      int trueCount = 0, allCount = RND.plusInt(2000) + 3000;
      for (int i = 0; i < allCount; i++) {
        if (RND.bool()) { trueCount++; }
      }

      double x = (double) trueCount / (double) allCount;
//      System.out.println(x);

      assertThat(x).isGreaterThan(0.4d);
      assertThat(x).isLessThan(0.6d);
    }
  }

  enum SomeEnum {
    E1, E2, E3, E4, E5
  }

  @Test
  public void someEnum() {
    Map<SomeEnum, AtomicInteger> map = new HashMap<>();
    for (SomeEnum someEnum : SomeEnum.values()) {
      map.put(someEnum, new AtomicInteger(0));
    }

    int count = RND.plusInt(2000) + 3000;
    for (int i = 0; i < count; i++) {
      map.get(RND.someEnum(SomeEnum.values())).incrementAndGet();
    }

    assertThat(map.get(SomeEnum.E1).intValue()).isGreaterThan(10);
    assertThat(map.get(SomeEnum.E2).intValue()).isGreaterThan(10);
    assertThat(map.get(SomeEnum.E3).intValue()).isGreaterThan(10);
    assertThat(map.get(SomeEnum.E4).intValue()).isGreaterThan(10);
    assertThat(map.get(SomeEnum.E5).intValue()).isGreaterThan(10);

  }

  @Test
  public void plusDouble() {
    assertThat(RND.plusDouble(10, 2)).isLessThan(10);
    assertThat(RND.plusDouble(10, 2)).isGreaterThanOrEqualTo(0);
  }

  @Test
  public void bd() {
    assertThat(RND.bd(10, 2)).isLessThan(new BigDecimal("10"));
    assertThat(RND.bd(10, 2)).isGreaterThanOrEqualTo(BigDecimal.ZERO);
  }

  @Test
  public void test_from() {
    assertThat(RND.from(1, 2, 3)).isIn(1, 2, 3);
  }

  @Test
  public void test_of() {
    ArrayList<String> list = new ArrayList<>();
    list.add("a1");
    list.add("b2");
    list.add("asd");
    assertThat(RND.of(list)).isIn("a1", "b2", "asd");
  }
}
