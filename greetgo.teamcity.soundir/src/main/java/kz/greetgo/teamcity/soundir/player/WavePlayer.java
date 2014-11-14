package kz.greetgo.teamcity.soundir.player;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class WavePlayer {
  
  public static void fileTo(File file, String sshAlias) throws Exception {
    InputStream stream = new FileInputStream(file);
    streamTo(stream, sshAlias);
    stream.close();
  }
  
  public static void streamTo(InputStream stream, String sshAlias) throws Exception {
    Runtime r = Runtime.getRuntime();
    Process p = r.exec(new String[] { "ssh", sshAlias, "aplay" });
    
    {
      OutputStream out = p.getOutputStream();
      
      byte[] buf = new byte[1024 * 8];
      while (true) {
        int count = stream.read(buf);
        if (count < 0) break;
        out.write(buf, 0, count);
      }
      
      stream.close();
      out.close();
    }
    
    int exit = p.waitFor();
    if (exit != 0) throw new RuntimeException("Cannot play with ssh. Exit code " + exit);
  }
  
}
