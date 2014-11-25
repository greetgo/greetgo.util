package kz.greetgo.teamcity.soundir.probes;

import kz.greetgo.teamcity.soundir.controller.MessageMapGetter;
import kz.greetgo.teamcity.soundir.controller.SendLettersController;
import kz.greetgo.teamcity.soundir.storage.Storage;
import kz.greetgo.teamcity.soundir.storage.StorageDir;
import kz.greetgo.teamcity.soundir.teamcity.Api;

public class ProbeSendLetterController {
  public static void main(String[] args) {
    Api.prepareBasicAuthenticator("root", "111");
    Api.protocol = "http";
    Api.host = "192.168.0.102";
    Api.port = 8111;
    
    Storage storage = new StorageDir("data/storage");
    
    SendLettersController sl = new SendLettersController();
    sl.storage = storage;
    sl.messageMap = MessageMapGetter.messageMap;
    
    sl.checkSendLetters("Scoring_Baf_ServerTest");
    
  }
}
