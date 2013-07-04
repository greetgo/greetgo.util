package kz.greetgo.conf.hot;

public interface HotConfigModifier {
  void setResetEnabled(boolean resetEnabled);
  
  void set(Class<?> classs, String name, Object value);
}
