package kz.greetgo.teamcity.soundir.player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class WaveCreator {
  public static void create(File waveFile, String message) throws Exception {
    waveFile.getParentFile().mkdirs();
    
    Runtime r = Runtime.getRuntime();
    Process p = r.exec(new String[] { "text2wave", "-eval", "(voice_msu_ru_nsh_clunits)" });
    {
      PrintStream out = new PrintStream(p.getOutputStream(), false, "UTF-8");
      
      out.print(message);
      
      out.flush();
      out.close();
    }
    
    {
      InputStream in = p.getInputStream();
      FileOutputStream fout = new FileOutputStream(waveFile);
      
      byte[] buf = new byte[1024 * 8];
      while (true) {
        int count = in.read(buf);
        if (count < 0) break;
        fout.write(buf, 0, count);
      }
      
      fout.close();
      in.close();
    }
    
    int ret = p.waitFor();
    p.destroy();
    if (ret != 0) throw new RuntimeException("Exit code of text2wave = " + ret);
    
  }
}
