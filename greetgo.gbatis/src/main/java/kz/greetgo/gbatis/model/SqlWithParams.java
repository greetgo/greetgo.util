package kz.greetgo.gbatis.model;

import java.util.ArrayList;
import java.util.List;

public class SqlWithParams {
  public RequestType type;
  public String sql;
  public final List<Object> params = new ArrayList<>();
}
