package kz.greetgo.teamcity.soundir.controller;

import java.util.HashMap;
import java.util.Map;

import kz.greetgo.teamcity.soundir.configs.Comp;

public class CompSyncronizer {
  
  private final Map<Comp, Object> syncerMap = new HashMap<>();
  
  public synchronized Object getSyncerFor(Comp comp) {
    Object syncer = syncerMap.get(comp);
    if (syncer == null) {
      syncer = new Object();
      syncerMap.put(comp, syncer);
    }
    return syncer;
  }
}
