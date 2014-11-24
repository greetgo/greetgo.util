package kz.greetgo.teamcity.soundir.storage;

import java.util.Map;

public interface Storage {
  Map<String, BuildTypeStatus> loadAll();
  
  BuildTypeStatus load(String buildType);
  
  BuildTypeStatus save(BuildTypeStatus buildTypeStatus);
}
