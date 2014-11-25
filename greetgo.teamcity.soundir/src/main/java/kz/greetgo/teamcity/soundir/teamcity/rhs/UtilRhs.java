package kz.greetgo.teamcity.soundir.teamcity.rhs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilRhs {
  public static Date strToDate(String str) {
    if (str == null) return null;
    {
      int idx = str.indexOf('+');
      if (idx >= 0) {
        str = str.substring(0, idx);
      }
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
    try {
      return sdf.parse(str);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
}
