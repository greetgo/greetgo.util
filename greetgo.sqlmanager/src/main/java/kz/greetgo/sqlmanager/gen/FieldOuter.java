package kz.greetgo.sqlmanager.gen;

import kz.greetgo.sqlmanager.model.JavaType;

public class FieldOuter {
  public final JavaType type;
  public final String name;
  
  public FieldOuter(JavaType javaType, String name) {
    this.type = javaType;
    this.name = name;
  }
}
