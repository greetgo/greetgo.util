package kz.greetgo.teamcity.soundir.probes;

import java.util.List;

import kz.greetgo.teamcity.soundir.configs.FromDb;

public class ProbeEmailsForBuildType {
  public static void main(String[] args) {
    List<String> list = FromDb.emailsForBuildType("Scoring_Baf_ServerTest");
    
    for (String email : list) {
      System.out.println(email);
    }
    
  }
}
