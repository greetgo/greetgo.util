package kz.greetgo.teamcity.soundir.teamcity.rhs;

import kz.greetgo.teamcity.soundir.teamcity.model.LastStatus;

import org.xml.sax.Attributes;

public class LastBuildStatus extends ResultHandler<LastStatus> {
  
  private final String buildTypeId;
  
  public LastBuildStatus(String buildTypeId) {
    this.buildTypeId = buildTypeId;
  }
  
  @Override
  public String ref() {
    return "/httpAuth/app/rest/buildTypes/id:" + buildTypeId + "/builds";
  }
  
  private int number = 0;
  private String status = null;
  
  @Override
  public LastStatus result() {
    return new LastStatus(buildTypeId, status, number);
  }
  
  @Override
  protected void startTag(String name, Attributes atts) throws Exception {
    if ("build".equals(name)) {
      int num = parseInt(atts.getValue("number"));
      if (number < num) {
        number = num;
        status = atts.getValue("status");
      }
      return;
    }
  }
  
  private static int parseInt(String value) {
    if (value == null) return 0;
    value = value.trim();
    if (value.length() == 0) return 0;
    return Integer.parseInt(value);
  }
  
  @Override
  protected void endTag(String name) throws Exception {}
  
}
