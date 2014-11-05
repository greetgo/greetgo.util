package kz.greetgo.gbatis.model;

import kz.greetgo.gbatis.futurecall.SqlViewer;

public class Result {
  
  /**
   * Тип возвращаемого результата
   */
  public ResultType type;
  
  /**
   * Java-класс, в который нужно засунуть результат
   */
  public Class<?> resultDataClass;
  
  /**
   * Имя поля для использования ключа мапы.
   * <p>
   * Используется если <nobr><code>{@link #type} == MAP</code></nobr>
   * </p>
   */
  public String mapKeyField;
  
  /**
   * Класс ключа мапы
   * <p>
   * Используется если <nobr><code>{@link #type} == MAP</code></nobr>
   * </p>
   */
  public Class<?> mapKeyClass;
  
  /**
   * Место вывода sql-ей для трэйсинга (может быть null)
   */
  public SqlViewer sqlViewer = null;
}
