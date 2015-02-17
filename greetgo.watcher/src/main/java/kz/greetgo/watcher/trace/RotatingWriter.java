package kz.greetgo.watcher.trace;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class RotatingWriter extends Writer {
  
  private final File dir;
  private final String prefix;
  private final String suffix;
  private final int maxFiles;
  private final long maxFileSize;
  private final String nomerFormat;
  
  public RotatingWriter(File dir, String prefix, String suffix, int maxFiles, long maxFileSize) {
    this.dir = dir;
    this.prefix = prefix;
    this.suffix = suffix;
    this.maxFiles = maxFiles;
    this.maxFileSize = maxFileSize;
    
    int max = Integer.toString(maxFiles).length();
    nomerFormat = "%0" + max + "d";
  }
  
  private static final int afterEnter(char[] cbuf, int off, int len) {
    for (int i = off, end = off + len; i < end; i++) {
      if (cbuf[i] == '\n') return i + 1;
    }
    return -1;
  }
  
  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    
    while (true) {
      int afterEnter = afterEnter(cbuf, off, len);
      if (afterEnter < 0) {
        flushPart(cbuf, off, len);
        break;
      }
      flushPart(cbuf, off, afterEnter - off);
      swap();
      off = afterEnter;
    }
    
  }
  
  private PrintWriter writer = null;
  private long length;
  
  private void flushPart(char[] cbuf, int off, int len) {
    if (writer == null) {
      prepareWriter();
    }
    
    writer.write(cbuf, off, len);
    length += len;
  }
  
  private void swap() {
    assert writer != null;
    if (length < maxFileSize) return;
    writer.close();
    writer = null;
    preparePlace(1);
  }
  
  private void preparePlace(int nomer) {
    File file = getFile(nomer);
    
    if (!file.exists()) return;
    
    if (nomer > maxFiles) {
      file.delete();
      return;
    }
    
    preparePlace(nomer + 1);
    file.renameTo(getFile(nomer + 1));
  }
  
  private void prepareWriter() {
    assert writer == null;
    File file = getFile(1);
    file.getParentFile().mkdirs();
    
    if (file.exists()) {
      length = file.length();
    } else {
      length = 0;
    }
    
    try {
      
      FileOutputStream fout = new FileOutputStream(file, true);
      writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fout, "UTF-8")), false);
      
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }
  
  private File getFile(int nomer) {
    return new File(dir, prefix + String.format(nomerFormat, nomer) + suffix);
  }
  
  @Override
  public void flush() throws IOException {
    if (writer != null) writer.flush();
  }
  
  @Override
  public void close() throws IOException {
    if (writer != null) {
      writer.close();
      writer = null;
    }
  }
}