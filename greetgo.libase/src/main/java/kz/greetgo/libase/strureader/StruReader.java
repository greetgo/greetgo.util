package kz.greetgo.libase.strureader;

import java.util.Map;

import kz.greetgo.libase.model.DbStru;
import kz.greetgo.libase.model.Field;
import kz.greetgo.libase.model.FieldVector;
import kz.greetgo.libase.model.ForeignKey;
import kz.greetgo.libase.model.Relation;
import kz.greetgo.libase.model.Sequence;
import kz.greetgo.libase.model.StoreFunc;
import kz.greetgo.libase.model.Table;
import kz.greetgo.libase.model.Trigger;
import kz.greetgo.libase.model.View;

public class StruReader {
  public static DbStru read(RowReader rowReader) throws Exception {
    DbStru ret = new DbStru();
    
    for (ColumnRow colRow : rowReader.readAllTableColumns()) {
      Table table = (Table)ret.relations.get(colRow.tableName);
      if (table == null) ret.relations.put(colRow.tableName, table = new Table(colRow.tableName));
      table.allFields.add(new Field(table, colRow.name, colRow.type, colRow.nullable,
          colRow.defaultValue));
      
    }
    
    for (PrimaryKeyRow pkr : rowReader.readAllTablePrimaryKeys().values()) {
      Table table = (Table)ret.relations.get(pkr.tableName);
      if (table == null) throw new NullPointerException("No table " + pkr.tableName);
      for (String fieldName : pkr.keyFieldNames) {
        table.keyFields.add(table.field(fieldName));
      }
    }
    
    for (ForeignKeyRow fkr : rowReader.readAllForeignKeys().values()) {
      Table from = (Table)ret.relations.get(fkr.fromTable);
      if (from == null) throw new NullPointerException("No table " + fkr.fromTable);
      Table to = (Table)ret.relations.get(fkr.toTable);
      if (to == null) throw new NullPointerException("No table " + fkr.toTable);
      
      if (fkr.fromColumns.size() != fkr.toColumns.size()) throw new IllegalArgumentException(
          "Different number of columns " + fkr);
      
      if (fkr.fromColumns.size() <= 0) throw new IllegalArgumentException("No columns " + fkr);
      
      ForeignKey fk = new ForeignKey(from, to);
      
      for (int i = 0, C = fkr.fromColumns.size(); i < C; i++) {
        fk.vectors.add(new FieldVector( //
            from.field(fkr.fromColumns.get(i)), //
            to.field(fkr.toColumns.get(i))));
      }
      
      from.foreignKeys.add(fk);
      ret.foreignKeys.add(fk);
    }
    
    for (SequenceRow s : rowReader.readAllSequences().values()) {
      ret.sequences.add(new Sequence(s.name, s.startFrom));
    }
    
    {
      Map<String, ViewRow> viewRows = rowReader.readAllViews();
      
      for (ViewRow vr : viewRows.values()) {
        if (ret.relations.keySet().contains(vr.name)) {
          throw new IllegalArgumentException("Cannot add view " + vr.name);
        }
        ret.relations.put(vr.name, new View(vr.name, vr.content));
      }
      
      for (ViewRow vr : viewRows.values()) {
        View view = (View)ret.relations.get(vr.name);
        if (view == null) throw new NullPointerException("No view " + vr.name);
        
        for (String depName : vr.dependenses) {
          Relation dep = ret.relations.get(depName);
          if (dep == null) throw new NullPointerException("No relation " + depName);
          view.dependences.add(dep);
        }
      }
    }
    
    {
      for (StoreFuncRow sfr : rowReader.readAllFuncs()) {
        StoreFunc f = new StoreFunc(sfr.name, sfr.argTypes);
        f.argNames.addAll(sfr.argNames);
        f.returns = sfr.returns;
        f.source = sfr.source;
        f.language = sfr.language;
        ret.funcs.put(f, f);
      }
    }
    
    {
      for (TriggerRow tr : rowReader.readAllTriggers().values()) {
        Trigger t = new Trigger(tr.name, tr.tableName);
        t.eventManipulation = tr.eventManipulation;
        t.actionOrientation = tr.actionOrientation;
        t.actionTiming = tr.actionTiming;
        t.actionStatement = tr.actionStatement;
        ret.triggers.put(t, t);
      }
    }
    
    return ret;
  }
}
