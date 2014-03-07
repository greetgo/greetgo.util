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
  
  public LibType libType = LibType.MYBATIS;
  
  public static final String MYBATIS_StatementType = "org.apache.ibatis.mapping.StatementType";
  public static final String MYBATIS_OPTIONS = "org.apache.ibatis.annotations.Options";
  public static final String MYBATIS_SELECT = "org.apache.ibatis.annotations.Select";
  public static final String MYBATIS_UPDATE = "org.apache.ibatis.annotations.Update";
  public static final String MYBATIS_DELETE = "org.apache.ibatis.annotations.Delete";
  public static final String MYBATIS_INSERT = "org.apache.ibatis.annotations.Insert";
  public static final String MYBATIS_PARAM = "org.apache.ibatis.annotations.Param";
  
  public static final String GBATIS_PRM = "kz.greetgo.gbatis.t.Prm";
  public static final String GBATIS_SELE = "kz.greetgo.gbatis.t.Sele";
  public static final String GBATIS_CALL = "kz.greetgo.gbatis.t.Call";
  public static final String GBATIS_AUTOIMPL = "kz.greetgo.gbatis.t.Autoimpl";
  
  public static final String LIST = "java.util.List";
  public static final String DATE = "java.util.Date";
  public static final String OBJECTS = "java.util.Objects";
  public static final String ARRAYS = "java.util.Arrays";
  
  private final StruGenerator sg;
  
  protected abstract SqlDialect sqld();
  
  public final Conf conf;
  protected final ViewFormer viewFormer;
  
  public Nf6Generator(Conf conf, StruGenerator sg) {
    this.conf = conf;
    this.sg = sg;
    this.viewFormer = new ViewFormerRowNumber(conf);
  }
  
  public void printSqls(PrintStream out) {
    
    printPrepareSqls(out);
    
    List<String> tnames = getTnames();
    
    for (String name : tnames) {
      Table table = sg.stru.tables.get(name);
      printKeyTable(table, out);
      for (Field field : table.fields) {
        printFieldsTable(field, out);
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
          out.println("create sequence " + conf.seqPrefix + table.name + from + conf.separator);
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
          out.println("alter table " + conf.tabPrefix + tname + "_" + field.name + " add "
              + formForeignKey(fieldNames, (Table)field.type) + conf.separator);
        }
      }
    }
    out.println();
    
    printViews(tnames, out);
  }
  
  public void printPrograms(PrintStream out) {
    List<String> tnames = getTnames();
    printInsertFunctions(tnames, out);
  }
  
  private List<String> getTnames() {
    List<String> tnames = new ArrayList<>();
    tnames.addAll(sg.stru.tables.keySet());
    Collections.sort(tnames);
    return tnames;
  }
  
  protected abstract void printPrepareSqls(PrintStream out);
  
  private void printPrimaryForeignKey(Table table, PrintStream out) {
    for (Field field : table.keys) {
      if (field.type instanceof Table) {
        List<Field> keys = ((Table)field.type).keys;
        out.print("alter table " + conf.tabPrefix + table.name + " add foreign key (");
        if (keys.size() == 1) {
          out.print(field.name);
        } else
          for (int i = 0, C = keys.size(); i < C; i++) {
            out.print(i == 0 ? "" :", ");
            out.print(field.name + (i + 1));
          }
        out.print(") references " + conf.tabPrefix + field.type.name + " (");
        boolean first = true;
        for (Field key : keys) {
          out.print(first ? "" :", ");
          first = false;
          out.print(key.name);
        }
        out.println(')' + conf.separator);
      }
    }
  }
  
  private void printKeyTable(Table table, PrintStream out) {
    out.println("create table " + conf.tabPrefix + table.name + " (");
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
    out.println("  " + conf.cre + " timestamp default current_timestamp not null,");
    StringBuilder sb = new StringBuilder();
    for (String name : keyNames) {
      if (sb.length() > 0) sb.append(", ");
      sb.append(name);
    }
    out.println("  primary key(" + sb + ")");
    out.println(')' + conf.separator);
  }
  
  private void printFieldsTable(Field field, PrintStream out) {
    
    List<String> keyFields = new ArrayList<>();
    
    out.println("create table " + conf.tabPrefix + field.table.name + "_" + field.name + " (");
    
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
    
    out.println("  " + conf.ts + " timestamp default current_timestamp not null,");
    
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
      out.println(conf.ts + "),");
    }
    {
      out.println("  " + formForeignKey(keyFields, field.table));
    }
    
    out.println(")" + conf.separator);
  }
  
  private String formForeignKey(List<String> fieldNames, Table table) {
    StringBuilder sb = new StringBuilder();
    sb.append("foreign key (");
    for (String key : fieldNames) {
      sb.append(key).append(", ");
    }
    sb.setLength(sb.length() - 2);
    sb.append(") references ").append(conf.tabPrefix).append(table.name).append(" (");
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
    sb.append("create view " + conf.vPrefix + field.table.name + "_" + field.name + " as\n");
    viewFormer.formFieldSelect(sb, field, null, 2, 0);
    out.println(sb + conf.separator);
  }
  
  private void printTableView(PrintStream out, Table table) {
    StringBuilder sb = new StringBuilder();
    sb.append("create view " + conf.vPrefix + table.name + " as\n");
    viewFormer.formTableSelect(sb, table, null, 2, 0);
    out.println(sb + conf.separator);
  }
  
  private void printInsertFunctions(List<String> tnames, PrintStream out) {
    for (String tname : tnames) {
      Table table = sg.stru.tables.get(tname);
      printTableInsertFunction(out, table);
      for (Field field : table.fields) {
        printFieldInsertFunction(out, field);
      }
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
          if (et.pack == null) et.pack = conf.modelStruPackage;
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
    ClassOuter ou = new ClassOuter(conf.modelStruPackage + table.subpackage(), "Fields", table.name);
    
    if (conf.modelStruExtends != null && conf.modelStruImplements != null) {
      throw new IllegalArgumentException("I do not know what to do: implements "
          + conf.modelStruImplements + " or extends " + conf.modelStruExtends);
    }
    
    String _parent_ = "";
    
    if (conf.modelStruExtends != null) {
      _parent_ = " extends " + ou._(conf.modelStruExtends);
    }
    if (conf.modelStruImplements != null) {
      _parent_ = " implements " + ou._(conf.modelStruImplements);
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
    
    ou.generateTo(conf.javaGenStruDir);
    
    return ou;
  }
  
  private ClassOuter generateJava(Table table, ClassOuter fieldsClass) {
    ClassOuter java = new ClassOuter(conf.modelPackage + table.subpackage(), "", table.name);
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
    
    java.generateTo(conf.javaGenDir);
    return java;
  }
  
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
    
    ret.generateTo(conf.javaGenStruDir);
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
    
    ClassOuter comm = new ClassOuter(conf.daoPackage + table.subpackage(), "", table.name
        + conf.daoSuffix);
    ClassOuter postgres = new ClassOuter(conf.daoPackage + ".postgres" + table.subpackage(), "",
        table.name + "Postgres" + conf.daoSuffix);
    ClassOuter oracle = new ClassOuter(conf.daoPackage + ".oracle" + table.subpackage(), "",
        table.name + "Oracle" + conf.daoSuffix);
    
    String seleAnnPg = libType == LibType.MYBATIS ? postgres._(MYBATIS_SELECT) :postgres
        ._(GBATIS_SELE);
    String seleAnnOra = libType == LibType.MYBATIS ? oracle._(MYBATIS_SELECT) :oracle
        ._(GBATIS_SELE);
    
    comm.println("public interface " + comm.className + " {");
    
    if (libType == LibType.GBATIS) postgres.println("@" + postgres._(GBATIS_AUTOIMPL));
    postgres.println("public interface " + postgres.className //
        + " extends " + postgres._(comm.name()) + "{");
    
    if (libType == LibType.GBATIS) oracle.println("@" + oracle._(GBATIS_AUTOIMPL));
    oracle.println("public interface " + oracle.className //
        + " extends " + oracle._(comm.name()) + "{");
    
    if (table.hasSequence()) {
      SimpleType type = (SimpleType)table.keys.get(0).type;
      comm.println("  " + type.javaType + " next();");
      String seq = conf.seqPrefix + table.name;
      
      postgres.println("  @Override");
      postgres.println("  @" + seleAnnPg + "(\"select nextval('" + seq + "')\")");
      postgres.println("  " + type.javaType + " next();");
      
      oracle.println("  @Override");
      oracle.println("  @" + seleAnnOra + "(\"select " + seq + ".nextval from dual\")");
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
    
    comm.generateTo(conf.javaGenDir);
    postgres.generateTo(conf.javaGenDir);
    oracle.generateTo(conf.javaGenDir);
    
    return new DaoClasses(comm, postgres, oracle);
  }
  
  private void generateDaoTableLoad(ClassOuter comm, Table table, ClassOuter java,
      ClassOuter fieldsClass) {
    
    String seleAnn = libType == LibType.MYBATIS ? comm._(MYBATIS_SELECT) :comm._(GBATIS_SELE);
    String prmAnn = libType == LibType.MYBATIS ? comm._(MYBATIS_PARAM) :comm._(GBATIS_PRM);
    
    List<FieldInfo> keyInfo = table.keyInfo();
    {
      comm.print("  @" + seleAnn + "(\"select * from " + conf.vPrefix + table.name);
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
          comm.print("@" + prmAnn + "(\"" + fi.name + "\")");
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
      
      comm.print("  @" + seleAnn + "(\"select * from " + conf.vPrefix + table.name);
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
          comm.print("@" + prmAnn + "(\"" + fi.name + "\")");
          comm.print(comm._(fi.javaType.javaType()) + " " + fi.name);
        }
      }
      comm.println(");");
    }
  }
  
  private void generateDaoTableInserts(ClassOuter comm, Table table, ClassOuter java,
      ClassOuter fieldsClass) {
    
    String callAnn = libType == LibType.MYBATIS ? comm._(MYBATIS_UPDATE) :comm._(GBATIS_CALL);
    String prmAnn = libType == LibType.MYBATIS ? comm._(MYBATIS_PARAM) :comm._(GBATIS_PRM);
    
    List<FieldInfo> keyInfo = table.keyInfo();
    {
      if (libType == LibType.MYBATIS) {
        comm.println("  @" + comm._(MYBATIS_OPTIONS) + "(statementType = "
            + comm._(MYBATIS_StatementType) + ".CALLABLE)");
      }
      
      comm.print("  @" + callAnn + "(\"{call " + conf._p_ + table.name);
      comm.print(" (");
      {
        boolean first = true;
        for (FieldInfo fi : keyInfo) {
          comm.print(first ? "" :", ");
          first = false;
          comm.print("#{" + fi.name + "}");
        }
      }
      comm.println(")}\")");
      comm.print("  void ins(");
      comm.print(comm._(java.name()) + " " + table.name);
      comm.println(");");
    }
    {
      if (libType == LibType.MYBATIS) {
        comm.println("  @" + comm._(MYBATIS_OPTIONS) + "(statementType = "
            + comm._(MYBATIS_StatementType) + ".CALLABLE)");
      }
      comm.print("  @" + callAnn + "(\"{call " + conf._p_ + table.name);
      comm.print(" (");
      {
        boolean first = true;
        for (FieldInfo fi : keyInfo) {
          comm.print(first ? "" :", ");
          first = false;
          comm.print("#{" + fi.name + "}");
        }
      }
      comm.println(")}\")");
      comm.print("  void add(");
      
      {
        boolean first = true;
        for (FieldInfo fi : keyInfo) {
          comm.print(first ? "" :", ");
          first = false;
          
          comm.print("@" + prmAnn + "(\"" + fi.name + "\")");
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
    
    String callAnn = libType == LibType.MYBATIS ? comm._(MYBATIS_UPDATE) :comm._(GBATIS_CALL);
    String prmAnn = libType == LibType.MYBATIS ? comm._(MYBATIS_PARAM) :comm._(GBATIS_PRM);
    
    if (libType == LibType.MYBATIS) {
      comm.println("  @" + comm._(MYBATIS_OPTIONS) + "(statementType = "
          + comm._(MYBATIS_StatementType) + ".CALLABLE)");
    }
    
    comm.print("  @" + callAnn + "(\"{call " + conf._p_ + field.table.name + "_" + field.name);
    
    comm.print(" (");
    {
      for (FieldInfo fi : keyInfo) {
        if (SimpleType.tbool.equals(fi.javaType)) {
          comm.print("#{" + fi.name + "Int}, ");
        } else {
          comm.print("#{" + fi.name + "}, ");
        }
      }
    }
    comm.println("moment())}\")");
    comm.print("  void set" + firstUpper(field.name) + "WithNow(");
    {
      boolean first = true;
      for (FieldInfo fi : keyInfo) {
        comm.print(first ? "" :", ");
        first = false;
        comm.print("@" + prmAnn + "(\"" + fi.name + "\")" + comm._(fi.javaType.javaType()) + " "
            + fi.name);
      }
    }
    comm.println(");");
  }
  
  private void setField(ClassOuter comm, Field field, List<FieldInfo> keyInfo,
      List<FieldInfo> fieldInfo, List<FieldInfo> all) {
    
    String callAnn = libType == LibType.MYBATIS ? comm._(MYBATIS_UPDATE) :comm._(GBATIS_CALL);
    String prmAnn = libType == LibType.MYBATIS ? comm._(MYBATIS_PARAM) :comm._(GBATIS_PRM);
    
    if (libType == LibType.MYBATIS) {
      comm.println("  @" + comm._(MYBATIS_OPTIONS) + "(statementType = "
          + comm._(MYBATIS_StatementType) + ".CALLABLE)");
    }
    
    comm.print("  @" + callAnn + "(\"{call " + conf._p_ + field.table.name + "_" + field.name);
    
    comm.print(" (");
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
    comm.println(")}\")");
    comm.print("  void set" + firstUpper(field.name) + "(");
    {
      boolean first = true;
      for (FieldInfo fi : keyInfo) {
        comm.print(first ? "" :", ");
        first = false;
        comm.print("@" + prmAnn + "(\"" + fi.name + "\")" + comm._(fi.javaType.javaType()) + " "
            + fi.name);
      }
    }
    {
      for (FieldInfo fi : fieldInfo) {
        if (SimpleType.tbool.equals(fi.javaType)) {
          comm.print(", @" + prmAnn + "(\"" + fi.name + "Int\") int " + fi.name + "Int");
        } else {
          comm.print(", @" + prmAnn + "(\"" + fi.name + "\")" + comm._(fi.javaType.objectType())
              + " " + fi.name);
        }
      }
    }
    comm.println(");");
  }
  
  private void insFieldWithNow(ClassOuter comm, Field field, ClassOuter java,
      List<FieldInfo> keyInfo, List<FieldInfo> all) {
    
    String callAnn = libType == LibType.MYBATIS ? comm._(MYBATIS_UPDATE) :comm._(GBATIS_CALL);
    
    if (libType == LibType.MYBATIS) {
      comm.println("  @" + comm._(MYBATIS_OPTIONS) + "(statementType = "
          + comm._(MYBATIS_StatementType) + ".CALLABLE)");
    }
    
    comm.print("  @" + callAnn + "(\"{call " + conf._p_ + field.table.name + "_" + field.name);
    comm.print(" (");
    {
      for (FieldInfo fi : keyInfo) {
        if (SimpleType.tbool.equals(fi.javaType)) {
          comm.print("#{" + fi.name + "Int}, ");
        } else {
          comm.print("#{" + fi.name + "}, ");
        }
      }
    }
    comm.println("moment())}\")");
    comm.println("  void ins" + firstUpper(field.name) + "WithNow(" + comm._(java.name()) + " "
        + field.table.name + ");");
  }
  
  private void insField(ClassOuter comm, Field field, ClassOuter java, List<FieldInfo> all) {
    String callAnn = libType == LibType.MYBATIS ? comm._(MYBATIS_UPDATE) :comm._(GBATIS_CALL);
    
    if (libType == LibType.MYBATIS) {
      comm.println("  @" + comm._(MYBATIS_OPTIONS) + "(statementType = "
          + comm._(MYBATIS_StatementType) + ".CALLABLE)");
    }
    
    comm.print("  @" + callAnn + "(\"{call " + conf._p_ + field.table.name + "_" + field.name);
    
    comm.print(" (");
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
    comm.println(")}\")");
    comm.println("  void ins" + firstUpper(field.name) + "(" + comm._(java.name()) + " "
        + field.table.name + ");");
  }
  
  private void generateDaoTableLoadAt(ClassOuter comm, Table table, ClassOuter java,
      ClassOuter fieldsClass) {
    String prmAnn = libType == LibType.MYBATIS ? comm._(MYBATIS_PARAM) :comm._(GBATIS_PRM);
    String seleAnn = libType == LibType.MYBATIS ? comm._(MYBATIS_SELECT) :comm._(GBATIS_SELE);
    
    StringBuilder sb = new StringBuilder();
    viewFormer.formTableSelect(sb, table, "tts.ts", 0, 0);
    
    List<FieldInfo> keyInfo = table.keyInfo();
    {
      comm.print("  @" + seleAnn + "(\"with tts as (select #{ts} ts from dual), xx as (" + sb
          + ") select * from xx");
      {
        boolean first = true;
        for (FieldInfo fi : keyInfo) {
          comm.print(first ? " where " :" and ");
          first = false;
          comm.print("xx." + fi.name + " = #{" + fi.name + "}");
        }
      }
      comm.println("\")");
      
      comm.print("  " + comm._(java.name()) + " loadAt(@" + prmAnn + "(\"" + conf.ts + "\")"
          + comm._(DATE) + " " + conf.ts);
      for (FieldInfo fi : keyInfo) {
        comm.print(", @" + prmAnn + "(\"" + fi.name + "\")");
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
      
      comm.print("  @" + seleAnn + "(\"with tts as (select #{ts} ts from dual), xx as (" + sb
          + ") select * from xx");
      {
        boolean first = true;
        for (FieldInfo fi : keyInfo) {
          comm.print(first ? " where " :" and ");
          first = false;
          comm.print("xx." + fi.name + " = #{" + fi.name + "}");
        }
      }
      comm.println("\")");
      
      comm.print("  " + comm._(LIST) + "<" + comm._(java.name()) + "> loadListAt(@" + prmAnn
          + "(\"" + conf.ts + "\")" + comm._(DATE) + " " + conf.ts);
      for (FieldInfo fi : keyInfo) {
        comm.print(", @" + prmAnn + "(\"" + fi.name + "\")");
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
    ClassOuter t = new ClassOuter(conf.daoPackage + ".i", "", "T");
    ClassOuter v = new ClassOuter(conf.daoPackage + ".i", "", "V");
    t.println("public interface " + t.className + " {");
    v.println("public interface " + v.className + " {");
    
    for (Table table : sg.stru.tables.values()) {
      t.println("  String " + table.name + " = \"" + conf.tabPrefix + table.name + "\";");
      v.println("  String " + table.name + " = \"" + conf.vPrefix + table.name + "\";");
      for (Field field : table.fields) {
        t.println("  String " + table.name + "_" + field.name + " = \"" + conf.tabPrefix
            + table.name + "_" + field.name + "\";");
        v.println("  String " + table.name + "_" + field.name + " = \"" + conf.vPrefix + table.name
            + "_" + field.name + "\";");
      }
      t.println();
      v.println();
    }
    
    t.generateTo(conf.javaGenDir);
    v.generateTo(conf.javaGenDir);
  }
  
  private void generateVT() {
    ClassOuter vt = new ClassOuter(conf.daoPackage + ".i", "", "VT");
    vt.println("public interface " + vt.className + " {");
    
    for (Table table : sg.stru.tables.values()) {
      StringBuilder tab = new StringBuilder();
      viewFormer.formTableSelect(tab, table, "tts.ts", 0, 0);
      vt.println("  String " + table.name + " = \"" + tab + "\";");
      for (Field field : table.fields) {
        StringBuilder fi = new StringBuilder();
        viewFormer.formFieldSelect(fi, field, "tts.ts", 0, 0);
        vt.println("  String " + table.name + "_" + field.name + " = \"" + fi + "\";");
      }
      vt.println();
    }
    
    vt.generateTo(conf.javaGenDir);
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
    String seleAnn = libType == LibType.MYBATIS ? comm._(MYBATIS_SELECT) :comm._(GBATIS_SELE);
    
    comm.println("@" + seleAnn + "(\"select * from " + conf.vPrefix + table.name + " "
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
