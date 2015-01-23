package kz.greepto.gpen.editors.gpen.prop;

/**
 * Получатель значений свойства
 */
@SuppressWarnings("all")
public interface ValueGetter {
  /**
   * Получает значение свойства
   * 
   * @param object объект у которого получается свойство
   * 
   * @return полученное значение
   */
  public abstract Object getValue(final Object object);
  
  /**
   * Получает тип значения свойства
   * 
   * @return класс - тип свойства
   */
  public abstract Class<?> getType();
  
  /**
   * Получает наименование свойства
   * 
   * @return наименование свойства
   */
  public abstract String getName();
}
