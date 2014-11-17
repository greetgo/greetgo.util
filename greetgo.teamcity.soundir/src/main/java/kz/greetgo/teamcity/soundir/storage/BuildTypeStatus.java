package kz.greetgo.teamcity.soundir.storage;

import java.util.Date;

import kz.greetgo.teamcity.soundir.configs.BuildType;
import kz.greetgo.teamcity.soundir.teamcity.model.Status;

public class BuildTypeStatus {
  BuildType buildType;
  Status status;
  int number;
  
  Date lastChange;
  Date lastPlay;
}
