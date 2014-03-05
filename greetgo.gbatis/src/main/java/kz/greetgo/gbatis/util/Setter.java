package kz.greetgo.gbatis.util;

public interface Setter {
  void set(Object value);
  
  String name();
  
  Class<?> type();
}
