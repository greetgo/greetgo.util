package kz.greetgo.teamcity.soundir.teamcity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import kz.greetgo.teamcity.soundir.teamcity.model.BuildStatus;
import kz.greetgo.teamcity.soundir.teamcity.model.Status;

public class Teamcity {
  private static int compareLongs(long l1, long l2) {
    if (l1 == l2) return 0;
    return l1 < l2 ? -1 :+1;
  }
  
  public static BuildStatus getFirstLastFailed(List<BuildStatus> list) {
    Collections.sort(list, new Comparator<BuildStatus>() {
      @Override
      public int compare(BuildStatus o1, BuildStatus o2) {
        return compareLongs(o2.number, o1.number);
      }
    });
    
    BuildStatus ret = null;
    
    for (BuildStatus bs : list) {
      if (bs.status != Status.FAILURE) return ret;
      ret = bs;
    }
    
    return ret;
  }
}
