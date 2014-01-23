package kz.greetgo.sqlmanager.gen;

import static kz.greetgo.sqlmanager.gen.AllUtil.firstUpper;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kz.greetgo.sqlmanager.model.EnumType;
import kz.greetgo.sqlmanager.model.Field;
import kz.greetgo.sqlmanager.model.FieldInfo;
import kz.greetgo.sqlmanager.model.SimpleType;
import kz.greetgo.sqlmanager.model.Table;
import kz.greetgo.sqlmanager.model.Type;
import kz.greetgo.sqlmanager.model.command.Command;
import kz.greetgo.sqlmanager.model.command.SelectAll;
import kz.greetgo.sqlmanager.model.command.ToDictionary;
import kz.greetgo.sqlmanager.parser.StruGenerator;
import kz.greetgo.sqlmanager.parser.StruParseException;

public abstract class Nf6Generator {
  
  private final StruGenerator sg;
  
  protected abstract SqlDialect sqld();
  
  public String separator = ";;";
  public String tabPrefix = "m_";
  public String seqPrefix = "s_";
  public String vPrefix = "v_";
  public String ts = "ts";
  public String cre = "createdAt";
  public String bigQuote = "big_quote";
  public String _ins_ = "ins_";
  public String _value_ = "__value__";
  public String daoSuffix = "Dao";
  
  public String javaGenDir;
  public String modelPackage;
  public String javaGenStruDir;
  public String modelStruPackage;
  public String daoPackage;
  public String modelStruExtends;
  public String modelStruImplements;
  
  public Nf6Generator(StruGenerator sg) {
    this.sg = sg;
  }
  
  public void convertTo(PrintStream out, PrintStream outPrograms) {
    
    out.println("create view dual as select 'X'::varchar as dummy" + separator);
    out.println();
    
    List<String> tnames = new ArrayList<>();
    tnames.addAll(sg.stru.tables.keySet());
    Collections.sort(tnames);
    for (String name : tnames) {
      Table table = sg.stru.tables.get(name);
      printKeySql(table, out);
      for (Field field : table.fields) {
        printFieldsSql(field, out);
      }
      out.println();
    }
    
    for (String name : tnames) {
      Table table = sg.stru.tables.get(name);
      if (table.keys.size() == 1) {
        Type keyType = table.keys.get(0).type;
        if (keyType instanceof SimpleType && ((SimpleType)keyType).needSequence) {
          String from = "";
          if (table.sequenceFrom > 0) {
            from = " start with " + table.sequenceFrom;
          }
          out.println("create sequence " + seqPrefix + table.name + from + separator);
        }
      }
    }
    
    out.println();
    for (String tname : tnames) {
      Table table = sg.stru.tables.get(tname);
      printPrimaryForeignKey(table, out);
      for (Field field : table.fields) {
        if (field.type instanceof Table) {
          List<SimpleType> types = new ArrayList<>();
          field.type.assignSimpleTypes(types);
          List<String> fieldNames = new ArrayList<>();
          if (types.size() == 1) {
            fieldNames.add(field.name);
          } else
            for (int i = 1, C = types.size(); i <= C; i++) {
              fieldNames.add(field.name + i);
            }
          out.println("alter table " + tabPrefix + tname + "_" + field.name + " add "
              + formForeignKey(fieldNames, (Table)field.type) + separator);
        }
      }
    }
    out.println();
    
    printViews(tnames, out);
    
    printInsertFunctions(tnames, outPrograms);
  }
  
  private void printPrimaryForeignKey(Table table, PrintStream out) {
    for (Field field : table.keys) {
      if (field.type instanceof Table) {
        List<Field> keys = ((Table)field.type).keys;
        out.print("alter table " + tabPrefix + table.name + " add foreign key (");
        if (keys.size() == 1) {
          out.print(field.name);
        } else
          for (int i = 0, C = keys.size(); i < C; i++) {
            out.print(i == 0 ? "" :", ");
            out.print(field.name + (i + 1));
          }
        out.print(") references " + tabPrefix + field.type.name + " (");
        boolean first = true;
        for (Field key : keys) {
          out.print(first ? "" :", ");
          first = false;
          out.print(key.name);
        }
        out.println(')' + separator);
      }
    }
  }
  
  private void printKeySql(Table table, PrintStream out) {
    out.println("create table " + tabPrefix + table.name + " (");
    List<String> keyNames = new ArrayList<>();
    
    for (Field field : table.keys) {
      List<SimpleType> types = new ArrayList<>();
      field.type.assignSimpleTypes(types);
      if (types.size() == 0) throw new StruParseException("No key types for " + field.type);
      if (types.size() == 1) {
        out.println("  " + field.name + ' ' + sqld().sqlType(types.get(0)) + " not null,");
        keyNames.add(field.name);
      } else {
        int index = 1;
        for (SimpleType st : types) {
          String keyName = field.name + index++;
          out.println("  " + keyName + ' ' + sqld().sqlType(st) + " not null,");
          keyNames.add(keyName);
        }
      }
    }
    out.println("  " + cre + " timestamp default current_timestamp not null,");
    StringBuilder sb = new StringBuilder();
    for (String name : keyNames) {
      if (sb.length() > 0) sb.append(", ");
      sb.append(name);
    }
    out.println("  primary key(" + sb + ")");
    out.println(')' + separator);
  }
  
