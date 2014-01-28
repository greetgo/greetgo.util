package kz.pompei.dao.i;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LensProbe {
  public static void main(String[] args) throws Exception {
    Class<?> cl = V.class;
    
    class Wow implements Comparable<Wow> {
      public final String str;
      public final int len;
      
      Wow(String str) {
        this.str = str;
        this.len = str.length();
      }
      
      @Override
      public int compareTo(Wow o) {
        {
          int cmpLen = len - o.len;
          if (cmpLen != 0) return cmpLen;
        }
        return str.toUpperCase().compareTo(o.str.toUpperCase());
      }
      
      @Override
      public String toString() {
        String len = "" + this.len;
        while (len.length() < 3) {
          len = ' ' + len;
        }
        String rest = str;
        final int max = 30;
        if (rest.length() > max) {
          rest = str.substring(0, max) + "[" + str.substring(max) + "]";
        }
        String left = len + "  " + rest;
        while (left.length() < 60) {
          left = left + ' ';
        }
        return left + "- ";
      }
    }
    
    List<Wow> wows = new ArrayList<>();
    for (Field field : cl.getFields()) {
      wows.add(new Wow((String)field.get(null)));
    }
    Collections.sort(wows);
    for (Wow wow : wows) {
      System.out.println(wow);
    }
  }
}
