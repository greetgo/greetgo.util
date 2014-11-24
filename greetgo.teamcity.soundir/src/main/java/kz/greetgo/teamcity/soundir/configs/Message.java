package kz.greetgo.teamcity.soundir.configs;

public enum Message {
  DownTests_ScoringBaf("ВНИМАНИЕ! упали тэс ты системы ско ринг. баф. ско ринг. баф"),
  
  DownTests_ScoringRbk("ВНИМАНИЕ! упали тэс ты системы ско ринг. эр бэ ка. ско ринг. эр бэ ка"),
  
  DownTests_CC20("ВНИМАНИЕ! упали тэс ты системы си си два нооль. си си два нооль"),
  
  ;
  
  public final String text;
  
  private Message(String text) {
    this.text = text;
  }
}
/*
prj           employee   
------------  ---------  
CC20-kaspi    kgorgulev  
Collect-RBK   kgorgulev  
Collect-RBK   pkokoshko  
GCCenter-BAF  kgorgulev  
GFrost-BAF    stursyn    
GFrost-RBK    stursyn    
GFrost-RBK    abolatov   
KSA-kaspi     abolatov   
KSA-kaspi     pkokoshko  
VKB-kaspi     stursyn    
VKB-kaspi     abolatov   
*/
