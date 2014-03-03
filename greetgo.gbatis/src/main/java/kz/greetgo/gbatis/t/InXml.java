package kz.greetgo.gbatis.t;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Показывает, что SQL-находиться в xml-е
 * 
 * @author pompei
 */
@Documented
@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InXml {
  /**
   * Определяет метоположение xml-а. Если пустой, то используеться имя = <nobr>
   * &lt;имя класса&gt;.xml</nobr>
   * 
   * @return метоположение xml-а
   */
  String value() default "";
}
