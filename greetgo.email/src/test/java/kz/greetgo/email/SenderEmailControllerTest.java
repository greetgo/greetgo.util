package kz.greetgo.email;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;

import org.testng.annotations.Test;

public class SenderEmailControllerTest {
  
  @Test
  public void send() throws Exception {
    
    String sendDir = "build/send";
    String sendedDir = "build/sended";
    
    EmailSaver saver = new EmailSaver("asd", sendDir);
    EmailSaver realSender = new EmailSaver("asd", "build/sendedReal");
    
    SenderEmailController c = new SenderEmailController(realSender, new File(sendDir), new File(
        sendedDir));
    
    Email e = new Email();
    e.setBody("body");
    e.setSubject("subject");
    e.setFrom("from");
    e.setTo("to");
    
    saver.send(e);
    
    c.sendAllExistingEmails();
    
    assertThat(1);
  }
}
