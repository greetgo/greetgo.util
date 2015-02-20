package kz.greetgo.watcher.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class RotateWriter extends Writer {
  private final long maxLength;
  private final LazyOutputStream out;
  private Writer writer;
  
  private final void tryReset() throws IOException {
    writer.flush();
    if (out.getLength() > maxLength) {
      out.reset();
    }
  }
  
  public RotateWriter(final long maxFileLength, final Iterable<File> files)
      throws UnsupportedEncodingException {
    this.maxLength = maxFileLength;
    out = new LazyOutputStream() {
      protected void newOut() throws IOException {
        File firstFile = files.iterator().next();
        long length = 0;
        if (firstFile.exists()) {
          length = firstFile.length();
          if (length >= maxFileLength) {
            Util.rotate(files);
            length = 0;
          }
        } else {
          if (!firstFile.getParentFile().exists()) {
            firstFile.getParentFile().mkdirs();
          }
        }
        this.length = length;
        this.out = new BufferedOutputStream(new FileOutputStream(firstFile, true));
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
