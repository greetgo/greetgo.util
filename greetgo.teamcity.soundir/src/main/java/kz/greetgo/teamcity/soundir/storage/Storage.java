package kz.greetgo.teamcity.soundir.storage;

import java.util.List;

import kz.greetgo.teamcity.soundir.configs.BuildType;

public interface Storage {
  List<BuildTypeStatus> loadAll();
  
  BuildTypeStatus load(BuildType buildType);
  
  BuildTypeStatus save(BuildTypeStatus buildTypeStatus);
}
