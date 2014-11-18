package kz.greetgo.teamcity.soundir.controller;

import static kz.greetgo.teamcity.soundir.configs.BuildTypeMessages.getMessageFor;

import java.util.ArrayList;
import java.util.List;

import kz.greetgo.teamcity.soundir.configs.BuildType;
import kz.greetgo.teamcity.soundir.configs.BuildTypeComp;
import kz.greetgo.teamcity.soundir.configs.Comp;
import kz.greetgo.teamcity.soundir.player.Play;

public class SoundSenter implements Runnable {
  private final BuildType buildType;
  private Finisher finisher;
  private final Thread myThread = new Thread(this);
  
  private static final CompSyncronizer COMP_SYNCRONIZER = new CompSyncronizer();
  
  public static SoundSenter around(BuildType buildType) {
    return new SoundSenter(buildType);
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
  
  private SoundSenter(BuildType buildType) {
    this.buildType = buildType;
  }
  
  public SoundSenter with(Finisher finisher) {
    this.finisher = finisher;
    return this;
  }
  
  private class PlayRun implements Runnable {
    private final Comp comp;
    
    public PlayRun(Comp comp) {
      this.comp = comp;
    }
    
    @Override
    public void run() {
      synchronized (COMP_SYNCRONIZER.getSyncerFor(comp)) {
        Play.message(getMessageFor(buildType).text).to(comp.name());
      }
    }
  }
  
  @Override
  public void run() {
    List<Thread> threads = new ArrayList<>();
    for (Comp comp : BuildTypeComp.data.get(buildType)) {
      threads.add(new Thread(new PlayRun(comp)));
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
