package kz.greetgo.teamcity.soundir.player;

import java.math.BigInteger;
import java.security.MessageDigest;

public class StrUtil {
  public static String strToMd5(String str) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.reset();
      md.update(str.getBytes("UTF-8"));
      byte[] digest = md.digest();
      BigInteger num = new BigInteger(1, digest);
      
      StringBuilder ret = new StringBuilder(32);
      ret.append(num.toString(16).toLowerCase());
      
      while (ret.length() < 32) {
        ret.insert(0, '0');
      }
      
      return ret.toString();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
