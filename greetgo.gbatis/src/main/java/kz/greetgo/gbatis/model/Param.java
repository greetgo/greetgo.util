package kz.greetgo.gbatis.model;

public class Param {
  public Class<?> type;
  public String name;
  
  @Override
  public String toString() {
    return "param " + name + " " + type.getSimpleName();
  }
}
