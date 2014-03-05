package kz.greetgo.gbatis.model;

import java.util.ArrayList;
import java.util.List;

public class WithView {
  public String table, view;
  public final List<String> fields = new ArrayList<>();
  
  public WithView() {}
  
  public WithView(String table, String view, String... fields) {
    this.table = table;
    this.view = view;
    for (String field : fields) {
      this.fields.add(field);
    }
  }
  
  @Override
  public String toString() {
    return "WithView " + view + " as " + table + " " + fields;
  }
}
