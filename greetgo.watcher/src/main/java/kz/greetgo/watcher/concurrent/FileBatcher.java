package kz.greetgo.watcher.concurrent;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

public class FileBatcher<T> extends Batcher<T> {
  private final PrintWriter writer;
  
  public FileBatcher(long timeout, Writer rw) {
    super(timeout);
    writer = new PrintWriter(rw);
  }
  
  @Override
  protected void batch(List<T> list) {
    for (T t : list) {
      writer.println(t.toString());
    }
  }
}
