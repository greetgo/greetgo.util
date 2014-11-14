package kz.greetgo.teamcity.soundir.probes;

import java.util.List;

import kz.greetgo.teamcity.soundir.teamcity.Api;
import kz.greetgo.teamcity.soundir.teamcity.model.LastStatus;
import kz.greetgo.teamcity.soundir.teamcity.rhs.BuildTypeIdList;
import kz.greetgo.teamcity.soundir.teamcity.rhs.LastBuildStatus;

public class ProbeListAllLastBuildStatuses {
  public static void main(String[] args) {
    Api.prepareBasicAuthenticator("root", "111");
    Api.protocol = "http";
    Api.host = "192.168.0.102";
    Api.port = 8111;
    
    List<String> buildTypeIdList = Api.get(new BuildTypeIdList());
    
    for (String buildTypeId : buildTypeIdList) {
      LastStatus ls = Api.get(new LastBuildStatus(buildTypeId));
      System.out.println(ls);
    }
    
  }
}
