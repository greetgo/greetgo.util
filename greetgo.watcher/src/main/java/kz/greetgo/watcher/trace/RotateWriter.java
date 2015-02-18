package kz.greetgo.watcher.trace;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class RotateWriter extends Writer {
  private final long maxFileSize;
  private final LazyOutputStream out;
  private Writer writer;
  
  private final void tryReset() throws IOException {
    writer.flush();
    if (out.getCount() > maxFileSize) {
      out.reset();
    }
  }
  
  public RotateWriter(long maxFileSize, final Iterable<File> files)
      throws UnsupportedEncodingException {
    this.maxFileSize = maxFileSize;
    out = new LazyOutputStream() {
      protected OutputStream newOut() throws IOException {
        FileSequence.rotate(files);
        return new FileOutputStream(files.iterator().next());
      }
    };
    writer = new OutputStreamWriter(out, "UTF-8");
  }
  
  private static final int indexOfNewline(char[] cbuf, int off, int to) {
    for (int i = off; i < to; i++) {
      if (cbuf[i] == '\n') return i;
    }
    return -1;
  }
  
  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    int to = off + len;
    while (true) {
      int nl = indexOfNewline(cbuf, off, to);
      if (nl < 0) {
        writer.write(cbuf, off, to - off);
        break;
      }
      writer.write(cbuf, off, nl + 1 - off);
      tryReset();
      off = nl + 1;
    }
  }
  
  @Override
  public void flush() throws IOException {
    out.flush();
  }
  
  @Override
  public void close() throws IOException {
    out.close();
  }
  
}
