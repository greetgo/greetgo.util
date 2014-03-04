package kz.greetgo.gbatis.model;

import java.util.ArrayList;
import java.util.List;

public class WithView {
  public String table, view;
  public final List<String> fields = new ArrayList<>();
  
  @Override
  public String toString() {
    return "WithView " + view + " as " + table + " " + fields;
  }
}
