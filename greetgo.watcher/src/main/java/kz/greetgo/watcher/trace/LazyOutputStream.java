package kz.greetgo.watcher.trace;

import java.io.IOException;
import java.io.OutputStream;

public abstract class LazyOutputStream extends OutputStream {
  protected OutputStream out;
  protected long length;
  
  /**
   * Setup out and length
   */
  protected abstract void newOut() throws IOException;
  
  private final void ensureOut() throws IOException {
    if (out == null) {
      newOut();
    }
  }
  
  public final void reset() throws IOException {
    if (out == null) return;
    out.close();
    out = null;
  }
  
  public final long getLength() {
    return length;
  }
  
  public final void write(int b) throws IOException {
    ensureOut();
    out.write(b);
    length++;
  }
  
  public final void write(byte b[]) throws IOException {
    write(b, 0, b.length);
  }
  
  public final void write(byte[] b, int off, int len) throws IOException {
    if ((off | len | (b.length - (len + off)) | (off + len)) < 0) throw new IndexOutOfBoundsException();
    
    ensureOut();
    out.write(b, off, len);
    length += len;
  }
  
  public final void flush() throws IOException {
    // Do nothing to not flush every '\n'
  }
  
  public final void close() throws IOException {
    if (out != null) {
      try {
        out.flush();
      } catch (IOException ignored) {}
      out.close();
    }
  }
}
