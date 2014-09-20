package kz.greetgo.email;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SenderEmailController {
  
  private boolean inSendOperation = false;
  
  private final File sendDir;
  private final File sendedDir;
  private final EmailSender emailSender;
  
  private final EmailSerializer emailSerializer = new EmailSerializer();
  
  public SenderEmailController(EmailSender emailSender, File sendDir, File sendedDir) {
    this.emailSender = emailSender;
    this.sendDir = sendDir;
    this.sendedDir = sendedDir;
  }
  
  public void sendAllExistingEmails() {
    synchronized (this) {
      if (inSendOperation) return;
      inSendOperation = true;
    }
    try {
      while (hasToSendOne()) {}
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
  
  private boolean hasToSendOne() throws Exception {
    EmailInfo info = getFirstFromDir(sendDir);
    if (info == null) return false;
    
    info.file.renameTo(info.sendingFile);
    
    try {
      emailSender.send(info.email);
    } catch (Exception exception) {
      info.sendingFile.renameTo(info.file);
      throw exception;
    }
    
    info.sendedFile.getParentFile().mkdirs();
    info.sendingFile.renameTo(info.sendedFile);
    
    return true;
  }
  
  private EmailInfo getFirstFromDir(File emailSendDir) throws Exception {
    File[] files = emailSendDir.listFiles(new FileFilter() {
      @Override
      public boolean accept(File pathname) {
        return pathname.isFile() && pathname.getName().endsWith(".xml");
      }
    });
    if (files == null) return null;
    if (files.length == 0) return null;
    
    EmailInfo ret = new EmailInfo();
    ret.file = files[0];
    ret.email = emailSerializer.deserialize(ret.file);
    
    ret.sendingFile = new File(ret.file.getAbsolutePath() + ".sending");
    
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    ret.sendedFile = new File(sendedDir + "/" + format.format(new Date()) + "/" + ret.file.getName());
    
    return ret;
  }
  
  public void cleanOldSendedFiles(int daysBefore) {
    // TODO XXX
  }
}