  private void printFieldsSql(Field field, PrintStream out) {
    
    List<String> keyFields = new ArrayList<>();
    
    out.println("create table " + tabPrefix + field.table.name + "_" + field.name + " (");
    
    for (Field key : field.table.keys) {
      List<SimpleType> types = new ArrayList<>();
      key.type.assignSimpleTypes(types);
      
      if (types.size() == 0) throw new StruParseException("No simple type for " + key.type.name);
      
      if (types.size() == 1) {
        String sqlType = sqld().sqlType(types.get(0));
        out.println("  " + key.name + " " + sqlType + " not null,");
        keyFields.add(key.name);
      } else {
        int i = 1;
        for (SimpleType stype : types) {
          String name = key.name + i++;
          keyFields.add(name);
          String sqlType = sqld().sqlType(stype);
          out.println("  " + name + " " + sqlType + " not null,");
        }
      }
    }
    
    out.println("  " + ts + " timestamp default current_timestamp not null,");
    
    {
      List<SimpleType> types = new ArrayList<>();
      field.type.assignSimpleTypes(types);
      
      if (types.size() == 0) throw new StruParseException("No simple type for field "
          + field.table.name + "." + field.name);
      
      if (types.size() == 1) {
        String sqlType = sqld().sqlType(types.get(0));
        out.println("  " + field.name + " " + sqlType + ",");
      } else {
        int i = 1;
        for (SimpleType stype : types) {
          String name = field.name + i++;
          String sqlType = sqld().sqlType(stype);
          out.println("  " + name + " " + sqlType + ",");
        }
      }
    }
    
    {
      out.print("  primary key (");
      for (String f : keyFields) {
        out.print(f + ", ");
      }
      out.println(ts + "),");
    }
    {
      out.println("  " + formForeignKey(keyFields, field.table));
    }
    
    out.println(")" + separator);
  }
  
  private String formForeignKey(List<String> fieldNames, Table table) {
    StringBuilder sb = new StringBuilder();
    sb.append("foreign key (");
    for (String key : fieldNames) {
      sb.append(key).append(", ");
    }
    sb.setLength(sb.length() - 2);
    sb.append(") references ").append(tabPrefix).append(table.name).append(" (");
    for (Field field : table.keys) {
      List<SimpleType> types = new ArrayList<>();
      field.type.assignSimpleTypes(types);
      if (types.size() == 1) {
        sb.append(field.name).append(", ");
      } else {
        for (int i = 1, C = types.size(); i <= C; i++) {
          sb.append(field.name + i).append(", ");
        }
      }
    }
    sb.setLength(sb.length() - 2);
    sb.append(')');
    return sb.toString();
  }
  
  private String space(int tabSize, int tabs) {
    StringBuilder sb = new StringBuilder(tabSize * tabs);
    for (int i = 0, C = tabSize * tabs; i < C; i++) {
      sb.append(' ');
    }
    return sb.toString();
  }
  
  private void printViews(List<String> tnames, PrintStream out) {
    for (String tname : tnames) {
      Table table = sg.stru.tables.get(tname);
      for (Field field : table.fields) {
        printFieldView(out, field);
        out.println();
      }
      printTableView(out, table);
      out.println();
    }
  }
  
  private void printFieldView(PrintStream out, Field field) {
    StringBuilder sb = new StringBuilder();
    sb.append("create view " + vPrefix + field.table.name + "_" + field.name + " as\n");
    formFieldSelect(sb, field, null, 2, 0);
    out.println(sb + separator);
  }
  
