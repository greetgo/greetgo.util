package kz.greetgo.sqlmanager.model;

public class FieldInfo {
  public final SimpleType stype;
  public final JavaType javaType;
  public final String name;
  
  public FieldInfo(SimpleType stype, JavaType javaType, String name) {
    this.stype = stype;
    this.javaType = javaType;
    this.name = name;
  }
}
