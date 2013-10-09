package kz.greetgo.smartgwt.share.utils;

import java.util.Date;

import com.google.common.collect.Range;
import com.google.common.collect.Ranges;

public final class DateSetup {
  public final Date date;
  
  public static final long MILLISECONDS_IN_MINUTE = 60 * 1000;
  public static final long MILLISECONDS_IN_DAY = 24 * 60 * MILLISECONDS_IN_MINUTE;
  
  public DateSetup(Date date) {
    this.date = (Date)date.clone();
  }
  
  public DateSetup() {
    this(new Date());
  }
  
  private static final long trunc(long number, long scale) {
    return scale * (number / scale);
  }
  
  @SuppressWarnings("deprecation")
  public final DateSetup resetHours() {
    date.setHours(0);
    return this;
  }
  
  @SuppressWarnings("deprecation")
  public final DateSetup resetMinutes() {
    date.setMinutes(0);
    return this;
  }
  
  public final DateSetup resetSeconds() {
    date.setTime(trunc(date.getTime(), MILLISECONDS_IN_MINUTE));
    return this;
  }
  
  public final DateSetup trunc() {
    return resetHours().resetMinutes().resetSeconds();
  }
  
  @SuppressWarnings("deprecation")
  public final DateSetup addDays(int days) {
    date.setDate(date.getDate() + days);
    return this;
  }
  
  public static final Range<Date> trunc(Range<Date> range) {
    return Ranges.closed(new DateSetup(range.lowerEndpoint()).trunc().date,
        new DateSetup(range.upperEndpoint()).trunc().date);
  }
  
  public static final Range<Date> truncDayUp(Range<Date> range) {
    return Ranges.closed(new DateSetup(range.lowerEndpoint()).trunc().date,
        new DateSetup(range.upperEndpoint()).trunc().addDays(1).date);
  }
}
