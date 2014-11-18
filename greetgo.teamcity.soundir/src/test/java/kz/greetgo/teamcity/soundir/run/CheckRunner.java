package kz.greetgo.teamcity.soundir.run;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import kz.greetgo.teamcity.soundir.configs.BuildType;
import kz.greetgo.teamcity.soundir.controller.Finisher;
import kz.greetgo.teamcity.soundir.controller.SoundSenter;
import kz.greetgo.teamcity.soundir.storage.BuildTypeStatus;
import kz.greetgo.teamcity.soundir.storage.Storage;
import kz.greetgo.teamcity.soundir.storage.StorageDir;
import kz.greetgo.teamcity.soundir.teamcity.Api;
import kz.greetgo.teamcity.soundir.teamcity.model.LastStatus;
import kz.greetgo.teamcity.soundir.teamcity.model.Status;
import kz.greetgo.teamcity.soundir.teamcity.rhs.BuildTypeIdList;
import kz.greetgo.teamcity.soundir.teamcity.rhs.LastBuildStatus;

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
        play(actual.buildType);
        continue;
      }
      
      if (saved.number != actual.number) {
        actual.lastChange = new Date();
        stor.save(actual);
        play(actual.buildType);
        continue;
      }
      
      if (tooLongTimeAgo(saved.lastPlay)) {
        play(saved.buildType);
        continue;
      }
      
      //default: nothing to do
    }
  }
  
  private boolean tooLongTimeAgo(Date lastPlay) {
    if (lastPlay == null) return true;
    Calendar cal = new GregorianCalendar();
    cal.setTime(lastPlay);
    cal.add(Calendar.HOUR_OF_DAY, 1);
    
    return new Date().after(cal.getTime());
  }
  
  private void play(BuildType buildType) {
    SoundSenter.around(buildType).with(new PlayDateSaver()).go();
  }
  
  private final class PlayDateSaver implements Finisher {
    @Override
    public void finish(BuildType buildType) {
      BuildTypeStatus bts = stor.load(buildType);
      bts.lastPlay = new Date();
      stor.save(bts);
    }
  }
}
