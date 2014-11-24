package kz.greetgo.teamcity.soundir.probes;

import java.util.List;
import java.util.Map;

import kz.greetgo.teamcity.soundir.configs.BuildTypeEmployeeMessage;
import kz.greetgo.teamcity.soundir.configs.FromDb;

public class ProbeFromDb {
  public static void main(String[] args) {
    Map<String, List<BuildTypeEmployeeMessage>> map = FromDb.getAssignedMessageMap();
    for (List<BuildTypeEmployeeMessage> list : map.values()) {
      for (BuildTypeEmployeeMessage x : list) {
        System.out.println(x);
      }
    }
  }
}
