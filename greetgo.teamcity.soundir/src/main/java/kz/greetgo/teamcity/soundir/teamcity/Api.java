package kz.greetgo.teamcity.soundir.teamcity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;

import kz.greetgo.teamcity.soundir.teamcity.rhs.ResultHandler;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class Api {
  public static void prepareBasicAuthenticator(final String login, final String password) {
    Authenticator.setDefault(new Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(login, password.toCharArray());
      }
    });
  }
  
  public static String protocol;
  public static String host;
  public static int port;
  
  public static String ref(String ref) {
    try {
      return ref0(ref);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  public static <T> T ref(String ref, ResultHandler<T> rh) {
    try {
      return ref0(ref, rh);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  public static <T> T get(ResultHandler<T> rh) {
    return ref(rh.ref(), rh);
  }
  
  public static <T> T ref0(String ref, ResultHandler<T> rh) throws Exception {
    XMLReader reader = XMLReaderFactory.createXMLReader();
    reader.setContentHandler(rh);
    InputStream in = new URL(protocol, host, port, ref).openStream();
    try {
      reader.parse(new InputSource(in));
      return rh.result();
    } finally {
      in.close();
    }
  }
  
  public static String ref0(String ref) throws IOException {
    
    InputStream in = new URL(protocol, host, port, ref).openStream();
    try {
      
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      
      {
        byte[] buf = new byte[1024 * 8];
        while (true) {
          int count = in.read(buf);
          if (count < 0) break;
          bout.write(buf, 0, count);
        }
      }
      
      return new String(bout.toByteArray(), "UTF-8");
    } finally {
      in.close();
    }
  }
}
