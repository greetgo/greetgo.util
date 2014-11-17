package kz.greetgo.teamcity.soundir.configs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildTypeComp {
  public static Map<BuildType, List<Comp>> data = new HashMap<>();
  static {
    add(BuildType.Cc20_ServerTests, Comp.me, Comp.oleg);
    add(BuildType.Scoring_Baf_ServerTest, Comp.oleg);
  }
  
  private static void add(BuildType buildType, Comp... comps) {
    List<Comp> list = new ArrayList<>();
    for (Comp comp : comps) {
      list.add(comp);
    }
    data.put(buildType, list);
  }
}
