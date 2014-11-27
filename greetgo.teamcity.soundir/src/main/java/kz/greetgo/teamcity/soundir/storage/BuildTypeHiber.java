package kz.greetgo.teamcity.soundir.storage;

import java.util.Date;

import kz.greetgo.teamcity.soundir.teamcity.model.Status;

public interface BuildTypeHiber {
  public String getBuildType();
  
  public Status getStatus();
  
  public void setStatus(Status status);
  
  public int getNumber();
  
  public void setNumber(int number);
  
  public Date getLastChange();
  
  public void setLastChange(Date lastChange);
  
  public Date getLastPlay();
  
  public void setLastPlay(Date lastPlay);
  
  public Date getLastSendLetter();
  
  public void setLastSendLetter(Date lastSendLetter);
}
