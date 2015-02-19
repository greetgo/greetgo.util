package kz.greetgo.watcher.concurrent;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

public class WriterBatcher<T> extends Batcher<T> {
  private final PrintWriter writer;
  
  public WriterBatcher(long timeout, Writer rw) {
    super(timeout);
    writer = new PrintWriter(rw);
  }
  
  protected void unpack(PrintWriter writer, T t) {
    writer.println(t.toString());
  }
  
  @Override
  protected final void batch(List<T> list) {
    for (T t : list) {
      unpack(writer, t);
    }
  }
}
