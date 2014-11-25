package kz.greetgo.teamcity.soundir.teamcity.rhs;

import kz.greetgo.teamcity.soundir.teamcity.model.ProjectInfo;

import org.xml.sax.Attributes;

public class GetProjectInfo extends ResultHandler<ProjectInfo> {
  
  private final String id;
  
  public GetProjectInfo(String id) {
    this.id = id;
  }
  
  private final ProjectInfo result = new ProjectInfo();
  
  @Override
  public ProjectInfo result() {
    return result;
  }
  
  @Override
  public String ref() {
    return "/httpAuth/app/rest/projects/id:" + id;
  }
  
  private boolean noProject = true;
  private boolean noParentProject = true;
  
  @Override
  protected void startTag(String name, Attributes atts) throws Exception {
    if (noProject && "project".equals(name)) {
      noProject = false;
      
      result.id = atts.getValue("id");
      result.name = atts.getValue("name");
      result.webLink = atts.getValue("webUrl");
      
      return;
    }
    if (noParentProject && "parentProject".equals(name)) {
      noParentProject = false;
      
      result.parentId = atts.getValue("id");
      
      return;
    }
  }
  
  @Override
  protected void endTag(String name) throws Exception {}
}
