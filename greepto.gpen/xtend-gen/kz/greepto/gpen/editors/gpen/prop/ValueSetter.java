package kz.greepto.gpen.editors.gpen.prop;

/**
 * Устанавливатель значения свойства
 */
@SuppressWarnings("all")
public interface ValueSetter {
  /**
   * Устанавливает значение свойства
   * @param object объект, которому устанавливается свойство
   * @param value устанавливаемое значение
   * @return старое значение свойства
   */
  public abstract Object setValue(final Object object, final Object value);
  
  /**
   * Получает тип свойства
   */
  public abstract Class<?> getType();
  
  /**
   * Получает наименование свойства
   */
  public abstract String getName();
}
