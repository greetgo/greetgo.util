package kz.greetgo.gbatis;

import java.util.Set;

public interface ClassScanner {
  Set<Class<?>> scanPackage(String packageName);
}
