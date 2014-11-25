package kz.greetgo.teamcity.soundir.teamcity.rhs;

import kz.greetgo.teamcity.soundir.teamcity.model.BuildInfo;
import kz.greetgo.teamcity.soundir.teamcity.model.Change;
import kz.greetgo.teamcity.soundir.teamcity.model.Status;

import org.xml.sax.Attributes;

public class GetBuildInfo extends ResultHandler<BuildInfo> {
  private final String id;
  
  public GetBuildInfo(String id) {
    this.id = id;
  }
  
  private final BuildInfo result = new BuildInfo();
  
  @Override
  public BuildInfo result() {
    return result;
  }
  
  @Override
  public String ref() {
    return "/httpAuth/app/rest/builds/id:" + id;
  }
  
  boolean inLastChanges = false;
  
  @Override
  protected void startTag(String name, Attributes atts) throws Exception {
    if ("lastChanges".equals(name)) {
      inLastChanges = true;
      return;
    }
    if (inLastChanges && "change".equals(name)) {
      Change c = new Change();
      c.id = atts.getValue("id");
      c.username = atts.getValue("username");
      c.happenedAt = UtilRhs.strToDate(atts.getValue("date"));
      c.webLink = atts.getValue("webLink");
      c.version = atts.getValue("version");
      result.lastChanges.add(c);
      return;
    }
    
    if (inLastChanges) return;
    
    if ("build".equals(name)) {
      result.id = atts.getValue("id");
      result.buildType = atts.getValue("buildTypeId");
      result.number = Long.parseLong(atts.getValue("number"));
      result.status = Status.fromStr(atts.getValue("status"));
      result.webLink = atts.getValue("webUrl");
      return;
    }
    
    if ("buildType".equals(name)) {
      result.buildTypeName = atts.getValue("name");
      result.projectId = atts.getValue("projectId");
      return;
    }
  }
  
  @Override
  protected void endTag(String name) throws Exception {
    endTagInner(name);
    
    text = null;
  }
  
  private void endTagInner(String name) {
    if ("lastChanges".equals(name)) {
      inLastChanges = false;
      return;
    }
    
    if (inLastChanges) return;
    
    if ("statusText".equals(name)) {
      result.statusText = text().trim();
      return;
    }
    
    if ("queuedDate".equals(name)) {
      result.queuedDate = UtilRhs.strToDate(text().trim());
      return;
    }
    if ("startDate".equals(name)) {
      result.startDate = UtilRhs.strToDate(text().trim());
      return;
    }
    if ("finishDate".equals(name)) {
      result.finishDate = UtilRhs.strToDate(text().trim());
      return;
    }
  }
}
