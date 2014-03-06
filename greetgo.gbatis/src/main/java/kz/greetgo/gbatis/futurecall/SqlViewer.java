package kz.greetgo.gbatis.futurecall;

import java.util.List;

public interface SqlViewer {
  boolean needView();
  
  void view(String sql, List<Object> params);
}
