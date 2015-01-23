package kz.greepto.gpen.util;

import static org.fest.assertions.api.Assertions.assertThat;

import org.testng.annotations.Test;

public class HandlerListInstrumentTest {
  
  private static class TestList extends HandlerListInstrument<String> {
    String cont() {
      StringBuilder sb = new StringBuilder(10);
      for (String str : this) {
        sb.append(str);
      }
      return sb.toString();
    }
  }
  
  @Test
  public void test() throws Exception {
    TestList test = new TestList();
    
    //System.out.println("Empty [" + test.cont() + ']');
    assertThat(test.cont()).isEmpty();
    
    HandlerKiller a = test.add("a");
    HandlerKiller b = test.add("b");
    HandlerKiller c = test.add("c");
    
    //System.out.println("abc [" + test.cont() + ']');
    assertThat(test.cont()).isEqualTo("abc");
    
    b.kill();
    b.kill();//twice must be OK (do nothing)
    
    //System.out.println("killed b [" + test.cont() + ']');
    assertThat(test.cont()).isEqualTo("ac");
    
    a.kill();
    
    //System.out.println("killed a [" + test.cont() + ']');
    assertThat(test.cont()).isEqualTo("c");
    
    test.add("D");
    
    //System.out.println("added D [" + test.cont() + ']');
    assertThat(test.cont()).isEqualTo("cD");
    
    c.kill();
    test.add("E");
    
    //System.out.println("killed c, added E [" + test.cont() + ']');
    assertThat(test.cont()).isEqualTo("DE");
  }
}
