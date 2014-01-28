package kz.greetgo.sqlmanager.gen;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import kz.greetgo.sqlmanager.model.Field;
import kz.greetgo.sqlmanager.model.SimpleType;
import kz.greetgo.sqlmanager.model.Table;
import kz.greetgo.sqlmanager.parser.StruGenerator;

public class Nf6GeneratorOracle extends Nf6Generator {
  
  private SqlDialect sqlDialect = null;
  
  @Override
  protected SqlDialect sqld() {
    if (sqlDialect == null) sqlDialect = new SqlDialectOracle();
    return sqlDialect;
  }
  
  public Nf6GeneratorOracle(StruGenerator sg) {
    super(sg);
  }
  
  @Override
  protected void printTableInsertFunction(PrintStream out, Table table) {
    String fname = conf._ins_ + table.name;
    out.println("create or replace function " + fname + "()");
    out.println("returns trigger as $" + conf.bigQuote + "$");
    out.println("begin");
    
    out.println("  if exists (");
    
    out.println("      select 1 from " + conf.tabPrefix + table.name);
    
    {
      boolean first = true;
      for (Field key : table.keys) {
        String k = first ? "where" :"and";
        first = false;
        out.println("      " + k + " " + key.name + " = new." + key.name);
      }
    }
    
    out.println("    )");
    out.println("    then return old ;");
    out.println("    else return new ;");
    out.println("  end if ;");
    
    out.println("end");
    out.println("$" + conf.bigQuote + "$ language plpgsql");
    
    out.println(conf.separator);
    
    out.println("drop trigger if exists " + fname + "_trigger on " + conf.tabPrefix + table.name
        + conf.separator);
    out.println("create trigger " + fname + "_trigger");
    out.println("  before insert on " + conf.tabPrefix + table.name);
    out.println("  for each row");
    out.println("  execute procedure " + fname + "()" + conf.separator);
  }
  
  @Override
  protected void printFieldInsertFunction(PrintStream out, Field field) {
    String fname = conf._ins_ + field.table.name + '_' + field.name;
    out.println("create or replace function " + fname + "()");
    out.println("returns trigger as $" + conf.bigQuote + "$");
    out.println("declare r record ;");
    
    List<String> keys = new ArrayList<>();
    
    for (Field fld : field.table.keys) {
      List<SimpleType> ftypes = new ArrayList<>();
      fld.type.assignSimpleTypes(ftypes);
      if (ftypes.size() == 1) {
        keys.add(fld.name);
      } else
        for (int i = 1, C = ftypes.size(); i <= C; i++) {
          keys.add(fld.name + 1);
        }
    }
    
    List<SimpleType> types = new ArrayList<>();
    field.type.assignSimpleTypes(types);
    
    List<String> refs = new ArrayList<>();
    if (types.size() == 1) {
      refs.add(field.name);
    } else
      for (int i = 1, C = types.size(); i <= C; i++) {
        refs.add(field.name + i);
      }
    
    out.println("begin");
    
    String t = conf.tabPrefix + field.table.name + "_" + field.name;
    
    out.println("  select * into r");
    out.println("    from " + t);
    {
      boolean first = true;
      for (String key : keys) {
        String s = first ? "where " :"and ";
        first = false;
        out.println("    " + s + key + " = new." + key);
      }
    }
    out.println("    and " + conf.ts + " = (select max(" + conf.ts + ")");
    out.println("      from " + t);
    {
      boolean first = true;
      for (String key : keys) {
        String s = first ? "where " :"and ";
        first = false;
        out.println("      " + s + key + " = new." + key);
      }
    }
    out.println("      );");
    out.println("  ");
    
    for (String ref : refs) {
      out.println("  if new." + ref + " is null and r." + ref
          + " is not null then return new ; end if ;");
      out.println("  if new." + ref + " is not null and r." + ref
          + " is null then return new ; end if ;");
      out.println("  if new." + ref + " is not null and r." + ref + " is not null and new." + ref
          + " != r." + ref + " then return new ; end if ;");
      out.println("  ");
    }
    
    out.println("  return old ;");
    out.println("  ");
    
    out.println("end");
    out.println("$" + conf.bigQuote + "$ language plpgsql");
    out.println(conf.separator);
    
    out.println("drop trigger if exists " + fname + "_trigger on " + t + conf.separator);
    out.println("create trigger " + fname + "_trigger");
    out.println("  before insert on " + t);
    out.println("  for each row");
    out.println("  execute procedure " + fname + "()" + conf.separator);
  }
  
  @Override
  protected void printPrepareSqls(PrintStream out) {}
}
