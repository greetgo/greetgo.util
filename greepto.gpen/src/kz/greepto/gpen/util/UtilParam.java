package kz.greepto.gpen.util;

import java.util.ArrayList;
import java.util.List;

public class UtilParam {
  public static List<String> parsParam(String param) {
    List<String> ret = new ArrayList<>();
    
    char kav = ' ';
    boolean inKav = false;
    
    StringBuilder sb = new StringBuilder(20);
    
    for (int i = 0, C = param.length(); i < C; i++) {
      char c = param.charAt(i);
      char prevC = i == 0 ? ' ' :param.charAt(i - 1);
      
      if (c != '"' && c != '\'') {
        if (c == '\\' && prevC == '\\') {
          sb.append('\\');
          continue;
        }
        if (c == '\\') continue;
        sb.append(c);
        continue;
      }
      
      if (inKav && kav == c && prevC != '\\') {
        ret.add(sb.toString());
        sb.setLength(0);
        inKav = false;
        continue;
      }
      
      if (inKav) {
        sb.append(c);
        continue;
      }
      
      kav = c;
      inKav = true;
      {
        String str = sb.toString().trim();
        if (str.length() > 0) for (String x : str.split("\\s+")) {
          ret.add(x);
        }
      }
      sb.setLength(0);
      
    }
    
    {
      String str = sb.toString().trim();
      if (str.length() > 0) for (String x : str.split("\\s+")) {
        ret.add(x);
      }
    }
    
    return ret;
  }
}
