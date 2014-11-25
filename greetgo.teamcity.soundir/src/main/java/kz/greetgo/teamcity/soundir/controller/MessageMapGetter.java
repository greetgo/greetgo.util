package kz.greetgo.teamcity.soundir.controller;

import java.util.List;
import java.util.Map;

import kz.greetgo.teamcity.soundir.configs.BuildTypeEmployeeMessage;
import kz.greetgo.teamcity.soundir.configs.FromDb;

public class MessageMapGetter {
  public static final Getter<Map<String, List<BuildTypeEmployeeMessage>>> messageMap = new Getter<Map<String, List<BuildTypeEmployeeMessage>>>() {
    private Map<String, List<BuildTypeEmployeeMessage>> result = null;
    
    @Override
    public Map<String, List<BuildTypeEmployeeMessage>> get() {
      if (result == null) result = FromDb.getAssignedMessageMap();
      return result;
    }
  };
}
