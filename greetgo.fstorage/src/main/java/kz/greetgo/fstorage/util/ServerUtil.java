package kz.greetgo.fstorage.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ServerUtil {
  public static void appendToSB(InputStream in, StringBuilder sb) {
    try {
      appendToSB0(in, sb);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  public static void appendToSB0(InputStream in, StringBuilder sb) throws IOException {
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    copyStreamsAndCloseIn(in, bout);
    sb.append(new String(bout.toByteArray(), "UTF-8"));
  }
  
  public static String streamToStr(InputStream in) {
    StringBuilder sb = new StringBuilder();
    appendToSB(in, sb);
    return sb.toString();
  }
  
  public static String streamToStr0(InputStream in) throws Exception {
    StringBuilder sb = new StringBuilder();
    appendToSB0(in, sb);
    return sb.toString();
  }
  
  public static OutputStream copyStreamsAndCloseIn(InputStream in, OutputStream out) {
    try {
      byte buffer[] = new byte[4096];
      
      while (true) {
        int read = in.read(buffer);
        if (read < 0) break;
        out.write(buffer, 0, read);
      }
      
      in.close();
      return out;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  public static String getVersion() {
    Package p = ServerUtil.class.getPackage();
    return p.getImplementationVersion();
  }
  
  public static String getVendor() {
    Package p = ServerUtil.class.getPackage();
    return p.getImplementationVendor();
  }
  
  public static void justOne(int value) {
    if (value == 1) return;
    throw new IllegalArgumentException("Update count = " + value);
  }
}
