package kz.greetgo.simpleInterpretator;

import java.util.Date;

public class HelperDate {
  
  private static DateConverter converter;
  
  public static void defineDateConverter(DateConverter converter) {
    HelperDate.converter = converter;
  }
  
  public static Date convertStrToDate(String value) {
    return converter.convertStrToDate(value);
  }
}
