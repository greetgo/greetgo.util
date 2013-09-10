package kz.greetgo.watcher.trace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class LinkedMessageOuter implements MessageOuter {
  public String prefix = "out";
  public String extention = ".log";
  public int indexLength = 3;
  
  public long maxFileSize = 30 * (1024 * 1024);
  public int maxFilesCount = 50;
  public String outDir = "/tmp/trace_out_dir";
  
  private volatile Thread outingThread = null;
  private boolean opened = true;
  
  private final Object syncObject = new Object();
  private volatile MessageDot last = null;
  
  @Override
  public void close() {
    opened = false;
  }
  
  public static String tolen(int len, String str) {
    if (str == null) str = "";
    if (str.length() >= len) return str;
    StringBuilder ret = new StringBuilder(len);
    for (int i = 0, C = len - str.length(); i < C; i++) {
      ret.append('0');
    }
    ret.append(str);
    return ret.toString();
  }
  
  private static class MessageDot {
    final String message;
    MessageDot next = null, prev;
    
    public MessageDot(String message) {
      this.message = message;
    }
  }
  
  @Override
  public void outMessage(String message) {
    MessageDot md = new MessageDot(message);
    synchronized (syncObject) {
      md.next = last;
      last = md;
      syncObject.notify();
    }
    
    checkOuting();
  }
  
  private void checkOuting() {
    synchronized (syncObject) {
      checkOutingInner();
    }
  }
  
  private void checkOutingInner() {
    if (outingThread != null) return;
    outingThread = new Thread(new Runnable() {
      @Override
      public void run() {
        W: while (opened || last != null) {
          MessageDot md = null;
          synchronized (syncObject) {
            md = last;
            last = null;
          }
          if (md != null) try {
            out(md);
          } catch (Exception e1) {
            throw new RuntimeException(e1);
          }
          synchronized (syncObject) {
            try {
              syncObject.wait(1000);
            } catch (InterruptedException e) {
              break W;
            }
          }
          try {
            Thread.sleep(10);
          } catch (InterruptedException e) {
            break W;
          }
        }
        outingThread = null;
      }
    });
    outingThread.start();
  }
  
  private File getFile(String dir, int index) {
    return new File(dir + "/" + prefix + "." + tolen(indexLength, "" + index) + extention);
  }
  
  private void out(MessageDot md) throws Exception {
    if (md == null) return;
    
    new File(outDir).mkdirs();
    
    MessageDot last = md;
    last.prev = null;
    while ((md = md.next) != null) {
      md.prev = last;
      md.prev.next = null;
      last = md;
    }
    
    File firstLog = getFile(outDir, 1);
    
    ByteCounter counter = new ByteCounter();
    if (firstLog.exists()) {
      counter.counter = firstLog.length();
    }
    
    PrintStream out = null;
    
    while (last != null) {
      
      if (counter.counter > maxFileSize) {
        if (out != null) {
          out.flush();
          out.close();
          out = null;
        }
        
        recircle(firstLog);
        counter.counter = 0;
      }
      
      if (out == null) {
        out = createPrinter(firstLog, counter);
      }
      
      out.print(last.message);
      out.print("\n");
      
      last = last.prev;
    }
    
    if (out != null) {
      out.flush();
      out.close();
      out = null;
    }
  }
  
  private PrintStream createPrinter(File mainLog, ByteCounter counter)
      throws UnsupportedEncodingException, FileNotFoundException {
    return new PrintStream(counter.out(new FileOutputStream(mainLog, true)), false, "UTF-8");
  }
  
  private void recircle(File mainLog) {
    if (mainLog.exists() && mainLog.length() > maxFileSize) {
      File maxLog = getFile(outDir, maxFilesCount);
      if (maxLog.exists()) maxLog.delete();
      for (int i = maxFilesCount - 1; i > 0; i--) {
        File cur = getFile(outDir, i);
        if (cur.exists()) {
          cur.renameTo(getFile(outDir, i + 1));
        }
      }
    }
  }
}
