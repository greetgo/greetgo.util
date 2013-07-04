package kz.greetgo.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ConfUtil {
  public void readFromFile(Object readTo, File file) throws Exception {
    readFromStream(readTo, new FileInputStream(file));
  }
  
  public void readFromFile(Object readTo, String fileName) throws Exception {
    readFromFile(readTo, new File(fileName));
  }
  
  public void readFromStream(Object readTo, InputStream inputStream) throws Exception {}
}
