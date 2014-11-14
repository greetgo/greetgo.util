package kz.greetgo.teamcity.soundir.probes;

import kz.greetgo.teamcity.soundir.player.StrUtil;

public class ProbeStrToMd5 {
  public static void main(String[] args) throws Exception {
    String str = "ВНИМАНИЕ! упала система коллекшин. эР Бэ Ка. коллекшин. эР Бэ Ка";
    
    String md5 = StrUtil.strToMd5(str);
    
    System.out.println(md5);
  }
}
