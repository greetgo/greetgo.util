package kz.greetgo.teamcity.soundir.storage;

import java.util.Map;

import kz.greetgo.teamcity.soundir.configs.BuildType;

public interface Storage {
  Map<BuildType, BuildTypeStatus> loadAll();
  
  BuildTypeStatus load(BuildType buildType);
  
  BuildTypeStatus save(BuildTypeStatus buildTypeStatus);
}