  private void formFieldSelect(StringBuilder sb, Field field, String time, int tabSize, int orig) {
    String s0 = " ", s1 = s0, s2 = s0, nl = "";
    if (tabSize > 0) {
      s0 = space(tabSize, orig + 0);
      s1 = space(tabSize, orig + 1);
      s2 = space(tabSize, orig + 2);
      nl = "\n";
    }
    
    String T = "", TF = "";//T - table, TF - table.field
    
    final String fieldTableTS;
    if (time == null || time.trim().length() == 0) {
      fieldTableTS = tabPrefix + field.table.name + "_" + field.name;
    } else {
      int idx = time.indexOf('.');
      if (idx < 0) {
        T = time;
        TF = time + '.' + time;
      } else {
        T = time.substring(0, idx);
        TF = time;
      }
      fieldTableTS = "(select x.* from " + tabPrefix + field.table.name + "_" + field.name + " x, "
          + T + " where x." + ts + " <= " + TF + ')';
    }
    
    List<String> keyNames = field.table.keyNames();
    
    List<String> names = new ArrayList<>();
    
    List<SimpleType> types = new ArrayList<>();
    field.type.assignSimpleTypes(types);
    if (types.size() == 1) {
      names.add(field.name);
    } else
      for (int i = 1, C = types.size(); i <= C; i++) {
        names.add(field.name + i);
      }
    
    sb.append(s0 + "select" + nl);
    {
      boolean first = true;
      for (String tname : keyNames) {
        String k = first ? " " :",";
        first = false;
        sb.append(s1 + k + "aa." + tname + nl);
      }
    }
    for (String name : names) {
      sb.append(s1 + ",bb." + name + nl);
    }
    sb.append(s0 + "from (" + nl);
    sb.append(s1 + "select" + nl);
    for (String tname : keyNames) {
      sb.append(s2 + "a." + tname + "," + nl);
    }
    sb.append(s2 + "max(b." + ts + ") ts" + nl);
    sb.append(s1 + "from " + tabPrefix + field.table.name + " a" + nl);
    sb.append(s1 + "left join " + fieldTableTS + " b" + nl);
    for (int i = 0, C = keyNames.size(); i < C; i++) {
      sb.append(s1).append(i == 0 ? "on" :"and");
      sb.append(" a." + keyNames.get(i) + " = b." + keyNames.get(i) + nl);
    }
    if (time != null && time.trim().length() > 0) {
      sb.append(s1 + "," + T + " where b." + ts + " <= " + time + nl);
    }
    sb.append(s1 + "group by" + nl);
    for (int i = 0, C = keyNames.size(); i < C; i++) {
      String k = i == 0 ? " " :",";
      sb.append(s2 + k + "a." + keyNames.get(i) + nl);
    }
    sb.append(s0 + ") aa left join " + tabPrefix + field.table.name + "_" + field.name + " bb" + nl);
    for (int i = 0, C = keyNames.size(); i < C; i++) {
      String tname = keyNames.get(i);
      String fname = keyNames.get(i);
      String and = i == 0 ? "on" :"and";
      sb.append(s0 + and + " aa." + tname + " = bb." + fname + nl);
    }
    sb.append(s0 + "and aa." + ts + " = bb." + ts);
  }
  
  private void printTableView(PrintStream out, Table table) {
    StringBuilder sb = new StringBuilder();
    sb.append("create view " + vPrefix + table.name + " as\n");
    formTableSelect(sb, table, null, 2, 0);
    out.println(sb + separator);
  }
  
  private void formTableSelect(StringBuilder sb, Table table, String time, int tabSize, int orig) {
    String s0 = " ", s1 = s0, s2 = s0, nl = "";
    if (tabSize > 0) {
      s0 = space(tabSize, orig + 0);
      s1 = space(tabSize, orig + 1);
      s2 = space(tabSize, orig + 2);
      nl = "\n";
    }
    String T = null, TF = null;//T - table, TF - table.field
    if (time != null && time.trim().length() > 0) {
      int idx = time.indexOf('.');
      if (idx < 0) {
        T = time;
        TF = time + '.' + time;
      } else {
        T = time.substring(0, idx);
        TF = time;
      }
    }
    
    List<String> keyNames = table.keyNames();
    
    sb.append(s0 + "select" + nl);
    for (String fname : keyNames) {
      sb.append(s1 + " x." + fname + ',' + nl);
    }
    sb.append(s1 + " x." + cre + nl);
    {
      int i = 1;
      for (Field field : table.fields) {
        List<SimpleType> types = new ArrayList<>();
        field.type.assignSimpleTypes(types);
        if (types.size() == 1) {
          sb.append(s1 + ",x" + i + "." + field.name + nl);
        } else
          for (int j = 1, C = types.size(); j <= C; j++) {
            sb.append(s1 + ",x" + i + "." + field.name + j + nl);
          }
        i++;
      }
    }
    {
      sb.append(s0 + "from ");
      String t = tabPrefix + table.name;
      if (T == null) {
        sb.append(t);
      } else {
        sb.append("(select u.* from " + t + " u, " + T + " where u." + cre + " <= " + TF + ")");
      }
      sb.append(" x" + nl);
    }
    {
      int i = 1;
      for (Field field : table.fields) {
        sb.append(s0 + "left join" + nl);
        if (T == null) {
          sb.append(s2 + vPrefix + table.name + "_" + field.name + nl);
        } else {
          sb.append('(');
          formFieldSelect(sb, field, time, tabSize, orig + 2);
          sb.append(')');
        }
        boolean first = true;
        for (String key : keyNames) {
          sb.append(s1).append(first ? "x" + i + " on " :"and ");
          first = false;
          sb.append("x" + i + "." + key + " = x." + key + nl);
        }
        i++;
      }
    }
  }
  
