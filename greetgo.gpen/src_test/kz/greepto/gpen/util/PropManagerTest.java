package kz.greepto.gpen.util;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Map;

import org.testng.annotations.Test;

public class PropManagerTest {
  
  public static class Asd {
    public String strField;
    private String strProperty;
    
    public int intField;
    private int intProperty;
    
    public String getStrProperty() {
      return strProperty;
    }
    
    public void setStrProperty(String strProperty) {
      this.strProperty = strProperty;
    }
    
    public int getIntProperty() {
      return intProperty;
    }
    
    public void setIntProperty(int intProperty) {
      this.intProperty = intProperty;
    }
    
  }
  
  @Test
  public void get_set_str() throws Exception {
    Asd asd = new Asd();
    
    PropManagerObject pm = new PropManagerObject(asd);
    
    String s1 = "fdsaffds786fa78ds56f876as5df85a";
    String s2 = "o4m3on54n657hv8fg78cx99f80d76f6";
    
    pm.set("strField", s1);
    pm.set("strProperty", s2);
    
    assertThat(pm.get("strField")).isEqualTo(s1);
    assertThat(asd.strField).isEqualTo(s1);
    assertThat(pm.get("strProperty")).isEqualTo(s2);
    assertThat(asd.getStrProperty()).isEqualTo(s2);
  }
  
  @Test
  public void get_set_asStr_str() throws Exception {
    Asd asd = new Asd();
    
    PropManagerObject pm = new PropManagerObject(asd);
    
    String s1 = "fdsaffds786fa78ds56f876as5df85a";
    String s2 = "o4m3on54n657hv8fg78cx99f80d76f6";
    
    pm.setAsStr("strField", s1);
    pm.setAsStr("strProperty", s2);
    
    assertThat(pm.getAsStr("strField")).isEqualTo(s1);
    assertThat(asd.strField).isEqualTo(s1);
    assertThat(pm.getAsStr("strProperty")).isEqualTo(s2);
    assertThat(asd.getStrProperty()).isEqualTo(s2);
  }
  
  @Test
  public void get_set_int() throws Exception {
    Asd asd = new Asd();
    
    PropManagerObject pm = new PropManagerObject(asd);
    
    int s1 = 56436;
    int s2 = 98765;
    
    pm.set("intField", s1);
    pm.set("intProperty", s2);
    
    assertThat(pm.get("intField")).isEqualTo(s1);
    assertThat(asd.intField).isEqualTo(s1);
    assertThat(pm.get("intProperty")).isEqualTo(s2);
    assertThat(asd.getIntProperty()).isEqualTo(s2);
  }
  
  @Test
  public void get_set_asStr_int() throws Exception {
    Asd asd = new Asd();
    
    PropManagerObject pm = new PropManagerObject(asd);
    
    int s1 = 56436;
    int s2 = 98765;
    
    pm.setAsStr("intField", "" + s1);
    pm.setAsStr("intProperty", "" + s2);
    
    assertThat(pm.getAsStr("intField")).isEqualTo("" + s1);
    assertThat(asd.intField).isEqualTo(s1);
    assertThat(pm.getAsStr("intProperty")).isEqualTo("" + s2);
    assertThat(asd.getIntProperty()).isEqualTo(s2);
  }
  
  @Test
  public void meta() throws Exception {
    Asd asd = new Asd();
    
    PropManagerObject pm = new PropManagerObject(asd);
    
    Map<String, Class<?>> meta = pm.meta();
    
    assertThat(meta.get("strField").getName()).isEqualTo(String.class.getName());
    assertThat(meta.get("strProperty").getName()).isEqualTo(String.class.getName());
    
    assertThat(meta.get("intField").getName()).isEqualTo(Integer.TYPE.getName());
    assertThat(meta.get("intProperty").getName()).isEqualTo(Integer.TYPE.getName());
  }
}
