package kz.greetgo.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ServerUtil {
  /**
   * Raise the first letter in upper case
   *
   * @param str source line
   * @return string with a capital first letter
   */
  public static String firstUpper(String str) {
    if (str == null) {
      return null;
    }
    str = str.trim();
    if (str.length() == 0) {
      return null;
    }

    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }

  /**
   * Return the first element that is not equal to <code>null</code>
   *
   * @param tt element list
   * @return the first not-null-element
   */
  @SafeVarargs
  public static <T> T fnn(T... tt) {
    for (T t : tt) {
      if (t != null) {
        return t;
      }
    }
    return null;
  }

  /**
   * The check which does not perform anything
   */
  public static void dummyCheck(@SuppressWarnings("unused") boolean tmp) {}

  /**
   * Checks that the element was not null, otherwise generates NullPointerException
   *
   * @param t coverage element
   * @return coverage element, that exactly is not null
   */
  public static <T> T notNull(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    return t;
  }

  /**
   * Read stream encoded as UTF-8 and add to the resulting string <code>sb</code>
   *
   * @param in readable stream
   * @param sb dedication to add a string
   */
  public static void appendToSB(InputStream in, StringBuilder sb) {
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    copyStreamsAndCloseIn(in, bout);
    sb.append(new String(bout.toByteArray(), UTF_8));
  }

  /**
   * Reads a stream to the end to bytes array, closes a stream, and returns that was read
   *
   * @param in readable stream
   * @return the array of read bytes
   */
  public static byte[] streamToByteArray(InputStream in) {
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    copyStreamsAndCloseIn(in, bout);
    return bout.toByteArray();
  }

  /**
   * Reads and closes the stream, and converts the read information into a string, assuming that it
   * represented in coding UTF-8
   *
   * @param in readable stream
   * @return resulting string
   */
  public static String streamToStr(InputStream in) {
    StringBuilder sb = new StringBuilder();
    appendToSB(in, sb);
    return sb.toString();
  }

  /**
   * Reads file to string as UTF-8 text
   *
   * @param file reading file
   * @return read text from file
   */
  @SuppressWarnings("unused")
  public static String readFile(File file) {
    try {
      return streamToStr(new FileInputStream(file));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Copies one stream into another, and after copy closes input stream. Copy buffer size is equal
   * to 4 Kb
   *
   * @param in  readable stream, which is closed after reading
   * @param out output stream
   * @return output stream
   */
  public static OutputStream copyStreamsAndCloseIn(InputStream in, OutputStream out) {
    try {
      byte[] buffer = new byte[4096];

      while (true) {
        int read = in.read(buffer);
        if (read < 0) {
          break;
        }
        out.write(buffer, 0, read);
      }

      return out;
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      try {
        in.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Check the argument for equality to one. If the check is failed - generates exception
   *
   * @param value argument checking for one
   */
  public static void justOne(int value) {
    if (value == 1) {
      return;
    }
    throw new IllegalArgumentException("Update count = " + value);
  }

  /**
   * Extract the package name from the full name of the class
   *
   * @param className full name of the class
   * @return package name
   */
  public static String extractPackage(String className) {
    final int idx = className.lastIndexOf('.');
    if (idx < 0) {
      return null;
    }
    return className.substring(0, idx);
  }

  /**
   * Forms the source file (but does not create it) for a specified source-folder and with name of
   * the class. Also the extension can be specified. By default, the extension is <code>.java</code>
   *
   * @param srcDir    the directory where the source codes are
   * @param className full name of the class
   * @param extension extension (if <code>null</code>, <code>.java</code> is used)
   * @return formed file
   */
  public static File resolveFile(String srcDir, String className, String extension) {
    return new File(
      srcDir + '/' + className.replace('.', '/') + (extension == null ? ".java" : extension));
  }

  /**
   * Extracts class name from the full name of the class
   *
   * @param className full name of the class
   * @return class name without the package
   */
  public static String extractName(String className) {
    final int idx = className.lastIndexOf('.');
    if (idx < 0) {
      return className;
    }
    return className.substring(idx + 1);
  }

  /**
   * Deletes a file or folder with all its contents
   *
   * @param file deletable file or folder
   */
  public static void deleteRecursively(File file) {
    if (!file.exists()) {
      return;
    }
    if (file.isDirectory()) {
      File[] subFiles = file.listFiles();
      if (subFiles != null) {
        for (File subFile : subFiles) {
          deleteRecursively(subFile);
        }
      }
    }
    dummyCheck(file.delete());
  }

  /**
   * Deletes a file or folder with all its contents
   *
   * @param fileFullName path to the file or folder
   */
  public static void deleteRecursively(String fileFullName) {
    deleteRecursively(new File(fileFullName));
  }

  /**
   * Performs java-serialization of the object and returns the data resulting after serialization
   *
   * @param object serializable object
   * @return serializable data
   */
  public static byte[] javaSerialize(Object object) {
    if (object == null) {
      return null;
    }
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    try (ObjectOutputStream oos = new ObjectOutputStream(bout)) {
      oos.writeObject(object);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return bout.toByteArray();
  }

  /**
   * Performs java-serialization of the object and returns the resulting object
   *
   * @param bytes previously serialized data
   * @return object after deserialize
   */
  @SuppressWarnings("unchecked")
  public static <T> T javaDeserialize(byte[] bytes) {
    ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
    try (ObjectInputStream oin = new ObjectInputStream(bin)) {
      return (T) oin.readObject();
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Trims the string on both sides, i. e. deletes whitespace at the beginning and end of the string
   * and returns the result. At <code>null</code> there are no errors - <code>null</code> is
   * returned
   *
   * @param str string to trim
   * @return string after trimming
   */
  public static String trim(String str) {
    if (str == null) {
      return null;
    }
    return str.trim();
  }

  /**
   * Trims the srting on the left, i. e. deletes whitespace at the beginning of the string. At
   * <code>null</code> there are no errors - <code>null</code> is returned
   *
   * @param str string to trim
   * @return string after trimming
   */
  public static String trimLeft(String str) {
    if (str == null) {
      return null;
    }
    for (int i = 0, len = str.length(); i < len; i++) {
      if (str.charAt(i) > ' ') {
        if (i == 0) {
          return str;
        }
        return str.substring(i);
      }
    }
    return "";
  }

  /**
   * Trims the srting on the right, i. e. deletes whitespace at the end of the string. At
   * <code>null</code> there are no errors - <code>null</code> is returned
   *
   * @param str string to trim
   * @return string after trimming
   */
  public static String trimRight(String str) {
    if (str == null) {
      return null;
    }
    int i = str.length() - 1;
    if (str.charAt(i) > ' ') {
      return str;
    }
    for (; i >= 0; i--) {
      if (str.charAt(i) > ' ') {
        return str.substring(0, i + 1);
      }
    }
    return "";
  }

  /**
   * Returns the annotation of the method checking the presence of this annotation at all inherited
   * methods if they are
   *
   * @param method     source method
   * @param annotation class of required annotation
   * @return the value of required annotation, or <code>null</code> if such annotation is not found
   */
  public static <T extends Annotation> T getAnnotation(Method method, Class<T> annotation) {
    while (true) {
      T ann = method.getAnnotation(annotation);
      if (ann != null) {
        return ann;
      }

      Class<?> aClass = method.getDeclaringClass();
      if (aClass == Object.class) {
        return null;
      }

      Class<?> superclass = aClass.getSuperclass();
      try {
        method = superclass.getMethod(method.getName(), method.getParameterTypes());
      } catch (NoSuchMethodException e) {
        return null;
      }
    }
  }

  private final static char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

  /**
   * Converts an array of bytes into their hexadecimal representation
   *
   * @param bytes source array of bytes or <code>null</code>
   * @return hexadecimal representation of the bytes in the array, or an empty string, if
   * transmitted <code>null</code> instead of array of bytes
   */
  public static String bytesToHex(byte[] bytes) {
    if (bytes == null) {
      return "";
    }
    char[] hexChars = new char[bytes.length * 2];
    for (int j = 0; j < bytes.length; j++) {
      int v = bytes[j] & 0xFF;
      hexChars[j * 2] = HEX_ARRAY[v >>> 4];
      hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
    }
    return new String(hexChars);
  }
}
