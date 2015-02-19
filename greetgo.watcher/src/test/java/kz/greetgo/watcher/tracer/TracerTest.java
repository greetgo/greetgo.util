package kz.greetgo.watcher.tracer;

import org.testng.annotations.Test;

public class TracerTest {
  @Test
  public void test() throws InterruptedException {
    Tracer<?> t = new ExampleFileTracer();
    
    t.trace("Ok");
    t.trace("Ohh");
    
    ProjectTracer.STAMP.reset();
    
    t.trace("dfds");
    t.trace("dgf");
  }
}
