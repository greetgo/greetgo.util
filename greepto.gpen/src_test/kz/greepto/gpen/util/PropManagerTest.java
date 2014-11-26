package kz.greepto.gpen.util;

import static org.fest.assertions.api.Assertions.assertThat;

import org.testng.annotations.Test;

public class PropManagerTest {
  
  public static class Asd {
    public String strField;
    private String strProperty;
    
    public String getStrProperty() {
      return strProperty;
    }
    
    public void setStrProperty(String strProperty) {
      this.strProperty = strProperty;
    }
  }
  
  @Test
  public void get_set_str() throws Exception {
    
    Asd asd = new Asd();
    
    PropManager pm = new PropManager(asd);
    
    String s1 = "fdsaffds786fa78ds56f876as5df85a";
    String s2 = "o4m3on54n657hv8fg78cx99f80d76f6";
    
    pm.set("strField", s1);
    pm.set("strProperty", s2);
    
    assertThat(pm.get("strField")).isEqualTo(s1);
    assertThat(pm.get("strProperty")).isEqualTo(s2);
  }
}
