package kz.greetgo.teamcity.soundir.teamcity.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BuildInfo {
  public String buildType, id;
  public String buildTypeName;
  public long number;
  public Status status;
  
  public String webLink;
  
  public String statusText;
  
  public Date queuedDate, startDate, finishDate;
  
  public final List<Change> lastChanges = new ArrayList<>();
  
  public String projectId;
  public ProjectInfo projectInfo;
  
  public String getLastUsername(String defValue) {
    if (lastChanges.size() == 0) return defValue;
    return lastChanges.get(0).username;
  }
  
  @Override
  public String toString() {
    return "BuildInfo [id=" + id + ", number=" + number + ", status=" + status + ", webLink="
        + webLink + ", statusText=" + statusText + ", queuedDate=" + queuedDate + ", startDate="
        + startDate + ", finishDate=" + finishDate + ", projectId=" + projectId + "]";
  }
  
  public String getProjectName(String defValue) {
    if (projectInfo == null) return defValue;
    if (projectInfo.parent == null) return projectInfo.name;
    return projectInfo.parent.name + " -> " + projectInfo.name;
  }
  
}
