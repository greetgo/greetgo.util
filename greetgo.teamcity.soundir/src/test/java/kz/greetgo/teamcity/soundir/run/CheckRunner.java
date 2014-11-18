package kz.greetgo.teamcity.soundir.run;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kz.greetgo.teamcity.soundir.configs.BuildType;
import kz.greetgo.teamcity.soundir.controller.Finisher;
import kz.greetgo.teamcity.soundir.controller.Joiner;
import kz.greetgo.teamcity.soundir.controller.SoundSenter;
import kz.greetgo.teamcity.soundir.storage.BuildTypeStatus;
import kz.greetgo.teamcity.soundir.storage.Storage;
import kz.greetgo.teamcity.soundir.storage.StorageDir;
import kz.greetgo.teamcity.soundir.teamcity.Api;
import kz.greetgo.teamcity.soundir.teamcity.model.LastStatus;
import kz.greetgo.teamcity.soundir.teamcity.model.Status;
import kz.greetgo.teamcity.soundir.teamcity.rhs.BuildTypeIdList;
import kz.greetgo.teamcity.soundir.teamcity.rhs.LastBuildStatus;

import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CheckRunner {
  
  private Storage stor;
  
  @BeforeTest
  public void prepare() {
    Api.prepareBasicAuthenticator("root", "111");
    Api.protocol = "http";
    Api.host = "192.168.0.102";
    Api.port = 8111;
    
    stor = StorageDir.defaultSD();
  }
  
  private Map<BuildType, BuildTypeStatus> requestTeamcity() {
    Map<BuildType, BuildTypeStatus> ret = new HashMap<>();
    
    for (String buildTypeId : Api.get(new BuildTypeIdList())) {
      LastStatus ls = Api.get(new LastBuildStatus(buildTypeId));
      BuildTypeStatus bts = ls.toBTS();
      if (bts != null) ret.put(bts.buildType, bts);
    }
    
    return ret;
  }
  
  @Test
  public void check() throws Exception {
    
    File file = new File("data/running.lock");
    
    file.getParentFile().mkdirs();
    
    RandomAccessFile raFile = new RandomAccessFile(file, "rw");
    try {
      FileChannel fileChannel = raFile.getChannel();
      try {
        FileLock lock = fileChannel.tryLock();
        if (lock == null) throw new SkipException("The file " + file + " is already locked");
        try {
          checkReal();
        } finally {
          lock.release();
        }
        
      } finally {
        fileChannel.close();
      }
    } finally {
      raFile.close();
    }
    
  }
  
  private void checkReal() {
    final List<Joiner> joiners = new LinkedList<>();
    
    Map<BuildType, BuildTypeStatus> savedMap = stor.loadAll();
    
    for (BuildTypeStatus actual : requestTeamcity().values()) {
      BuildTypeStatus saved = savedMap.get(actual.buildType);
      if (actual.status == Status.SUCCESS) {
        if (saved == null) {
          actual.lastChange = new Date();
          stor.save(actual);
          continue;
        }
        
        if (saved.number == actual.number) continue;
        
        actual.lastChange = new Date();
        stor.save(actual);
        continue;
      }
      
      if (saved == null) {
        actual.lastChange = new Date();
        stor.save(actual);
        play(actual.buildType, joiners);
        continue;
      }
      
      if (saved.number != actual.number) {
        actual.lastChange = new Date();
        stor.save(actual);
        play(actual.buildType, joiners);
        continue;
      }
      
      if (tooLongTimeAgo(saved.lastPlay)) {
        play(saved.buildType, joiners);
        continue;
      }
      
      //default: nothing to do
    }
    
    for (Joiner joiner : joiners) {
      joiner.join();
    }
  }
  
  private boolean tooLongTimeAgo(Date lastPlay) {
    if (lastPlay == null) return true;
    Calendar cal = new GregorianCalendar();
    cal.setTime(lastPlay);
    cal.add(Calendar.HOUR_OF_DAY, 1);
    
    return new Date().after(cal.getTime());
  }
  
  private final Finisher playDateSaver = new Finisher() {
    @Override
    public void finish(BuildType buildType) {
      System.out.println("Finish " + buildType);
      BuildTypeStatus bts = stor.load(buildType);
      bts.lastPlay = new Date();
      stor.save(bts);
    }
  };
  
  private void play(BuildType buildType, List<Joiner> joinerList) {
    joinerList.add(SoundSenter.around(buildType).with(playDateSaver).go());
  }
  
}
