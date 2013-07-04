package kz.greetgo.conf.hot;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ METHOD, TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Description {
  String value();
}
