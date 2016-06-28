package kz.greetgo.util;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Предоставляет возможность генерирования большой строки с возможностью использования подставляемых
 * параметров. Подставляемые параметры определяются внутри строки в фигурных скобках, например:
 * {ИМЯ_ПАРАМЕТРА}.
 * </p>
 * 
 * <p>
 * Параметры подставляются их значениями в момент чтения результирующей строки. Чтение
 * результирующей строки происходит вызовом метода {@link #toString()}.
 * </p>
 * 
 * <p>
 * Значения параметров определяются методом {@link #prm(String, String)}.
 * </p>
 * 
 * @author pompei
 */
public class StrReplacer {
  private final StringBuilder sb;

  /**
   * Constructs a string replacer with no characters in it and an initial capacity of 16 characters.
   */
  public StrReplacer() {
    sb = new StringBuilder();
  }

  /**
   * Constructs a string replacer with no characters in it and an initial capacity specified by the
   * <code>capacity</code> argument.
   *
   * @param capacity
   *          the initial capacity.
   * @throws NegativeArraySizeException
   *           if the <code>capacity</code> argument is less than <code>0</code>.
   */
  public StrReplacer(int capacity) {
    sb = new StringBuilder(capacity);
  }

  private StrReplacer(CharSequence charSequence) {
    sb = new StringBuilder(charSequence);
  }

  /**
   * Wraps a string replacer that contains the same characters as the specified
   * <code>CharSequence</code>. The initial capacity of the string builder is <code>16</code> plus
   * the length of the <code>CharSequence</code> argument.
   *
   * @param charSequence
   *          the sequence to copy.
   * @throws NullPointerException
   *           if <code>charSequence</code> is <code>null</code>
   */
  public static StrReplacer wrap(CharSequence charSequence) {
    return new StrReplacer(charSequence);
  }

  private final Map<String, String> paramMap = new HashMap<String, String>();

  /**
   * Добавляет стороннюю строку целиком
   * 
   * @param s
   *          добавляемая сторонняя строка
   * @return <code>this</code>
   */
  public StrReplacer add(CharSequence s) {
    sb.append(s);
    return this;
  }

  /**
   * Добавляет часть сторонней строки
   * 
   * @param s
   *          сторонняя строка
   * @param start
   *          начало добавляемой части в сторонней строке
   * @param end
   *          конец добавляемой части в сторонней строке
   * @return <code>this</code>
   */
  public StrReplacer add(CharSequence s, int start, int end) {
    sb.append(s, start, end);
    return this;
  }

  /**
   * Добавляет целое число, преобразуя его в строку таким же способом, как это делает метод
   * {@link String#valueOf(int)}
   * 
   * @param i
   *          добавляемое число
   * @return <code>this</code>
   */
  public StrReplacer add(int i) {
    sb.append(i);
    return this;
  }

  /**
   * Appends the string representation of a subarray of the {@code char} array argument to this
   * sequence.
   * <p>
   * Characters of the {@code char} array {@code str}, starting at index {@code offset}, are
   * appended, in order, to the contents of this sequence. The length of this sequence increases by
   * the value of {@code len}.
   * <p>
   * The overall effect is exactly as if the arguments were converted to a string by the method
   * {@link String#valueOf(char[],int,int)}, and the characters of that string were then
   * {@link #append(String) appended} to this character sequence.
   *
   * @param str
   *          the characters to be appended.
   * @param offset
   *          the index of the first {@code char} to append.
   * @param len
   *          the number of {@code char}s to append.
   * @return a reference to this object.
   * @throws IndexOutOfBoundsException
   *           if {@code offset < 0} or {@code len < 0} or {@code offset+len > str.length}
   */
  public StrReplacer add(char[] str, int offset, int len) {
    sb.append(str, offset, len);
    return this;
  }

  /**
   * Тоже что и {@link #add(char[], int, int)}, но берёт массив целиком
   * 
   * @param str
   *          добавляемы целиком массив
   * @return <code>this</code>
   */
  public StrReplacer add(char[] str) {
    sb.append(str);
    return this;
  }

  /**
   * Добавляет целое число, преобразуя его в строку таким же способом, как это делает метод
   * {@link String#valueOf(int)}
   * 
   * @param i
   *          добавляемое число
   * @return <code>this</code>
   */
  public StrReplacer add(Integer i) {
    sb.append(i);
    return this;
  }

  /**
   * Добавляет целое число, преобразуя его в строку таким же способом, как это делает метод
   * {@link String#valueOf(long)}
   * 
   * @param i
   *          добавляемое число
   * @return <code>this</code>
   */
  public StrReplacer add(long l) {
    sb.append(l);
    return this;
  }

  /**
   * Добавляет целое число, преобразуя его в строку таким же способом, как это делает метод
   * {@link String#valueOf(long)}
   * 
   * @param i
   *          добавляемое число
   * @return <code>this</code>
   */
  public StrReplacer add(Long l) {
    sb.append(l);
    return this;
  }

  public StrReplacer add(StringBuilder sb) {
    sb.append(sb);
    return this;
  }

  public StrReplacer add(StrReplacer sr) {
    sb.append(sr.sb);
    return this;
  }

  /**
   * Устанавливает значение параметра
   * 
   * @param key
   *          имя параметра
   * @param value
   *          значение параметра
   * @return <code>this</code>
   */
  public StrReplacer prm(String key, String value) {
    paramMap.put(key, value);
    return this;
  }

  /**
   * Генерирует результирующий {@link StringBuilder}, где вместо параметров, стоят их значения
   */
  public StringBuilder generateResultStringBuilder() {
    StringBuilder ret = new StringBuilder(sb.length());
    appendResultTo(ret);
    return ret;
  }

  /**
   * Добавляет результирующий текст в указанный {@link StringBuilder}
   * 
   * @param target
   *          место, куда добавляется результат
   */
  public void appendResultTo(StringBuilder target) {
    String s = sb.toString();
    while (true) {
      int i1 = s.indexOf('{');
      if (i1 < 0) break;
      int i2 = s.indexOf('}', i1);
      if (i2 < 0) break;
      String key = s.substring(i1 + 1, i2);
      String value = paramMap.get(key);
      if (value == null) {
        target.append(s, 0, i2);
        s = s.substring(i2);
      } else {
        target.append(s, 0, i1).append(value);
        s = s.substring(i2 + 1);
      }
    }
    if (s.length() > 0) target.append(s);
  }

  /**
   * Формирует результирующую строку, где вместо параметров, стоят их значения
   */
  @Override
  public String toString() {
    return generateResultStringBuilder().toString();
  }

  /**
   * Получает длинну (количество символов) текущего сформированного текста (без подстановки значений
   * параметров)
   * 
   * @return длинна текущего текста
   */
  public int getLen() {
    return sb.length();
  }

  /**
   * Sets the length of the character sequence. The sequence is changed to a new character sequence
   * whose length is specified by the argument. For every nonnegative index <i>k</i> less than
   * <code>newLength</code>, the character at index <i>k</i> in the new character sequence is the
   * same as the character at index <i>k</i> in the old sequence if <i>k</i> is less than the length
   * of the old character sequence; otherwise, it is the null character <code>'&#92;u0000'</code>.
   *
   * In other words, if the <code>newLength</code> argument is less than the current length, the
   * length is changed to the specified length.
   * <p>
   * If the <code>newLength</code> argument is greater than or equal to the current length,
   * sufficient null characters (<code>'&#92;u0000'</code>) are appended so that length becomes the
   * <code>newLength</code> argument.
   * <p>
   * The <code>newLength</code> argument must be greater than or equal to <code>0</code>.
   *
   * @param newLength
   *          the new length
   * @throws IndexOutOfBoundsException
   *           if the <code>newLength</code> argument is negative.
   */
  public void setLen(int newLen) {
    sb.setLength(newLen);
  }

  /**
   * Уменьшает длинну текста на указанное количество символов. Если результирующий размер получается
   * меньше нуля, то никакой ошибки не возникет, просто весь тест очищается
   * 
   * @param size
   *          размер, на который уменьшается текст
   */
  public void decLen(int size) {
    int newLen = sb.length() - size;
    if (newLen < 0) {
      sb.setLength(0);
    } else {
      sb.setLength(newLen);
    }
  }
}
