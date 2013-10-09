package kz.greetgo.simpleInterpretator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HelperDate {
  
  public static final DateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
  public static final DateFormat YMD_HM = new SimpleDateFormat("yyyy-MM-dd hh:mm");
  
  public static final DateFormat ruDateFormat = new SimpleDateFormat("dd.MM.yyyy");
  public static final DateFormat ruDateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
  
  public static final DateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
  public static final DateFormat dmy_hmFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
  
  public static Date convertStrToDate(String value) {
    if (value == null) return null;
    value = value.trim();
    if (value.length() == 0) return null;
    
    try {
      return YMD.parse(value);
    } catch (ParseException e) {}
    try {
      return YMD_HM.parse(value);
    } catch (ParseException e) {}
    
    try {
      return ruDateFormat.parse(value);
    } catch (ParseException e) {}
    try {
      return ruDateTimeFormat.parse(value);
    } catch (ParseException e) {}
    
    try {
      return dmy.parse(value);
    } catch (ParseException e) {}
    try {
      return dmy_hmFormat.parse(value);
    } catch (ParseException e) {}
    
    throw new InterpreterError("Неизвестный формат даты и/или времени: " + value);
  }
}