package kz.greepto.gpen.util;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.List;

import org.testng.annotations.Test;

public class UtilParamTest {
  @Test
  public void parsParam_001() throws Exception {
    List<String> list = UtilParam.parsParam("LEFT'ONE\\'TWO\"THREE\"FORE'RIGHT1 RIGHT2");
    
    for (String param : list) {
      System.out.println(param);
    }
    
    assertThat(list).hasSize(4);
    
    assertThat(list.get(0)).isEqualTo("LEFT");
    assertThat(list.get(1)).isEqualTo("ONE'TWO\"THREE\"FORE");
    assertThat(list.get(2)).isEqualTo("RIGHT1");
    assertThat(list.get(3)).isEqualTo("RIGHT2");
  }
  
  @Test
  public void parsParam_002() throws Exception {
    List<String> list = UtilParam.parsParam("LEFT\"ONE\\\"TWO'THREE'FORE\\\\X\"RIGHT1 RIGHT2");
    
    for (String param : list) {
      System.out.println(param);
    }
    
    assertThat(list).hasSize(4);
    
    assertThat(list.get(0)).isEqualTo("LEFT");
    assertThat(list.get(1)).isEqualTo("ONE\"TWO'THREE'FORE\\X");
    assertThat(list.get(2)).isEqualTo("RIGHT1");
    assertThat(list.get(3)).isEqualTo("RIGHT2");
  }
}
