package kz.greetgo.watcher.ds;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

@Deprecated
public abstract class ConfDataSourceWatcher extends DataSourceWatcher {
  private final String filePrefix;
  
  private boolean activeInfo = false;
  private boolean activeInfo2 = false;
  private boolean activeCommon = false;
  private long configReadLastTime = 0;
  private long configReadPeriod = 5000;
  
  private final ConfDataSourceTrace trace = new ConfDataSourceTrace();
  
  public void setConfigReadPeriod(long configReadPeriod) {
    this.configReadPeriod = configReadPeriod;
  }
  
  public ConfDataSourceWatcher(String filePrefix) {
    this.filePrefix = filePrefix;
  }
  
  private void readKeyValue(String key, String value) {
    if ("active".equals(key)) {
      setActive(strToBool(value));
      return;
    }
    if ("infoTraceEnabled".equals(key)) {
      activeInfo = strToBool(value);
      return;
    }
    if ("info2TraceEnabled".equals(key)) {
      activeInfo2 = strToBool(value);
      return;
    }
    if ("commonTraceEnabled".equals(key)) {
      activeCommon = strToBool(value);
      return;
    }
    if ("configReadPeriod".equals(key)) {
      configReadPeriod = strToLong(value);
      return;
    }
    if ("indexLength".equals(key)) {
      trace.outer.indexLength = strToInt(value);
      return;
    }
    if ("extention".equals(key)) {
      trace.outer.extention = value;
      return;
    }
    if ("prefix".equals(key)) {
      trace.outer.prefix = value;
      return;
    }
    if ("outDir".equals(key)) {
      trace.outer.outDir = value;
      return;
    }
    if ("maxFileSize".equals(key)) {
      trace.outer.maxFileSize = strToLong(value);
      return;
    }
    if ("maxFilesCount".equals(key)) {
      trace.outer.maxFilesCount = strToInt(value);
      return;
    }
  }
  
  private static int strToInt(String value) {
    if (value == null) return 0;
    return Integer.parseInt(value.replaceAll("\\s+", ""));
  }
  
  private void createConfFile() {
    File confFile = new File(filePrefix + ".conf");
    try {
      confFile.getParentFile().mkdirs();
      PrintStream out = new PrintStream(new FileOutputStream(confFile), false, "UTF-8");
      
      out.println("active false");
      out.println("infoTraceEnabled false");
      out.println("info2TraceEnabled false");
      out.println("commonTraceEnabled false");
      out.println("");
      out.println("configReadPeriod     5 000");
      out.println("");
      out.println("indexLength      3");
      out.println("extention        .log");
      out.println("prefix           sqlTrace");
      out.println("outDir           " + filePrefix + ".d");
      
      out.println("maxFileSize      30 000 000");
      out.println("maxFilesCount    100");
      
      out.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  private void readConfig() {
    File confFile = new File(filePrefix + ".conf");
    if (!confFile.exists()) {
      createConfFile();
      return;
    }
    
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(confFile),
          "UTF-8"));
      String line = null;
      while ((line = br.readLine()) != null) {
        line = line.trim();
        if (line.length() == 0) continue;
        if (line.startsWith("#")) continue;
        
        int space = line.indexOf(' ');
        if (space < 0) continue;
        String key = line.substring(0, space);
        String value = line.substring(space).trim();
        readKeyValue(key, value);
      }
      br.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Override
  public void acceptInfo(String logMessage) {
    if (System.currentTimeMillis() - configReadLastTime >= configReadPeriod) {
      readConfig();
      configReadLastTime = System.currentTimeMillis();
    }
    if (activeInfo) trace.info(logMessage);
  }
  
  @Override
  public void acceptCommon(String logMessage) {
    if (System.currentTimeMillis() - configReadLastTime >= configReadPeriod) {
      readConfig();
      configReadLastTime = System.currentTimeMillis();
    }
    if (activeCommon) trace.common(logMessage);
  }
  
  @Override
  public void acceptInfo2(String logMessage) {
    if (System.currentTimeMillis() - configReadLastTime >= configReadPeriod) {
      readConfig();
      configReadLastTime = System.currentTimeMillis();
    }
    if (activeInfo2) trace.info2(logMessage);
  }
  
  public static boolean strToBool(String str) {
    if (str == null) return false;
    str = str.trim().toLowerCase();
    if (str.startsWith("t")) return true;
    if ("1".equals(str)) return true;
    return false;
  }
  
  private static long strToLong(String value) {
    if (value == null) return 0;
    return Long.parseLong(value.replaceAll("\\s+", ""));
  }
}
