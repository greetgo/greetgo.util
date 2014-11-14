package kz.greetgo.teamcity.soundir.player;

import java.io.File;

public class Play {
  
  public static class Message {
    private final String message;
    
    public Message(String message) {
      this.message = message;
    }
    
    public void to(String sshAlias) {
      playMessageTo(message, sshAlias);
    }
    
  }
  
  public static Message message(String message) {
    return new Message(message);
  }
  
  public static void playMessageTo(String message, String sshAlias) {
    try {
      File file = getWaveFile(message);
      
      WavePlayer.fileTo(file, sshAlias);
      
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  private static final String WAVE_DIR = "data/waves";
  
  private static File getWaveFile(String message) throws Exception {
    File waveFile = new File(WAVE_DIR + "/" + StrUtil.strToMd5(message) + ".wav");
    if (!waveFile.exists()) {
      waveFile.getParentFile().mkdirs();
      WaveCreator.create(waveFile, message);
    }
    return waveFile;
  }
}
