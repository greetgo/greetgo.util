package kz.greetgo.watcher.tracer;

import kz.greetgo.watcher.concurrent.Batcher;
import kz.greetgo.watcher.concurrent.WriterBatcher;

public class ExampleFileTracer extends ProjectTracer<ExampleEvent> {
  private static final Batcher<ExampleEvent> BATCHER = new WriterBatcher<ExampleEvent>(100, defaultWriter("example"));
  
  @Override
  protected Batcher<ExampleEvent> batcher() {
    return BATCHER;
  }
  
  @Override
  protected ExampleEvent pack(String message) {
    return new ExampleEvent(message);
  }
}
