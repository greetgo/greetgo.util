package kz.greetgo.teamcity.soundir.teamcity.rhs;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

public class BuildTypeIdList extends ResultHandler<List<String>> {
  
  private final String projectId;
  
  public BuildTypeIdList(String projectId) {
    this.projectId = projectId;
  }
  
  public BuildTypeIdList() {
    this.projectId = null;
  }
  
  @Override
  public String ref() {
    if (projectId == null) return "/httpAuth/app/rest/buildTypes";
    return "/httpAuth/app/rest/projects/id:" + projectId;
  }
  
  private final List<String> result = new ArrayList<>();
  
  @Override
  public List<String> result() {
    return result;
  }
  
  @Override
  protected void startTag(String name, Attributes atts) throws Exception {
    if ("buildType".equals(name)) {
      result.add(atts.getValue("id"));
    }
  }
  
  @Override
  protected void endTag(String name) throws Exception {}
}
