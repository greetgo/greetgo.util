package kz.greetgo.sqlmanager.gen;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kz.greetgo.sqlmanager.model.JavaType;

public class ClassOuter {
  public final String packageName;
  public final String className;
  
  public final Map<String, String> imports = new HashMap<>();
  
  public final List<StringBuilder> content = new ArrayList<>();
  public final List<FieldOuter> fields = new ArrayList<>();
  
  private StringBuilder line = null;
  
  public void print(String part) {
    boolean first = true;
    for (String aline : part.split("\n")) {
      line = first ? line :null;
      put(aline);
    }
  }
  
  private void put(String part) {
    if (line == null) {
      line = new StringBuilder();
      content.add(line);
    }
    line.append(part);
  }
  
  public void println(String part) {
    print(part);
    println();
  }
  
  public void println() {
    if (line != null) {
      line = null;
    } else {
      content.add(new StringBuilder());
    }
  }
  
  public ClassOuter(String packageName, String pre, String className) {
    this.packageName = packageName;
    this.className = pre + AllUtil.firstUpper(className);
  }
  
  public FieldOuter addField(JavaType javaType, String name) {
    FieldOuter ret = new FieldOuter(javaType, name);
    fields.add(ret);
    return ret;
  }
  
  public String _(String fullname) {
    int idx = fullname.lastIndexOf('.');
    if (idx < 0) return fullname;
    
    String name = fullname.substring(idx + 1);
    
    String imFullname = imports.get(name);
    
    if (imFullname == null) {
      imports.put(name, fullname);
      return name;
    }
    
    if (imFullname.equals(fullname)) return name;
    
    return fullname;
  }
  
  public void generateTo(String toDir) {
    File file = new File(toDir + "/" + packageName.replaceAll("\\.", "/") + "/" + className
        + ".java");
    file.getParentFile().mkdirs();
    try {
      PrintStream out = new PrintStream(file, "UTF-8");
      out.println("package " + packageName + ";");
      
      for (String im : imports.values()) {
        out.println("import " + im + ";");
      }
      
      for (StringBuilder sb : content) {
        out.println(sb);
      }
      
      out.println("}");
      out.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  public String name() {
    return packageName + "." + className;
  }
}
