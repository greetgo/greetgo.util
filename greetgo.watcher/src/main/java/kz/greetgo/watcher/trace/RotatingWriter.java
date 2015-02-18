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
  private final Iterable<File> files;
  private final long maxFileSize;
  
  public RotatingWriter(Iterable<File> files, long maxFileSize) {
    this.files = files;
    this.maxFileSize = maxFileSize;
  }
  
  private static final int indexOfNewline(char[] cbuf, int off, int to) {
    for (int i = off; i < to; i++) {
      if (cbuf[i] == '\n') return i;
    }
    return -1;
  }
  
  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    int to = off + len;
    while (true) {
      int nl = indexOfNewline(cbuf, off, to);
      if (nl < 0) {
        flushPart(cbuf, off, to - off);
        break;
      }
      flushPart(cbuf, off, nl + 1 - off);
      swap();
      off = nl + 1;
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
    FileSequence.rotate(files);
  }
  
  private void prepareWriter() {
    assert writer == null;
    File file = files.iterator().next();
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