package kz.greetgo.interpreter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import kz.greetgo.simpleInterpretator.DateConverterStd;
import kz.greetgo.simpleInterpretator.HelperDate;
import kz.greetgo.simpleInterpretator.Interpreter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class InterpreterTest {
  @Test
  public void asd() {
    Interpreter u = new Interpreter();
    u.addValue("ASD", 123L);
    u.addValue("DSa", 987);
    u.addValue("X", -333);
    
    u.compileString("1+ASD/(1+DSA*ASD- модуль(1 + (не x)))");
    
    System.out.println(u);
    System.out.println(u.evaluate());
  }
  
  @Test
  public void asd2() {
    Interpreter u = new Interpreter();
    u.addValue("ASD", 123L);
    u.addValue("DSa", "aq");
    u.addValue("X", -333);
    
    u.compileString("'aq'==dsa");
    
    System.out.println(u);
    System.out.println(u.evaluate());
  }
  
  @Test
  public void asd_null() {
    Interpreter u = new Interpreter();
    u.addValue("ASD", null);
    u.addValue("DSa", null);
    u.addValue("X", -333);
    
    u.compileString("ASD = 'ab'");
    
    System.out.println(u);
    System.out.println(u.evaluate());
    
    u.compileString("ASD = Dsa");
    
    System.out.println(u);
    System.out.println(u.evaluate());
  }
  
  @Test
  public void asd_LK_CASH_CREDIT_LIMIT() {
    Interpreter u = new Interpreter();
    u.addValue("LK_CASH_CREDIT_LIMIT", 10000);
    u.addValue("FIO_01", "Петров");
    u.addValue("X", -333);
    
    u.compileString("LK_CASH_CREDIT_LIMIT >= 1000");
    
    System.out.println(u);
    System.out.println(u.evaluate());
    
    u.compileString("FIO_01 = 'Петров'");
    
    System.out.println(u);
    System.out.println(u.evaluate());
    
  }
  
  @Test
  public void asd_dot() {
    Interpreter u = new Interpreter();
    u.addValue("LK_CASH_CREDIT_LIMIT", 10000);
    u.addValue("AIS.operator.fio", "Петров");
    u.addValue("X", -333);
    
    u.compileString("AIS.operator.fio = 'Петров'");
    
    System.out.println(u);
    System.out.println(u.evaluate());
    
  }
  
  @BeforeClass
  public void setup() {
    HelperDate.defineDateConverter(new DateConverterStd());
  }
  
  @Test
  public void asd_date() throws ParseException {
    Interpreter u = new Interpreter();
    u.addValue("d1", new SimpleDateFormat("yyyyMMdd").parse("20120406"));
    
    u.compileString("d1 = {06.04.2012}");
    
    System.out.println(u);
    System.out.println(u.evaluate());
    
    u.compileString("d1 = {06/04/2012 11:32}");
    
    System.out.println(u);
    System.out.println(u.evaluate());
    
    u.compileString("d1 = {2012-04-06 11:13}");
    
    System.out.println(u);
    System.out.println(u.evaluate());
    
    Object d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2012-04-06 11:13:57");
    Object d2 = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse("2012/04/06 11:13");
    
    System.out.println(d1.equals(d2));
    
    u.addValue("d2", new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2012-04-06 11:32"));
    
    u.compileString("d2 = {06/04/2012 11:32}");
    
    System.out.println(u);
    System.out.println(u.evaluate());
  }
}
