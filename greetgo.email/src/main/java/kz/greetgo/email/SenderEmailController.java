package kz.greetgo.email;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SenderEmailController {
  private boolean inSendOperation = false;
  
  private File sendDir;
  private File sendedDir;
  private EmailSender emailSender;
  
  public SenderEmailController(EmailSender emailSender, File sendDir, File sendedDir) {
    this.emailSender = emailSender;
    this.sendDir = sendDir;
    this.sendedDir = sendedDir;
  }
  
  private final EmailSerializer serializer = new EmailSerializer();
  
  public void sendAllExistingEmails() {
    synchronized (this) {
      if (inSendOperation) return;
      inSendOperation = true;
    }
    try {
      while (sendOne()) {}
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      inSendOperation = false;
    }
  }
  
  private static class EmailInfo {
    File file, sendingFile, sendedFile;
    Email email;
  }
  
  private EmailInfo getFirstFromDir(File dir) throws Exception {
    File[] list = dir.listFiles(new FileFilter() {
      @Override
      public boolean accept(File pathname) {
        return pathname.isFile() && pathname.getName().endsWith(".xml");
      }
    });
    if (list == null) return null;
    if (list.length == 0) return null;
    
    EmailInfo ret = new EmailInfo();
    ret.file = list[0];
    
    ret.email = serializer.deserialize(ret.file);
    ret.sendingFile = new File(ret.file.getAbsolutePath() + ".sending");
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
    ret.sendedFile = new File(sendedDir + "/" + f.format(new Date()) + "/" + ret.file.getName());
    
    return ret;
  }
  
  public static void main(String[] args) {
    File f = new File("asd/asd/asdasd");
    System.out.println(f.getName());
    
  }
  
  private boolean sendOne() throws Exception {
    EmailInfo info = getFirstFromDir(sendDir);
    if (info == null) return false;
    
    info.file.renameTo(info.sendingFile);
    
    try {
      emailSender.send(info.email);
    } catch (Exception e) {
      info.sendingFile.renameTo(info.file);
      throw e;
    }
    
    info.sendedFile.getParentFile().mkdirs();
    info.sendingFile.renameTo(info.sendedFile);
    
    return true;
  }
  
  public void cleanOldSendedFiles(int daysBefore) {
    //TODO xxxxxxxxxx
  }
}
