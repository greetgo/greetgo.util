package kz.greetgo.sqlmanager.gen;

import java.io.PrintStream;

import kz.greetgo.sqlmanager.model.Field;
import kz.greetgo.sqlmanager.model.FieldInfo;
import kz.greetgo.sqlmanager.model.Table;
import kz.greetgo.sqlmanager.parser.StruGenerator;

public class Nf6GeneratorOracle extends Nf6Generator {
  
  private SqlDialect sqlDialect = null;
  
  @Override
  protected SqlDialect sqld() {
    if (sqlDialect == null) sqlDialect = new SqlDialectOracle();
    return sqlDialect;
  }
  
  public Nf6GeneratorOracle(Conf conf, StruGenerator sg) {
    super(conf, sg);
  }
  
  @Override
  protected void printTableInsertFunction(PrintStream out, Table table) {
    String fname = conf._p_ + table.name;
    out.print("create or replace procedure " + fname + "(");
    
    {
      boolean first = true;
      for (Field key : table.keys) {
        for (FieldInfo fi : key.fieldInfo()) {
          out.print(first ? "" :", ");
          first = false;
          out.print(fi.name + "__ " + sqld().procType(fi.stype));
        }
      }
    }
    
    out.println(") is begin");
    
    {
      out.print("  insert into " + conf.tabPrefix + table.name + " (");
      {
        boolean first = true;
        for (Field key : table.keys) {
          for (FieldInfo fi : key.fieldInfo()) {
            out.print(first ? "" :", ");
            first = false;
            out.print(fi.name);
          }
        }
      }
      out.print(") values (");
      {
        boolean first = true;
        for (Field key : table.keys) {
          for (FieldInfo fi : key.fieldInfo()) {
            out.print(first ? "" :", ");
            first = false;
            out.print(fi.name + "__");
          }
        }
      }
      out.println(") ; ");
    }
    
    out.println("exception when others then null ; ");
    out.println("end ; ");
    
    out.println(conf.separator);
  }
  
  @Override
  protected void printFieldInsertFunction(PrintStream out, Field field) {
    
    String fname = conf._p_ + field.table.name + '_' + field.name;
    out.print("create or replace procedure " + fname + "(");
    
    {
      boolean first = true;
      for (Field key : field.table.keys) {
        for (FieldInfo fi : key.fieldInfo()) {
          out.print(first ? "" :", ");
          first = false;
          out.print(fi.name + "__ " + sqld().procType(fi.stype));
        }
      }
      for (FieldInfo fi : field.fieldInfo()) {
        out.print(", " + fi.name + "__ " + sqld().procType(fi.stype));
      }
    }
    
    out.println(") is");
    
    String tname = conf.tabPrefix + field.table.name + "_" + field.name;
    
    out.println("  r " + tname + "%rowtype ; ");
    out.println("  doit int ; ");
    out.println("begin");
    
    out.println("  select case when exists (select 1 from " + tname);
    {
      boolean first = true;
      for (Field key : field.table.keys) {
        for (FieldInfo fi : key.fieldInfo()) {
          out.print(first ? "    where " :"    and ");
          out.println(fi.name + " = " + fi.name + "__");
          first = false;
        }
      }
    }
    out.println("    ) then 0 else 1 end into doit from dual ; ");
    
    out.println();
    out.println("  if doit = 0 then ");
    out.println();
    
    out.println("  select * into r");
    out.println("    from " + tname);
    {
      boolean first = true;
      for (Field key : field.table.keys) {
        for (FieldInfo fi : key.fieldInfo()) {
          out.print(first ? "    where " :"    and ");
          out.println(fi.name + " = " + fi.name + "__");
          first = false;
        }
      }
    }
    out.println("    and " + conf.ts + " = (select max(" + conf.ts + ") ");
    out.println("      from " + tname);
    {
      boolean first = true;
      for (Field key : field.table.keys) {
        for (FieldInfo fi : key.fieldInfo()) {
          out.print(first ? "      where " :"      and ");
          out.println(fi.name + " = " + fi.name + "__");
          first = false;
        }
      }
    }
    out.println("      ) ; ");
    out.println();
    
    for (FieldInfo fi : field.fieldInfo()) {
      out.println("  if doit = 0 and " + fi.name + "__ is null and r." + fi.name
          + " is not null then doit := 1 ; end if ; ");
      out.println("  if doit = 0 and " + fi.name + "__ is not null and r." + fi.name
          + " is null then doit := 1 ; end if ; ");
      out.println("  if doit = 0 and " + fi.name + "__ is not null and r." + fi.name
          + " is not null and " + fi.name + "__ != r." + fi.name + " then doit := 1 ; end if ; ");
      out.println();
    }
    
    out.println("  end if ; ");
    out.println();
    
    out.println("  if doit = 1 then ");
    out.print("    insert into " + tname + " (");
    {
      boolean first = true;
      for (Field key : field.table.keys) {
        for (FieldInfo fi : key.fieldInfo()) {
          out.print(first ? "" :", ");
          first = false;
          out.print(fi.name);
        }
      }
      for (FieldInfo fi : field.fieldInfo()) {
        out.print(", " + fi.name);
      }
    }
    out.print(") values (");
    {
      boolean first = true;
      for (Field key : field.table.keys) {
        for (FieldInfo fi : key.fieldInfo()) {
          out.print(first ? "" :", ");
          first = false;
          out.print(fi.name + "__");
        }
      }
      for (FieldInfo fi : field.fieldInfo()) {
        out.print(", " + fi.name + "__");
      }
    }
    out.println(") ; ");
    out.println("  end if ; ");
    
    out.println("end ; ");
    out.println(conf.separator);
  }
  
  @Override
  protected void printPrepareSqls(PrintStream out) {
    out.println("create or replace function moment return timestamp");
    out.println("is begin return systimestamp ; end ; ");
    out.println(conf.separator);
    out.println();
  }
}
