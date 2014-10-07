package kz.greetgo.conf.hot;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Предоставляет описание элемента конфига (если стоит у метода) или самого конфига (если стоит у
 * интерфейса)
 * 
 * @author pompei
 */
@Documented
@Target({ METHOD, TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Description {
  /**
   * Предоставляет описание элемента конфига (если стоит у метода) или самого конфига (если стоит у
   * интерфейса)
   * 
   * @return описание элемента конфига (если стоит у метода) или самого конфига (если стоит у
   *         интерфейса)
   */
  String value();
}