  private void printInsertFunctions(List<String> tnames, PrintStream out) {
    for (String tname : tnames) {
      Table table = sg.stru.tables.get(tname);
      printTableInsertFunction(out, table);
      for (Field field : table.fields) {
        printFieldInsertFunction(out, field);
        out.println();
      }
      out.println();
    }
  }
  
  protected abstract void printTableInsertFunction(PrintStream out, Table table);
  
  protected abstract void printFieldInsertFunction(PrintStream out, Field field);
  
  public void generateJava() {
    
    Set<String> set = new HashSet<>();
    
    for (Table table : sg.stru.tables.values()) {
      
      List<Field> fields = new ArrayList<>();
      fields.addAll(table.fields);
      fields.addAll(table.keys);
      
      for (Field field : fields) {
        if (field.type instanceof EnumType) {
          EnumType et = (EnumType)field.type;
          if (et.pack == null) et.pack = modelStruPackage;
          if (!set.contains(et.objectType())) {
            set.add(et.objectType());
            generateEnum(et);
          }
        }
      }
    }
    
    for (Table table : sg.stru.tables.values()) {
      ClassOuter fieldsClass = generateFieldsJava(table);
      ClassOuter java = generateJava(table, fieldsClass);
      generateDao(table, java, fieldsClass);
    }
    
    generateIfaces();
  }
  
  private ClassOuter generateFieldsJava(Table table) {
    ClassOuter ou = new ClassOuter(modelStruPackage + table.subpackage(), "Fields", table.name);
    
    if (modelStruExtends != null && modelStruImplements != null) {
      throw new IllegalArgumentException("I do not know what to do: implements "
          + modelStruImplements + " or extends " + modelStruExtends);
    }
    
    String _parent_ = "";
    
    if (modelStruExtends != null) {
      _parent_ = " extends " + ou._(modelStruExtends);
    }
    if (modelStruImplements != null) {
      _parent_ = " implements " + ou._(modelStruImplements);
    }
    
    ou.println("public abstract class " + ou.className + _parent_ + " {");
    
    for (Field field : table.fields) {
      for (FieldInfo fi : field.fieldInfo()) {
        FieldOuter f = ou.addField(fi.javaType, fi.name);
        ou.println("public " + ou._(f.type.objectType()) + " " + f.name + ";");
      }
    }
    
    {
      ou.println("public " + ou.className + " assign(" + ou.className + " from) {");
      ou.println("if (from == null) return this;");
      for (FieldOuter f : ou.fields) {
        ou.println("this." + f.name + " = from." + f.name + ";");
      }
      ou.println("return this;");
      ou.println("}");
    }
    
    for (FieldOuter fo : ou.fields) {
      if (SimpleType.tbool.equals(fo.type)) {
        ou.println("public int get" + firstUpper(fo.name) + "Int() {");
        ou.println("  return (" + fo.name + " != null && " + fo.name + ")?1:0;");
        ou.println("}");
      }
    }
    
    ou.generateTo(javaGenStruDir);
    
    return ou;
  }
  
  private ClassOuter generateJava(Table table, ClassOuter fieldsClass) {
    ClassOuter java = new ClassOuter(modelPackage + table.subpackage(), "", table.name);
    java.println("public class " + java.className + " extends " + java._(fieldsClass.name()) + " {");
    
    for (Field f : table.keys) {
      for (FieldInfo fi : f.fieldInfo()) {
        java.println("public " + java._(fi.javaType.javaType()) + " " + fi.name + ";");
      }
    }
    
    {
      java.println("@Override public int hashCode() {");
      java.print("return " + java._(ARRAYS) + ".hashCode(new Object[] {");
      for (Field f : table.keys) {
        for (FieldInfo fi : f.fieldInfo()) {
          java.print(fi.name + ",");
        }
      }
      java.println(" });");
      java.println("}");
    }
    {
      java.println("@Override public boolean equals(Object obj) {");
      java.println("if (this == obj) return true;");
      java.println("if (obj == null) return false;");
      java.println("if (getClass() != obj.getClass()) return false;");
      java.println(java.className + " other = (" + java.className + ")obj;");
      boolean first = true;
      for (Field field : table.keys) {
        for (FieldInfo fi : field.fieldInfo()) {
          java.print(first ? "return " :"&& ");
          first = false;
          java.print(java._(OBJECTS) + ".equals(" + fi.name + ", other." + fi.name + ")");
        }
      }
      java.println(";");
      java.println("}");
    }
    
    java.println();
    java.println("public " + java.className + "() {}");
    java.println();
    
    {
      java.print("public " + java.className + "(");
      boolean first = true;
      for (Field field : table.keys) {
        for (FieldInfo fi : field.fieldInfo()) {
          java.print(first ? "" :", ");
          first = false;
          java.print(java._(fi.javaType.javaType()) + " " + fi.name);
        }
      }
      java.println(") {");
      
      for (Field field : table.keys) {
        for (FieldInfo fi : field.fieldInfo()) {
          java.println("this." + fi.name + " = " + fi.name + ";");
        }
      }
      
      java.println("}");
    }
    
    for (Command command : table.commands) {
      if (command instanceof ToDictionary) {
        generateJavaToDictionary(java, table, (ToDictionary)command);
      }
    }
    
    java.generateTo(javaGenDir);
    return java;
  }
  
