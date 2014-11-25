package kz.greetgo.teamcity.soundir.teamcity.model;

import java.util.Date;

public class Change {
  public String id, username;
  public Date happenedAt;
  public String webLink;
  public String version;
  
  @Override
  public String toString() {
    return "Change [id=" + id + ", username=" + username + ", happenedAt=" + happenedAt
        + ", webLink=" + webLink + "]";
  }
}
