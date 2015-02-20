package kz.greetgo.watcher.tracer;

import kz.greetgo.watcher.concurrent.Batcher;
import kz.greetgo.watcher.concurrent.WriterBatcher;

public final class ExampleFileTracer extends ProjectTracer {
  private static final Batcher<ExampleEvent> BATCHER = new WriterBatcher<ExampleEvent>(100,
      defaultFileWriter("example"));
  
  public final void trace(String message) {
    BATCHER.add(new ExampleEvent(message));
  }
}
