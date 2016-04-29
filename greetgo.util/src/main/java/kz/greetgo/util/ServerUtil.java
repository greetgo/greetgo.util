package kz.greetgo.util;

import static java.util.Collections.unmodifiableSet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ServerUtil {
  /**
   * Поднять первую букву в верхний регистр
   * 
   * @param str
   *          исходная строка
   * @return строка с заглавной первой буквой
   */
  public static String firstUpper(String str) {
    if (str == null) return null;
    str = str.trim();
    if (str.length() == 0) return null;
    
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }
  
  @SafeVarargs
  public static <T> T fnn(T... tt) {
    for (T t : tt) {
      if (t != null) return t;
    }
    return null;
  }
  
  public static void dummyCheck(boolean tmp) {}
  
  public static <T> T notNull(T t) {
    if (t == null) throw new NullPointerException();
    return t;
  }
  
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
  
  public static byte[] streamToByteArray(InputStream in) {
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    copyStreamsAndCloseIn(in, bout);
    return bout.toByteArray();
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
  
  private static final Class<?>[] PARAMETERS = new Class[] { URL.class };
  
  private static final ConcurrentHashMap<File, File> addedToClassPath = new ConcurrentHashMap<>();
  
  /**
   * Получает список файлов, которые были добавлены в classpath
   * 
   * @return список файлов
   */
  public static Set<File> getAddedToClassPath() {
    Set<File> ret = new HashSet<>();
    ret.addAll(addedToClassPath.keySet());
    return unmodifiableSet(ret);
  }
  
  /**
   * Добавляет указанную директорию в текущий classpath. После вызова, можно будет загружать классы
   * из этой директории с помощью Class.forName(...)
   * 
   * @param dir
   *          директория, в которой лежат откомпилированные классы, и которая не находиться ещё в
   *          текущем classpath-е
   */
  public static void addToClasspath(File dir) throws Exception {
    
    if (addedToClassPath.containsKey(dir)) return;
    addedToClassPath.put(dir, dir);
    
    URLClassLoader sysLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();
    
    Method addUrlMethod = URLClassLoader.class.getDeclaredMethod("addURL", PARAMETERS);
    addUrlMethod.setAccessible(true);
    addUrlMethod.invoke(sysLoader, new Object[] { dir.toURI().toURL() });
  }
  
  /**
   * Добавляет указанную директорию в текущий classpath. После вызова, можно будет загружать классы
   * из этой директории с помощью Class.forName(...)
   * 
   * @param dir
   *          директория, в которой лежат откомпилированные классы, и которая не находиться ещё в
   *          текущем classpath-е
   */
  public static void addToClasspath(String dirName) throws Exception {
    addToClasspath(new File(dirName));
  }
  
  /**
   * Извлекает имя пакета из поного имени класса
   * 
   * @param className
   *          полное имя класса
   * @return имя пакета
   */
  public static String extractPackage(String className) {
    final int idx = className.lastIndexOf('.');
    if (idx < 0) return null;
    return className.substring(0, idx);
  }
  
  /**
   * Формирует исходный файл (но не создаёт его) для указанной source-папки и именем класса. Также
   * можно указать расширение. По умолчанию расширение берётся <code>.java</code>
   * 
   * @param srcDir
   *          директория, в которой ложат исходники
   * @param className
   *          полное имя класса
   * @param extension
   *          расширение (если <code>null</code>, то применяется <code>.java</code>)
   * @return сформированный файл
   */
  public static File resolveFile(String srcDir, String className, String extension) {
    return new File(
        srcDir + '/' + className.replace('.', '/') + (extension == null ? ".java" :extension));
  }
  
  /**
   * Извлекает имя класса из полного имени класса
   * 
   * @param className
   *          полное имя класса
   * @return имя класса без пакета
   */
  public static String extractName(String className) {
    final int idx = className.lastIndexOf('.');
    if (idx < 0) return className;
    return className.substring(idx + 1);
  }
  
  public static void deleteRecursively(File file) {
    if (!file.exists()) return;
    if (file.isDirectory()) for (File sub : file.listFiles()) {
      deleteRecursively(sub);
    }
    file.delete();
  }
  
  public static void deleteRecursively(String fileFullName) {
    deleteRecursively(new File(fileFullName));
  }
  
  public static byte[] javaSerialize(Object object) {
    if (object == null) return null;
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    try (ObjectOutputStream oos = new ObjectOutputStream(bout)) {
      oos.writeObject(object);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return bout.toByteArray();
  }
  
  @SuppressWarnings("unchecked")
  public static <T> T javaDeserialize(byte[] bytes) {
    ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
    try (ObjectInputStream oin = new ObjectInputStream(bin)) {
      return (T)oin.readObject();
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
  
  public static String trim(String str) {
    if (str == null) return null;
    return str.trim();
  }
  
  public static String trimLeft(String str) {
    if (str == null) return null;
    for (int i = 0, len = str.length(); i < len; i++) {
      if (str.charAt(i) > ' ') {
        if (i == 0) return str;
        return str.substring(i);
      }
    }
    return "";
  }
  
  public static String trimRight(String str) {
    if (str == null) return str;
    int i = str.length() - 1;
    if (str.charAt(i) > ' ') return str;
    for (; i >= 0; i--) {
      if (str.charAt(i) > ' ') return str.substring(0, i + 1);
    }
    return "";
  }
  
  public static <T extends Annotation> T getAnnotation(Method method, Class<T> annotation) {
    while (true) {
      T ann = method.getAnnotation(annotation);
      if (ann != null) return ann;
      
      Class<?> aClass = method.getDeclaringClass();
      if (aClass == Object.class) return null;
      
      Class<?> superclass = aClass.getSuperclass();
      try {
        method = superclass.getMethod(method.getName(), method.getParameterTypes());
      } catch (NoSuchMethodException e) {
        return null;
      }
    }
  }
}
