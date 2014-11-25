package kz.greetgo.teamcity.soundir.teamcity.rhs;

import java.util.ArrayList;
import java.util.List;

import kz.greetgo.teamcity.soundir.teamcity.model.BuildStatus;
import kz.greetgo.teamcity.soundir.teamcity.model.Status;

import org.xml.sax.Attributes;

public class BuildStatusList extends ResultHandler<List<BuildStatus>> {
  
  private final String buildType;
  
  public BuildStatusList(String buildType) {
    this.buildType = buildType;
  }
  
  private final List<BuildStatus> result = new ArrayList<>();
  
  @Override
  public List<BuildStatus> result() {
    return result;
  }
  
  @Override
  public String ref() {
    return "/httpAuth/app/rest/buildTypes/id:" + buildType + "/builds";
  }
  
  @Override
  protected void startTag(String name, Attributes atts) throws Exception {
    if ("build".equals(name)) {
      BuildStatus x = new BuildStatus();
      x.buildType = buildType;
      x.id = atts.getValue("id");
      x.status = Status.fromStr(atts.getValue("status"));
      x.number = Long.parseLong(atts.getValue("number"));
      result.add(x);
    }
  }
  
  @Override
  protected void endTag(String name) throws Exception {}
  
}
