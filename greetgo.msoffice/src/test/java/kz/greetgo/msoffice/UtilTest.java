package kz.greetgo.msoffice;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.Assert;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UtilTest {
  
  private final Map<String, String> map = new HashMap<String, String>();
  
  @BeforeMethod
  public void prepareExcelMap() {
    map.clear();
    
    map.put("2011-11-11T11:11:11Z", "40858.466099537036");
    map.put("2011-11-11T17:55:07Z", "40858.7466099537036");
  }
  
  @Test
  public void excelToDate() {
    for (Entry<String, String> e : map.entrySet()) {
      Date d = UtilOffice.excelToDate(e.getValue());
      Assert.assertEquals("Для excel-даты [" + e.getValue() + "]", e.getKey(),
          UtilOffice.toW3CDTF(d));
    }
  }
  
  @Test
  public void toExcelDateTime() {
    for (Entry<String, String> e : map.entrySet()) {
      String sActual = UtilOffice.toExcelDateTime(UtilOffice.parseW3CDTF(e.getKey()));
      String sExpected = e.getValue();
      assertAbout("Для java-даты [" + e.getKey() + "]", 8e-6, sExpected, sActual);
    }
  }
  
  private static void assertAbout(String message, double eps, String expected, String actual) {
    double delta = new BigDecimal(expected).subtract(new BigDecimal(actual)).abs().doubleValue();
    Assert.assertTrue(message + ": expected = " + expected + ", actual = " + actual + ", delta = "
        + delta + ", but eps = " + eps, delta <= eps);
  }
}