package kz.greetgo.teamcity.soundir.teamcity.rhs;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

public class ProjectIdList extends ResultHandler<List<String>> {
  
  @Override
  public String ref() {
    return "/httpAuth/app/rest/projects";
  }
  
  private final List<String> result = new ArrayList<>();
  
  @Override
  public List<String> result() {
    return result;
  }
  
  @Override
  protected void startTag(String name, Attributes atts) throws Exception {
    if ("project".equals(name)) {
      result.add(atts.getValue("id"));
    }
  }
  
  @Override
  protected void endTag(String name) throws Exception {}
}
