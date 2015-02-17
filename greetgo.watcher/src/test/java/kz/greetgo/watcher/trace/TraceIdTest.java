package kz.greetgo.watcher.trace;

import static org.fest.assertions.api.Assertions.assertThat;

import org.testng.annotations.Test;

public class TraceIdTest {
  @Test
  public void toStringTest() throws Exception {
    TraceIdThreadStorage tip = new TraceIdThreadStorage();
    
    TraceId traceId = tip.traceId();
    
    String str = traceId.toString();
    
    String runStr = Long.toHexString(traceId.run.getLeastSignificantBits()).toUpperCase();
    
    assertThat(str).isEqualTo("TRACE-" + runStr + "-00000002");
  }
}
