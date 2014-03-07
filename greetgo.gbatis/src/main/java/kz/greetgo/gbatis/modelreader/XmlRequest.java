package kz.greetgo.gbatis.modelreader;

import java.util.ArrayList;
import java.util.List;

import kz.greetgo.gbatis.model.RequestType;
import kz.greetgo.gbatis.model.WithView;

public class XmlRequest {
  public String id;
  public RequestType type;
  public final List<WithView> withViewList = new ArrayList<>();
  
  public String sql;
  
  @Override
  public String toString() {
    return "XmlRequest " + id + " " + type + " [" + sql + "] " + withViewList;
  }
}
