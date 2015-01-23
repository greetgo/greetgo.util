package kz.greepto.gpen.editors.gpen.prop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Определяет вес в ряду присвоений свойств
 * 
 * @author pompei
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SetOrderWeight {
  /**
   * Возвращает вес. Вес тех, у кого нет этого элемента, равен 1000. Если вес больше, что элемент
   * помещается дальше в ряду присвоений
   * 
   * @return вес элемента
   */
  int value();
}