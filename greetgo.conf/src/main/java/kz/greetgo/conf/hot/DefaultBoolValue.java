package kz.greetgo.conf.hot;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Предоставляет значение параметра по умолчанию
 * 
 * @author pompei
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultBoolValue {
  /**
   * Предоставляет значение параметра по умолчанию
   * 
   * @return значение параметра по умолчанию
   */
  boolean value();
}
