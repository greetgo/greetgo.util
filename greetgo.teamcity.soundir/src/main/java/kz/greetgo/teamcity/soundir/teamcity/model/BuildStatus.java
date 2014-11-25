package kz.greetgo.teamcity.soundir.teamcity.model;

public class BuildStatus {
  public String buildType, id;
  public Status status;
  public long number;
  
  @Override
  public String toString() {
    return "BuildStatus [id=" + id + ", status=" + status + ", number=" + number + "]";
  }
  
}
