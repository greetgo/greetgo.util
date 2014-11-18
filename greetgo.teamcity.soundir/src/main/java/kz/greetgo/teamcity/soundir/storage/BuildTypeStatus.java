package kz.greetgo.teamcity.soundir.storage;

import java.util.Date;

import kz.greetgo.teamcity.soundir.configs.BuildType;
import kz.greetgo.teamcity.soundir.teamcity.model.Status;

public class BuildTypeStatus {
  public BuildType buildType;
  public Status status;
  public int number;
  
  public Date lastChange;
  public Date lastPlay;
}
