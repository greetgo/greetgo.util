package kz.greetgo.gbatis.t;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Определяет идентификатор метода. Если он не указан, то идентификатор равен
 * имени метода
 * 
 * @author pompei
 */
@Documented
@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlId {
  /**
   * Определяет идентификатор метода. Если он не указан, то идентификатор равен
   * имени метода
   * 
   * @return идентификатор метода
   */
  String value();
}
