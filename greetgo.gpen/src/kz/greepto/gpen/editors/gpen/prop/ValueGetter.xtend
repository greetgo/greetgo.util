package kz.greepto.gpen.editors.gpen.prop

/**
 * Получатель значений свойства
 */
interface ValueGetter {

  /**
   * Получает значение свойства
   *
   * @param object объект у которого получается свойство
   *
   * @return полученное значение
   */
  def Object getValue(Object object)

  /**
   * Получает тип значения свойства
   *
   * @return класс - тип свойства
   */
  def Class<?> getType()

  /**
   * Получает наименование свойства
   *
   * @return наименование свойства
   */
  def String getName()
}
