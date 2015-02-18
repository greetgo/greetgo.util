package kz.greetgo.watcher.tracer;

import java.util.List;

import kz.greetgo.watcher.concurrent.Batcher;

import org.testng.annotations.Test;

public class TracerTest {
  @Test
  public void test() {
    Tracer tracer = new Tracer() {
      private final Batcher<TraceEvent> batcher = new Batcher<TraceEvent>(100) {
        protected void batch(List<TraceEvent> list) {
          for (TraceEvent ev : list) {
            String run = Long.toHexString(ev.run.getLeastSignificantBits());
            System.out.printf("%s-%06d- %s\n", run, ev.number, ev.message);
          }
        }
      };
      
      @Override
      protected String user() {
        return null;
      }
      
      @Override
      protected Batcher<TraceEvent> batcher() {
        return batcher;
      }
    };
    
    Trace trace1 = tracer.trace("clientImportRegister");
    trace1.info("Ok");
    trace1.error("Ohh");
    
    tracer.reset();
    
    Trace trace2 = tracer.trace("noificationService");
    trace2.info("dfds");
    trace2.error("dgf");
    
  }
}
