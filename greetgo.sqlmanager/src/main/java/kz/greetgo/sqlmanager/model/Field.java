package kz.greetgo.sqlmanager.model;

import java.util.ArrayList;
import java.util.List;

public class Field {
  public final Table table;
  public final String name;
  public final Type type;
  
  public Field(Table table, String name, Type type) {
    this.table = table;
    this.name = name;
    this.type = type;
  }
  
  @Override
  public String toString() {
    return name + (type == null ? "" :" " + type.name);
  }
  
  public List<FieldInfo> fieldInfo() {
    List<FieldInfo> ret = new ArrayList<>();
    
    List<SimpleType> stypes = new ArrayList<>();
    List<JavaType> jtypes = new ArrayList<>();
    type.assignSimpleTypes(stypes);
    type.assignJavaTypes(jtypes);
    for (int i = 0, C = stypes.size(); i < C; i++) {
      ret.add(new FieldInfo(stypes.get(i), jtypes.get(i), name + (C == 1 ? "" :"" + (i + 1))));
    }
    
    return ret;
  }
}