  public static final String SELECT = "org.apache.ibatis.annotations.Select";
  public static final String UPDATE = "org.apache.ibatis.annotations.Update";
  public static final String DELETE = "org.apache.ibatis.annotations.Delete";
  public static final String INSERT = "org.apache.ibatis.annotations.Insert";
  public static final String PARAM = "org.apache.ibatis.annotations.Param";
  public static final String LIST = "java.util.List";
  public static final String DATE = "java.util.Date";
  public static final String OBJECTS = "java.util.Objects";
  public static final String ARRAYS = "java.util.Arrays";
  
  private void generateEnum(EnumType et) {
    if (et.as != null) return;
    ClassOuter ret = new ClassOuter(et.pack(), "", et.name);
    ret.println("public enum " + ret.className + " {");
    
    ret.print("  ");
    for (String name : et.values) {
      ret.print(name + ", ");
    }
    ret.println(";");
    ret.println("");
    
    ret.println("public static " + ret.className + " valueOfOrNull(String str) {");
    ret.println("  try { return valueOf(str);");
    ret.println("  } catch (java.lang.IllegalArgumentException e) {");
    ret.println("    return null;");
    ret.println("  }");
    ret.println("}");
    
    ret.generateTo(javaGenStruDir);
  }
  
  class DaoClasses {
    ClassOuter commonDao;
    ClassOuter postgres;
    ClassOuter oracle;
    
    public DaoClasses(ClassOuter common, ClassOuter postgres, ClassOuter oracle) {
      this.commonDao = common;
      this.postgres = postgres;
      this.oracle = oracle;
    }
  }
  
  private DaoClasses generateDao(Table table, ClassOuter java, ClassOuter fieldsClass) {
    
    ClassOuter comm = new ClassOuter(daoPackage + table.subpackage(), "", table.name + daoSuffix);
    ClassOuter postgres = new ClassOuter(daoPackage + ".postgres" + table.subpackage(), "",
        table.name + "Postgres" + daoSuffix);
    ClassOuter oracle = new ClassOuter(daoPackage + ".oracle" + table.subpackage(), "", table.name
        + "Oracle" + daoSuffix);
    
    comm.println("public interface " + comm.className + " {");
    postgres.println("public interface " + postgres.className //
        + " extends " + postgres._(comm.name()) + "{");
    oracle.println("public interface " + oracle.className //
        + " extends " + oracle._(comm.name()) + "{");
    
    if (table.hasSequence()) {
      SimpleType type = (SimpleType)table.keys.get(0).type;
      comm.println("  " + type.javaType + " next();");
      String seq = seqPrefix + table.name;
      
      postgres.println("  @Override");
      postgres.println("  @" + postgres._(SELECT) + "(\"select nextval('" + seq + "')\")");
      postgres.println("  " + type.javaType + " next();");
      
      oracle.println("  @Override");
      oracle.println("  @" + oracle._(SELECT) + "(\"select " + seq + ".nextval from dual\")");
      oracle.println("  " + type.javaType + " next();");
      
    }
    
    comm.println();
    generateDaoTableInserts(comm, table, java, fieldsClass);
    generateDaoTableLoad(comm, table, java, fieldsClass);
    generateDaoTableLoadAt(comm, table, java, fieldsClass);
    for (Field field : table.fields) {
      generateDaoFieldInserts(comm, field, java, fieldsClass);
    }
    
    generateDaoTableCommands(comm, table, java, fieldsClass);
    
    comm.generateTo(javaGenDir);
    postgres.generateTo(javaGenDir);
    oracle.generateTo(javaGenDir);
    
    return new DaoClasses(comm, postgres, oracle);
  }
  
