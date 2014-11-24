package kz.greetgo.teamcity.soundir.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import kz.greetgo.teamcity.soundir.teamcity.model.Status;

public class StorageDir implements Storage {
  private static Storage defaultSD = null;
  
  public static Storage defaultSD() {
    if (defaultSD != null) return defaultSD;
    return defaultSD = new StorageDir("data/storage_001");
  }
  
  private final String dir;
  
  public StorageDir(String dir) {
    this.dir = dir;
  }
  
  private static final String EXT = "build_type";
  
  private File fileFor(String buildType) {
    return new File(dir + '/' + buildType + '.' + EXT);
  }
  
  @Override
  public Map<String, BuildTypeStatus> loadAll() {
    File fdir = new File(dir);
    if (!fdir.exists()) return new HashMap<>();
    File[] files = fdir.listFiles(new FileFilter() {
      @Override
      public boolean accept(File pathname) {
        return pathname.getName().toLowerCase().endsWith("." + EXT);
      }
    });
    
    Map<String, BuildTypeStatus> ret = new HashMap<>();
    
    for (File file : files) {
      BuildTypeStatus bts = loadFromFile(file);
      if (bts == null) continue;
      ret.put(bts.buildType, bts);
    }
    
    return ret;
  }
  
  private static BuildTypeStatus loadFromFile(File file) {
    
    Map<String, String> strMap = readStrMap(file);
    
    BuildTypeStatus ret = new BuildTypeStatus();
    
    ret.buildType = strMap.get("buildType");
    
    ret.status = Status.valueOf(strMap.get("status"));
    ret.number = Integer.parseInt(strMap.get("number"));
    
    ret.lastChange = strToDate(strMap.get("lastChange"));
    ret.lastPlay = strToDate(strMap.get("lastPlay"));
    
    return ret;
  }
  
  private static void saveToFile(BuildTypeStatus bts, File file) {
    file.getParentFile().mkdirs();
    
    try {
      PrintStream out = new PrintStream(file, "UTF-8");
      
      out.println(" buildType  : " + bts.buildType);
      out.println(" status     : " + bts.status.name());
      out.println(" number     : " + bts.number);
      out.println(" lastChange : " + dateToStr(bts.lastChange));
      out.println(" lastPlay   : " + dateToStr(bts.lastPlay));
      
      out.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  private static String dateToStr(Date date) {
    if (date == null) return "";
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
  }
  
  private static Date strToDate(String str) {
    if (str == null) return null;
    str = str.trim();
    if (str.length() == 0) return null;
    
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    try {
      return df.parse(str);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
  
  private static Map<String, String> readStrMap(File file) {
    try {
      Map<String, String> ret = new HashMap<>();
      
      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),
          "UTF-8"));
      String line = null;
      while ((line = br.readLine()) != null) {
        line = line.trim();
        if (line.length() == 0) continue;
        if (line.startsWith("#")) continue;
        int idx = line.indexOf(':');
        if (idx < 0) continue;
        ret.put(line.substring(0, idx).trim(), line.substring(idx + 1).trim());
      }
      br.close();
      
      return ret;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public BuildTypeStatus load(String buildType) {
    File file = fileFor(buildType);
    return loadFromFile(file);
  }
  
  @Override
  public BuildTypeStatus save(BuildTypeStatus bts) {
    File file = fileFor(bts.buildType);
    saveToFile(bts, file);
    return bts;
  }
  
}
