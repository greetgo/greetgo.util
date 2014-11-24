package kz.greetgo.teamcity.soundir.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kz.greetgo.teamcity.soundir.configs.BuildTypeEmployeeMessage;
import kz.greetgo.teamcity.soundir.configs.FromDb;
import kz.greetgo.teamcity.soundir.player.Play;

public class SoundSender implements Runnable {
  private final String buildType;
  private Finisher finisher;
  private final Thread myThread = new Thread(this);
  
  private static final CompSyncronizer COMP_SYNCRONIZER = new CompSyncronizer();
  
  public static SoundSender around(String buildType) {
    return new SoundSender(buildType);
  }
  
  public Joiner go() {
    myThread.start();
    return joiner;
  }
  
  public final Joiner joiner = new Joiner() {
    @Override
    public void join() {
      try {
        myThread.join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  };
  
  private SoundSender(String buildType) {
    this.buildType = buildType;
  }
  
  public SoundSender with(Finisher finisher) {
    this.finisher = finisher;
    return this;
  }
  
  private class PlayRun implements Runnable {
    private final BuildTypeEmployeeMessage btem;
    
    public PlayRun(BuildTypeEmployeeMessage btem) {
      this.btem = btem;
    }
    
    @Override
    public void run() {
      synchronized (COMP_SYNCRONIZER.getSyncerFor(btem.employee)) {
        Play.message(btem.message).to(btem.employee);
      }
    }
  }
  
  private Map<String, List<BuildTypeEmployeeMessage>> messageMap = null;
  
  private Map<String, List<BuildTypeEmployeeMessage>> getMessageMap() {
    if (messageMap == null) {
      messageMap = FromDb.getAssignedMessageMap();
    }
    return messageMap;
  }
  
  @Override
  public void run() {
    
    List<BuildTypeEmployeeMessage> list = getMessageMap().get(buildType);
    if (list == null) return;
    
    List<Thread> threads = new ArrayList<>();
    
    for (BuildTypeEmployeeMessage btem : list) {
      threads.add(new Thread(new PlayRun(btem)));
    }
    
    for (Thread t : threads) {
      t.start();
    }
    
    for (Thread t : threads) {
      try {
        t.join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    
    if (finisher != null) finisher.finish(buildType);
  }
}
