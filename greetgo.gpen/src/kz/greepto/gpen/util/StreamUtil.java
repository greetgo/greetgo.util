package kz.greepto.gpen.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtil {
  public static int copyStreams(InputStream in, OutputStream out) {
    try {
      return copyStreamsEx(in, out, 1024 * 4);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  public static int copyStreamsEx(InputStream in, OutputStream out, int bufSize) throws IOException {
    int ret = 0;
    byte buf[] = new byte[bufSize];
    while (true) {
      int count = in.read(buf);
      if (count < 0) {
        in.close();
        return ret;
      }
      ret += count;
      out.write(buf, 0, count);
    }
  }
}
