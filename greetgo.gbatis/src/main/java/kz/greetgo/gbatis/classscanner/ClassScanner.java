package kz.greetgo.gbatis.classscanner;

import java.util.Set;

public interface ClassScanner {
  Set<Class<?>> scanPackage(String packageName);
}
