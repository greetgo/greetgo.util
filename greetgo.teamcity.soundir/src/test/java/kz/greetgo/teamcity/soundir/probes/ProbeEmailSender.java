package kz.greetgo.teamcity.soundir.probes;

import kz.greetgo.teamcity.soundir.email.Email;
import kz.greetgo.teamcity.soundir.email.EmailSender;

public class ProbeEmailSender {
  public static void main(String[] args) {
    Email x = new Email();
    x.to = "ekolpakov@greet-go.com";
    x.from = "hi";
    x.subject = "Hello";
    x.body = "body of letter";
    
    EmailSender.send(x);
  }
}
