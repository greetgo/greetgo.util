package kz.greetgo.util;

import java.util.HashMap;
import java.util.Map;

public class StrReplacer {
  private final StringBuilder sb = new StringBuilder();
  
  private final Map<String, String> paramMap = new HashMap<String, String>();
  
  public StrReplacer add(CharSequence s) {
    sb.append(s);
    return this;
  }
  
  public StrReplacer add(CharSequence s, int start, int end) {
    sb.append(s, start, end);
    return this;
  }
  
  public StrReplacer add(int i) {
    sb.append(i);
    return this;
  }
  
  public StrReplacer add(char[] str, int offset, int len) {
    sb.append(str, offset, len);
    return this;
  }
  
  public StrReplacer add(char[] str) {
    sb.append(str);
    return this;
  }
  
  public StrReplacer add(Integer i) {
    sb.append(i);
    return this;
  }
  
  public StrReplacer add(long l) {
    sb.append(l);
    return this;
  }
  
  public StrReplacer add(Long l) {
    sb.append(l);
    return this;
  }
  
  public StrReplacer add(StringBuilder sb) {
    sb.append(sb);
    return this;
  }
  
  public StrReplacer add(StrReplacer sr) {
    sb.append(sr.sb);
    return this;
  }
  
  public StrReplacer prm(String key, String value) {
    paramMap.put(key, value);
    return this;
  }
  
  @Override
  public String toString() {
    String s = sb.toString();
    StringBuilder ret = new StringBuilder(s.length());
    
    while (true) {
      int i1 = s.indexOf('{');
      if (i1 < 0) break;
      int i2 = s.indexOf('}', i1);
      if (i2 < 0) break;
      String key = s.substring(i1 + 1, i2);
      String value = paramMap.get(key);
      if (value == null) {
        ret.append(s, 0, i2);
        s = s.substring(i2);
      } else {
        ret.append(s, 0, i1).append(value);
        s = s.substring(i2 + 1);
      }
    }
    
    if (s.length() > 0) ret.append(s);
    
    return ret.toString();
  }
}
