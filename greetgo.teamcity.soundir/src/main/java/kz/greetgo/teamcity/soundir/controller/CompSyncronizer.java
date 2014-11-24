package kz.greetgo.teamcity.soundir.controller;

import java.util.HashMap;
import java.util.Map;

public class CompSyncronizer {
  
  private final Map<String, Object> syncerMap = new HashMap<>();
  
  public synchronized Object getSyncerFor(String comp) {
    Object syncer = syncerMap.get(comp);
    if (syncer == null) {
      syncer = new Object();
      syncerMap.put(comp, syncer);
    }
    return syncer;
  }
}
