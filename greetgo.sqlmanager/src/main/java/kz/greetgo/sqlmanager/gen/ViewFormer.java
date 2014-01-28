package kz.greetgo.sqlmanager.gen;

import kz.greetgo.sqlmanager.model.Field;
import kz.greetgo.sqlmanager.model.Table;

public interface ViewFormer {
  void formFieldSelect(StringBuilder sb, Field field, String time, int tabSize, int orig);
  
  void formTableSelect(StringBuilder sb, Table table, String time, int tabSize, int orig);
}