  private void generateDaoTableLoad(ClassOuter comm, Table table, ClassOuter java,
      ClassOuter fieldsClass) {
    List<FieldInfo> keyInfo = table.keyInfo();
    {
      comm.print("  @" + comm._(SELECT) + "(\"select * from " + vPrefix + table.name);
      {
        boolean first = true;
        for (FieldInfo fi : keyInfo) {
          comm.print(first ? " where " :" and ");
          first = false;
          comm.print(fi.name + " = #{" + fi.name + "}");
        }
      }
      comm.println("\")");
      
      comm.print("  " + comm._(java.name()) + " load(");
      {
        boolean first = true;
        for (FieldInfo fi : keyInfo) {
          comm.print(first ? "" :", ");
          first = false;
          comm.print("@" + comm._(PARAM) + "(\"" + fi.name + "\")");
          comm.print(comm._(fi.javaType.javaType()) + " " + fi.name);
        }
      }
      comm.println(");");
    }
    if (table.keys.size() > 1) {
      {
        List<SimpleType> tmp = new ArrayList<>();
        table.keys.get(table.keys.size() - 1).type.assignSimpleTypes(tmp);
        for (int i = 0, C = tmp.size(); i < C; i++) {
          keyInfo.remove(keyInfo.size() - 1);
        }
      }
      
      comm.print("  @" + comm._(SELECT) + "(\"select * from " + vPrefix + table.name);
      {
        boolean first = true;
        for (FieldInfo fi : keyInfo) {
          comm.print(first ? " where " :" and ");
          first = false;
          comm.print(fi.name + " = #{" + fi.name + "}");
        }
      }
      comm.println("\")");
      
      comm.print("  " + comm._(LIST) + "<" + comm._(java.name()) + "> loadList(");
      {
        boolean first = true;
        for (FieldInfo fi : keyInfo) {
          comm.print(first ? "" :", ");
          first = false;
          comm.print("@" + comm._(PARAM) + "(\"" + fi.name + "\")");
          comm.print(comm._(fi.javaType.javaType()) + " " + fi.name);
        }
      }
      comm.println(");");
    }
  }
  
  private void generateDaoTableInserts(ClassOuter comm, Table table, ClassOuter java,
      ClassOuter fieldsClass) {
    List<FieldInfo> keyInfo = table.keyInfo();
    {
      comm.print("  @" + comm._(INSERT) + "(\"insert into " + tabPrefix + table.name);
      comm.print(" (");
      {
        boolean first = true;
        for (FieldInfo fi : keyInfo) {
          comm.print(first ? "" :", ");
          first = false;
          comm.print(fi.name);
        }
      }
      comm.print(") values (");
      {
        boolean first = true;
        for (FieldInfo fi : keyInfo) {
          comm.print(first ? "" :", ");
          first = false;
          comm.print("#{" + fi.name + "}");
        }
      }
      comm.println(")\")");
      comm.print("  void ins(");
      comm.print(comm._(java.name()) + " " + table.name);
      comm.println(");");
    }
    {
      comm.print("  @" + comm._(INSERT) + "(\"insert into " + tabPrefix + table.name);
      comm.print(" (");
      {
        boolean first = true;
        for (FieldInfo fi : keyInfo) {
          comm.print(first ? "" :", ");
          first = false;
          comm.print(fi.name);
        }
      }
      comm.print(") values (");
      {
        boolean first = true;
        for (FieldInfo fi : keyInfo) {
          comm.print(first ? "" :", ");
          first = false;
          comm.print("#{" + fi.name + "}");
        }
      }
      comm.println(")\")");
      comm.print("  void add(");
      {
        boolean first = true;
        for (FieldInfo fi : keyInfo) {
          comm.print(first ? "" :", ");
          first = false;
          comm.print("@" + comm._(PARAM) + "(\"" + fi.name + "\")");
          comm.print(comm._(fi.javaType.javaType()) + " " + fi.name);
        }
      }
      comm.println(");");
    }
  }
  
  private void generateDaoFieldInserts(ClassOuter comm, Field field, ClassOuter java,
      ClassOuter fieldsClass) {
    
    List<FieldInfo> keyInfo = field.table.keyInfo();
    List<FieldInfo> fieldInfo = field.fieldInfo();
    List<FieldInfo> all = new ArrayList<>();
    all.addAll(keyInfo);
    all.addAll(fieldInfo);
    {
      insField(comm, field, java, all);
      if (SimpleType.ttime.equals(field.type)) {
        insFieldWithNow(comm, field, java, keyInfo, all);
      }
    }
    {
      setField(comm, field, keyInfo, fieldInfo, all);
      if (SimpleType.ttime.equals(field.type)) {
        setFieldWithNow(comm, field, keyInfo, all);
      }
    }
  }
  
  private void setFieldWithNow(ClassOuter comm, Field field, List<FieldInfo> keyInfo,
      List<FieldInfo> all) {
    comm.print("  @" + comm._(INSERT) + "(\"insert into " + tabPrefix + field.table.name + "_"
        + field.name);
    comm.print(" (");
    {
      boolean first = true;
      for (FieldInfo fi : all) {
        comm.print(first ? "" :", ");
        first = false;
        comm.print(fi.name);
      }
    }
    comm.print(") values (");
    {
      for (FieldInfo fi : keyInfo) {
        if (SimpleType.tbool.equals(fi.javaType)) {
          comm.print("#{" + fi.name + "Int}, ");
        } else {
          comm.print("#{" + fi.name + "}, ");
        }
      }
    }
    comm.println("current_timestamp)\")");
    comm.print("  void set" + firstUpper(field.name) + "WithNow(");
    {
      boolean first = true;
      for (FieldInfo fi : keyInfo) {
        comm.print(first ? "" :", ");
        first = false;
        comm.print("@" + comm._(PARAM) + "(\"" + fi.name + "\")" + comm._(fi.javaType.javaType())
            + " " + fi.name);
      }
    }
    comm.println(");");
  }
  
