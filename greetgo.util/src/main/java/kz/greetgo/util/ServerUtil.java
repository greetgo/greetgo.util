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

  /**
   * Вернуть первый элемент которы не равен <code>null</code>
   * 
   * @param tt
   *          список элементов
   * @return первый not-null-елемент
   */
  @SafeVarargs
  public static <T> T fnn(T... tt) {
    for (T t : tt) {
      if (t != null) return t;
    }
    return null;
  }

  /**
   * Ни чего не делающая проверка
   */
  public static void dummyCheck(boolean tmp) {}

  /**
   * Проверяет чтобы элемент не был null, иначе генерирует NullPointerException
   * 
   * @param t
   *          проверяемый элемент
   * @return проверяемый элемент, который точно not null
   */
  public static <T> T notNull(T t) {
    if (t == null) throw new NullPointerException();
    return t;
  }

  /**
   * Считать поток в кодировке UTF-8 и добавить полученнцю строку в <code>sb</code>
   * 
   * @param in
   *          считываемый поток
   * @param sb
   *          назначение для добавления строки
   */
  public static void appendToSB(InputStream in, StringBuilder sb) {
    try {
      appendToSB0(in, sb);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * То же что и {@link #appendToSB(InputStream, StringBuilder)}, но содержит
   * <code>throws IOException</code>
   * 
   * @param in
   *          считываемый поток
   * @param sb
   *          назначение для добавления строки
   * @throws IOException
   *           генерируется в случае ошибки ввода/вывода
   */
  public static void appendToSB0(InputStream in, StringBuilder sb) throws IOException {
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    copyStreamsAndCloseIn(in, bout);
    sb.append(new String(bout.toByteArray(), "UTF-8"));
  }

  /**
   * Считывает поток до конца в массив байтов, закрывает поток, и возвращает, что считал
   * 
   * @param in
   *          считываемый поток
   * @return массив считанных байт
   */
  public static byte[] streamToByteArray(InputStream in) {
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    copyStreamsAndCloseIn(in, bout);
    return bout.toByteArray();
  }

  /**
   * Считывает и закрывает поток, и считанную информацию превращает в строку, предполагая что она
   * предствлена в кодировке UTF-8
   * 
   * @param in
   *          считываемый поток
   * @return полученная строка
   */
  public static String streamToStr(InputStream in) {
    StringBuilder sb = new StringBuilder();
    appendToSB(in, sb);
    return sb.toString();
  }

  /**
   * То же что и {@link #streamToStr(InputStream)} но содержит <code>throws Exception</code>
   * 
   * @param in
   *          считываемый поток
   * @return полученная строка
   * @throws Exception
   *           сюда пробрасываются все ошибки
   */
  public static String streamToStr0(InputStream in) throws Exception {
    StringBuilder sb = new StringBuilder();
    appendToSB0(in, sb);
    return sb.toString();
  }

  /**
   * Копирует один поток в другой, и после копирования закрывает входящий. Буфер копирования равен 4
   * кБ
   * 
   * @param in
   *          считываемый поток, который закрывается после считывания
   * @param out
   *          выходящий поток
   * @return выходящий поток
   */
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

  /**
   * Проверяет аргумент на равенство единице. Если проверка проваливается - генерирует исключение
   * 
   * @param value
   *          проверяемый на единицу аргумент
   */
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

  /**
   * Удаляет файл или папку со всем её содержимым
   * 
   * @param file
   *          удаляемый файл или папка
   */
  public static void deleteRecursively(File file) {
    if (!file.exists()) return;
    if (file.isDirectory()) for (File sub : file.listFiles()) {
      deleteRecursively(sub);
    }
    file.delete();
  }

  /**
   * Удаляет файл или папку со всем её содержимым
   * 
   * @param fileFullName
   *          путь к файлу или папке
   */
  public static void deleteRecursively(String fileFullName) {
    deleteRecursively(new File(fileFullName));
  }

  /**
   * Производит java-сериализацию объекта и возвращает получиенные сериализацией данные
   * 
   * @param object
   *          сериализуемый объект
   * @return сериализованные данные
   */
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

  /**
   * Производит java-десериализацию и возвращает полученный объект
   * 
   * @param bytes
   *          ранее сериализованные данные
   * @return десериализованный объект
   */
  @SuppressWarnings("unchecked")
  public static <T> T javaDeserialize(byte[] bytes) {
    ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
    try (ObjectInputStream oin = new ObjectInputStream(bin)) {
      return (T)oin.readObject();
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Обрезает строку с обоих сторон, т.е. удаляет пробельные символы в начале и в конце строки и
   * возвращает полученный результат. При <code>null</code> ошибок не возникает - возвращается
   * <code>null</code>
   * 
   * @param str
   *          обрезаемая строка
   * @return обрезанная строка
   */
  public static String trim(String str) {
    if (str == null) return null;
    return str.trim();
  }

  /**
   * Обрезает строку слева, т.е. удаляет из строки пробельные символы вначале строки. При
   * <code>null</code> ошибок не возникает - возвращается <code>null</code>
   * 
   * @param str
   *          обрезаемая строка
   * @return обрезанная строка
   */
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

  /**
   * Обрезает строку справа, т.е. удаляет из строки пробельные символы вконце строки. При
   * <code>null</code> ошибок не возникает - возвращается <code>null</code>
   * 
   * @param str
   *          обрезаемая строка
   * @return обрезанная строка
   */
  public static String trimRight(String str) {
    if (str == null) return str;
    int i = str.length() - 1;
    if (str.charAt(i) > ' ') return str;
    for (; i >= 0; i--) {
      if (str.charAt(i) > ' ') return str.substring(0, i + 1);
    }
    return "";
  }

  /**
   * Возвращает аннотацию метода, проверяя наличие этой аннотации у всех наследуемых методов, если
   * они есть
   * 
   * @param method
   *          исходный метод
   * @param annotation
   *          класс необходимой аннотации
   * @return значение необходиой аннотации, или <code>null</code> если такая аннотация не найдена
   */
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

  private final static char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

  /**
   * Преобразует массив байтов в их шеснадцатиричное представление
   * 
   * @param bytes
   *          исходный массив байтов или <code>null</code>
   * @return шеснадцатиричное представление байтов в массиве или пустая строка, если передали
   *         <code>null</code> вместо массива байтов
   */
  public static String bytesToHex(byte[] bytes) {
    if (bytes == null) return "";
    char[] hexChars = new char[bytes.length * 2];
    for (int j = 0; j < bytes.length; j++) {
      int v = bytes[j] & 0xFF;
      hexChars[j * 2] = HEX_ARRAY[v >>> 4];
      hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
    }
    return new String(hexChars);
  }
}
