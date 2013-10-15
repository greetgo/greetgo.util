package kz.greetgo.watcher.trace;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ByteCounter {
  public long counter = 0;
  
  public InputStream in(final InputStream in) {
    return new InputStream() {
      @Override
      public int read() throws IOException {
        counter++;
        return in.read();
      }
      
      @Override
      public void close() throws IOException {
        in.close();
      }
    };
  }
  
  public OutputStream out(final OutputStream out) {
    return new OutputStream() {
      @Override
      public void write(int b) throws IOException {
        counter++;
        out.write(b);
      }
      
      @Override
      public void write(byte[] b) throws IOException {
        counter += b.length;
        out.write(b);
      }
      
      @Override
      public void write(byte[] b, int off, int len) throws IOException {
        counter += len;
        out.write(b, off, len);
      }
      
      @Override
      public void close() throws IOException {
        out.close();
      }
      
      @Override
      public void flush() throws IOException {
        out.flush();
      }
    };
  }
}
