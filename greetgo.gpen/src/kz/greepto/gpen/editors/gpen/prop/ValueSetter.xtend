package kz.greepto.gpen.editors.gpen.prop

/**
 * Устанавливатель значения свойства
 */
interface ValueSetter {

  /**
   * Устанавливает значение свойства
   * @param object объект, которому устанавливается свойство
   * @param value устанавливаемое значение
   * @return старое значение свойства
   */
  def Object setValue(Object object, Object value)

  /**
   * Получает тип свойства
   */
  def Class<?> getType()

  /**
   * Получает наименование свойства
   */
  def String getName()
}