  private void setField(ClassOuter comm, Field field, List<FieldInfo> keyInfo,
      List<FieldInfo> fieldInfo, List<FieldInfo> all) {
    comm.print("  @" + comm._(INSERT) + "(\"insert into " + tabPrefix + field.table.name + "_"
        + field.name);
    comm.print(" (");
    {
      boolean first = true;
      for (FieldInfo fi : all) {
        comm.print(first ? "" :", ");
        first = false;
        comm.print(fi.name);
      }
    }
    comm.print(") values (");
    {
      boolean first = true;
      for (FieldInfo fi : all) {
        comm.print(first ? "" :", ");
        first = false;
        if (SimpleType.tbool.equals(fi.javaType)) {
          comm.print("#{" + fi.name + "Int}");
        } else {
          comm.print("#{" + fi.name + "}");
        }
      }
    }
    comm.println(")\")");
    comm.print("  void set" + firstUpper(field.name) + "(");
    {
      boolean first = true;
      for (FieldInfo fi : keyInfo) {
        comm.print(first ? "" :", ");
        first = false;
        comm.print("@" + comm._(PARAM) + "(\"" + fi.name + "\")" + comm._(fi.javaType.javaType())
            + " " + fi.name);
      }
    }
    {
      for (FieldInfo fi : fieldInfo) {
        if (SimpleType.tbool.equals(fi.javaType)) {
          comm.print(", @" + comm._(PARAM) + "(\"" + fi.name + "Int\") int " + fi.name + "Int");
        } else {
          comm.print(", @" + comm._(PARAM) + "(\"" + fi.name + "\")"
              + comm._(fi.javaType.objectType()) + " " + fi.name);
        }
      }
    }
    comm.println(");");
  }
  
  private void insFieldWithNow(ClassOuter comm, Field field, ClassOuter java,
      List<FieldInfo> keyInfo, List<FieldInfo> all) {
    comm.print("  @" + comm._(INSERT) + "(\"insert into " + tabPrefix + field.table.name + "_"
        + field.name);
    comm.print(" (");
    {
      boolean first = true;
      for (FieldInfo fi : all) {
        comm.print(first ? "" :", ");
        first = false;
        comm.print(fi.name);
      }
    }
    comm.print(") values (");
    {
      for (FieldInfo fi : keyInfo) {
        if (SimpleType.tbool.equals(fi.javaType)) {
          comm.print("#{" + fi.name + "Int}, ");
        } else {
          comm.print("#{" + fi.name + "}, ");
        }
      }
    }
    comm.println("current_timestamp)\")");
    comm.println("  void ins" + firstUpper(field.name) + "WithNow(" + comm._(java.name()) + " "
        + field.table.name + ");");
  }
  
  private void insField(ClassOuter comm, Field field, ClassOuter java, List<FieldInfo> all) {
    comm.print("  @" + comm._(INSERT) + "(\"insert into " + tabPrefix + field.table.name + "_"
        + field.name);
    comm.print(" (");
    {
      boolean first = true;
      for (FieldInfo fi : all) {
        comm.print(first ? "" :", ");
        first = false;
        comm.print(fi.name);
      }
    }
    comm.print(") values (");
    {
      boolean first = true;
      for (FieldInfo fi : all) {
        comm.print(first ? "" :", ");
        first = false;
        if (SimpleType.tbool.equals(fi.javaType)) {
          comm.print("#{" + fi.name + "Int}");
        } else {
          comm.print("#{" + fi.name + "}");
        }
      }
    }
    comm.println(")\")");
    comm.println("  void ins" + firstUpper(field.name) + "(" + comm._(java.name()) + " "
        + field.table.name + ");");
  }
  
