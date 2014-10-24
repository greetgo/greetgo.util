package kz.greetgo.email;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
  
  @Test
  public void cleanOldSendedFiles() throws Exception {
    
    File sendedDir = new File("build/sended_" + (new Date().getTime()));
    sendedDir.mkdirs();
    
    File fOld = new File(sendedDir, "old.xml");
    File fNew = new File(sendedDir, "new.xml");
    
    fOld.createNewFile();
    fNew.createNewFile();
    
    Calendar cal = new GregorianCalendar();
    cal.add(Calendar.DAY_OF_YEAR, -100);
    
    fOld.setLastModified(cal.getTimeInMillis());
    
    SenderEmailController c = new SenderEmailController(null, null, sendedDir);
    c.cleanOldSendedFiles(10);
    
    assertThat(fOld.exists()).isFalse();
    assertThat(fNew.exists()).isTrue();
    
    fNew.delete();
    sendedDir.delete();
  }
}
