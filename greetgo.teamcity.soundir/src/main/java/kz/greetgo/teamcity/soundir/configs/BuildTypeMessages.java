package kz.greetgo.teamcity.soundir.configs;

import java.util.HashMap;
import java.util.Map;

public class BuildTypeMessages {
  
  public static Map<BuildType, Message> data = new HashMap<>();
  static {
    data.put(BuildType.Scoring_Baf_ServerTest, Message.DownTests_ScoringBaf);
    data.put(BuildType.Cc20_ServerTests, Message.DownTests_CC20);
  }
  
  public static Message getMessageFor(BuildType buildType) {
    Message ret = data.get(buildType);
    if (ret == null) throw new NoMessageForBuildType(buildType);
    return ret;
  }
}