  private void generateDaoTableLoadAt(ClassOuter comm, Table table, ClassOuter java,
      ClassOuter fieldsClass) {
    
    StringBuilder sb = new StringBuilder();
    formTableSelect(sb, table, "tts.ts", 0, 0);
    
    List<FieldInfo> keyInfo = table.keyInfo();
    {
      comm.print("  @" + comm._(SELECT) + "(\"with tts as (select #{ts} ts from dual), xx as ("
          + sb + ") select * from xx");
      {
        boolean first = true;
        for (FieldInfo fi : keyInfo) {
          comm.print(first ? " where " :" and ");
          first = false;
          comm.print("xx." + fi.name + " = #{" + fi.name + "}");
        }
      }
      comm.println("\")");
      
      comm.print("  " + comm._(java.name()) + " loadAt(@" + comm._(PARAM) + "(\"" + ts + "\")"
          + comm._(DATE) + " " + ts);
      for (FieldInfo fi : keyInfo) {
        comm.print(", @" + comm._(PARAM) + "(\"" + fi.name + "\")");
        comm.print(comm._(fi.javaType.javaType()) + " " + fi.name);
      }
      comm.println(");");
    }
    if (table.keys.size() > 1) {
      {
        List<SimpleType> tmp = new ArrayList<>();
        table.keys.get(table.keys.size() - 1).type.assignSimpleTypes(tmp);
        for (int i = 0, C = tmp.size(); i < C; i++) {
          keyInfo.remove(keyInfo.size() - 1);
        }
      }
      
      comm.print("  @" + comm._(SELECT) + "(\"with tts as (select #{ts} ts from dual), xx as ("
          + sb + ") select * from xx");
      {
        boolean first = true;
        for (FieldInfo fi : keyInfo) {
          comm.print(first ? " where " :" and ");
          first = false;
          comm.print("xx." + fi.name + " = #{" + fi.name + "}");
        }
      }
      comm.println("\")");
      
      comm.print("  " + comm._(LIST) + "<" + comm._(java.name()) + "> loadListAt(@" + comm._(PARAM)
          + "(\"" + ts + "\")" + comm._(DATE) + " " + ts);
      for (FieldInfo fi : keyInfo) {
        comm.print(", @" + comm._(PARAM) + "(\"" + fi.name + "\")");
        comm.print(comm._(fi.javaType.javaType()) + " " + fi.name);
      }
      comm.println(");");
    }
  }
  
  private void generateIfaces() {
    generateTandV();
    generateVT();
  }
  
  private void generateTandV() {
    ClassOuter t = new ClassOuter(daoPackage + ".i", "", "T");
    ClassOuter v = new ClassOuter(daoPackage + ".i", "", "V");
    t.println("public interface " + t.className + " {");
    v.println("public interface " + v.className + " {");
    
    for (Table table : sg.stru.tables.values()) {
      t.println("  String " + table.name + " = \"" + tabPrefix + table.name + "\";");
      v.println("  String " + table.name + " = \"" + vPrefix + table.name + "\";");
      for (Field field : table.fields) {
        t.println("  String " + table.name + "_" + field.name + " = \"" + tabPrefix + table.name
            + "_" + field.name + "\";");
        v.println("  String " + table.name + "_" + field.name + " = \"" + vPrefix + table.name
            + "_" + field.name + "\";");
      }
      t.println();
      v.println();
    }
    
    t.generateTo(javaGenDir);
    v.generateTo(javaGenDir);
  }
  
  private void generateVT() {
    ClassOuter vt = new ClassOuter(daoPackage + ".i", "", "VT");
    vt.println("public interface " + vt.className + " {");
    
    for (Table table : sg.stru.tables.values()) {
      StringBuilder tab = new StringBuilder();
      formTableSelect(tab, table, "tts.ts", 0, 0);
      vt.println("  String " + table.name + " = \"" + tab + "\";");
      for (Field field : table.fields) {
        StringBuilder fi = new StringBuilder();
        formFieldSelect(fi, field, "tts.ts", 0, 0);
        vt.println("  String " + table.name + "_" + field.name + " = \"" + fi + "\";");
      }
      vt.println();
    }
    
    vt.generateTo(javaGenDir);
  }
  
  private void generateDaoTableCommands(ClassOuter comm, Table table, ClassOuter java,
      ClassOuter fieldsClass) {
    for (Command command : table.commands) {
      if (command instanceof SelectAll) {
        generateDaoTableSelectAll(comm, table, java, (SelectAll)command);
        continue;
      }
      if (command instanceof ToDictionary) return;//Ignore here
      throw new StruParseException("Unknown command class " + command.getClass());
    }
  }
  
  private void generateDaoTableSelectAll(ClassOuter comm, Table table, ClassOuter java,
      SelectAll command) {
    comm.println("@" + comm._(SELECT) + "(\"select * from " + vPrefix + table.name + " "
        + command.orderBy() + "\")");
    comm.println(comm._(LIST) + "<" + java.className + "> " + command.methodName + "();");
  }
  
  private void generateJavaToDictionary(ClassOuter java, Table table, ToDictionary todict) {
    java.println("public " + java._(todict.toClass) + " toDictionary() {");
    
    {
      java.print("  return new " + java._(todict.toClass) + "(");
      boolean first = true;
      for (FieldInfo fi : table.keyInfo()) {
        java.print(first ? "" :", ");
        first = false;
        java.print(fi.name);
      }
      if (todict.more != null && todict.more.trim().length() > 0) {
        java.print(", " + todict.more);
      }
      java.println(");");
    }
    
    java.println("}");
  }
}
