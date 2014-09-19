package kz.greetgo.email;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class EmailSaver implements EmailSender {
  private final String sendDir;
  private final String name;
  
  public EmailSaver(String name, String sendDir) {
    this.name = name;
    this.sendDir = sendDir;
  }
  
  private final Random rnd = new Random();
  
  private String createFullName() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS");
    
    return name + '-' + sdf.format(new Date()) + '.' + rnd.nextInt() + ".email.xml";
  }
  
  @Override
  public void send(Email email) {
    String filename = createFullName();
    File file = new File(sendDir + "/" + filename + ".creating");
    file.getParentFile().mkdirs();
    EmailSerializer s = new EmailSerializer();
    try {
      s.serialize(file, email);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    
    file.renameTo(new File(sendDir + "/" + filename));
  }
  
}
