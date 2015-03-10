package kz.greetgo.watcher.tracer;

import org.testng.annotations.Test;

public class TracerTest {
  @Test
  public void exampleFileTracer() {
    ExampleFileTracer t = new ExampleFileTracer();
    
    t.trace("Ok");
    t.trace("Ohh");
    
    ProjectTracer.STAMP.reset();
    
    t.trace("dfds");
    t.trace("dgf");
  }
  
  @Test
  public void exampleDataSourceTracer() throws InterruptedException {
    ExampleDataSourceTracer t = new ExampleDataSourceTracer();
    
    t.trace("Ok");
    t.trace("Ohh");
    
    ProjectTracer.STAMP.reset();
    
    t.trace("dfds");
    t.trace("dgf");
    Thread.sleep(1000);
  }
}
