package kz.greetgo.gbatis.model;

import java.util.ArrayList;
import java.util.List;

public class Request {
  public String sql;
  public RequestType type;
  public final List<WithView> withList = new ArrayList<>();
  public final List<Param> paramList = new ArrayList<>();
  
  public ResultType resultType;
  public Class<?> resultDataClass;
  public boolean callNow;
  public String mapKeyField;
  public Class<?> mapKeyClass;
}
