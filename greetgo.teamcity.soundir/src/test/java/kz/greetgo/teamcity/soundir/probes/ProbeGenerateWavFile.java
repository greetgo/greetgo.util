package kz.greetgo.teamcity.soundir.probes;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class ProbeGenerateWavFile {
  public static void main(String[] args) throws Exception {
    Runtime r = Runtime.getRuntime();
    Process p = r.exec(new String[] { "text2wave", "-eval", "(voice_msu_ru_nsh_clunits)" });
    {
      PrintStream out = new PrintStream(p.getOutputStream(), false, "UTF-8");
      
      out.print("ВНИМАНИЕ! упала система коллекшин. эР Бэ Ка. коллекшин. эР Бэ Ка");
      
      out.flush();
      out.close();
    }
    
    {
      InputStream in = p.getInputStream();
      FileOutputStream fout = new FileOutputStream("/home/pompei/tmp/java.wav");
      
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
    System.out.println("OK");
  }
}
