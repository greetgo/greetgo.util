package kz.greetgo.teamcity.soundir.teamcity.model;

public class LastStatus {
  public final String buildTypeId;
  public final Status status;
  public final int number;
  
  public LastStatus(String buildTypeId, String status, int number) {
    this.buildTypeId = buildTypeId;
    this.status = Status.fromStr(status);
    this.number = number;
  }
  
  @Override
  public String toString() {
    return "Build type id: " + buildTypeId + ", number: " + number + ", last status: " + status;
  }
}
