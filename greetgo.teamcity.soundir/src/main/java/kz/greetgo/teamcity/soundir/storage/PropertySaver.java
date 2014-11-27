package kz.greetgo.teamcity.soundir.storage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PropertySaver {
  
  private final String dir;
  
  public PropertySaver(String dir) {
    this.dir = dir;
  }
  
  private File getFile(String group, String name) {
    return new File(dir + "/" + group + "/" + name + ".param");
  }
  
  public <T extends Enum<T>> void setEnum(String group, String name, Enum<T> value) {
    if (value == null) setStr(group, name, null);
    else
      setStr(group, name, value.name());
  }
  
  public void setInt(String group, String name, int value) {
    setStr(group, name, "" + value);
  }
  
  private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
  
  public void setDate(String group, String name, Date value) {
    if (value == null) {
      setStr(group, name, null);
      return;
    }
    setStr(group, name, new SimpleDateFormat(DATE_FORMAT).format(value));
  }
  
  @SuppressWarnings("unchecked")
  public <T extends Enum<T>> T getEnum(Class<T> enumClass, String group, String name) {
    String str = getStr(group, name);
    if (str == null) return null;
    
    try {
      Method method = enumClass.getMethod("valueOf", String.class);
      return (T)method.invoke(enumClass, str);
    } catch (Exception e) {
      if (e instanceof InvocationTargetException
          && e.getCause() instanceof IllegalArgumentException) {
        return null;
      }
      throw new RuntimeException(e);
    }
  }
  
  public int getInt(String group, String name) {
    try {
      return Integer.parseInt(getStr(group, name));
    } catch (NumberFormatException e) {
      return 0;
    }
  }
  
  public Date getDate(String group, String name) {
    try {
      return new SimpleDateFormat(DATE_FORMAT).parse(getStr(group, name));
    } catch (ParseException | NullPointerException e) {
      return null;
    }
  }
  
  public String getStr(String group, String name) {
    File file = getFile(group, name);
    if (!file.exists()) return null;
    try {
      ByteArrayOutputStream bout = new ByteArrayOutputStream(10);
      {
        FileInputStream fin = new FileInputStream(file);
        try {
          byte buf[] = new byte[256];
          while (true) {
            int count = fin.read(buf);
            if (count < 0) break;
            bout.write(buf, 0, count);
          }
        } finally {
          fin.close();
        }
      }
      return bout.toString("UTF-8");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  public void setStr(String group, String name, String value) {
    File file = getFile(group, name);
    if (value == null && !file.exists()) return;
    if (value == null) {
      file.delete();
      return;
    }
    file.getParentFile().mkdirs();
    try {
      PrintStream out = new PrintStream(file, "UTF-8");
      out.print(value);
      out.close();
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }
}
