package kz.greetgo.teamcity.soundir.storage;

import java.util.Date;

import kz.greetgo.teamcity.soundir.teamcity.model.Status;

public class StorageDir implements Storage {
  private static Storage defaultSD = null;
  
  public static Storage defaultSD() {
    if (defaultSD != null) return defaultSD;
    return defaultSD = new StorageDir("data/storage_005");
  }
  
  private final PropertySaver ps;
  
  public StorageDir(String dir) {
    ps = new PropertySaver(dir);
  }
  
  @Override
  public BuildTypeHiber forBuildType(final String buildType) {
    return new BuildTypeHiber() {
      
      @Override
      public void setStatus(Status status) {
        ps.setEnum(buildType, "status", status);
      }
      
      @Override
      public void setNumber(int number) {
        ps.setInt(buildType, "number", number);
      }
      
      @Override
      public void setLastSendLetter(Date lastSendLetter) {
        ps.setDate(buildType, "lastSendLetter", lastSendLetter);
      }
      
      @Override
      public void setLastPlay(Date lastPlay) {
        ps.setDate(buildType, "lastPlay", lastPlay);
      }
      
      @Override
      public void setLastChange(Date lastChange) {
        ps.setDate(buildType, "lastChange", lastChange);
      }
      
      @Override
      public Status getStatus() {
        return ps.getEnum(Status.class, buildType, "status");
      }
      
      @Override
      public int getNumber() {
        return ps.getInt(buildType, "number");
      }
      
      @Override
      public Date getLastSendLetter() {
        return ps.getDate(buildType, "lastSendLetter");
      }
      
      @Override
      public Date getLastPlay() {
        return ps.getDate(buildType, "lastPlay");
      }
      
      @Override
      public Date getLastChange() {
        return ps.getDate(buildType, "lastChange");
      }
      
      @Override
      public String getBuildType() {
        return buildType;
      }
    };
  }
}
