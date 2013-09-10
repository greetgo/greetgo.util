package kz.greetgo.msoffice.xlsx.parse;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import kz.greetgo.msoffice.UtilOffice;

public class SharedStrings {
  private static final int SPACE_FOR_HEADER = 200;
  
  public SharedStrings(String workDir) throws Exception {
    new File(workDir + "/xl").mkdirs();
    file = new RandomAccessFile(new File(workDir + "/xl/sharedStrings.xml"), "rw");
    prepareFile();
  }
  
  private int index = 0;
  private RandomAccessFile file;
  
  private final void prepareFile() throws Exception {
    FileChannel fc = file.getChannel();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < SPACE_FOR_HEADER; i++) {
      sb.append(' ');
    }
    sb.append("\r\n");
    byte[] bytes = sb.toString().getBytes();
    ByteBuffer buf = ByteBuffer.wrap(bytes);
    while (buf.hasRemaining()) {
      fc.write(buf);
    }
    fc.truncate(bytes.length);
  }
  
  public int index(String str) {
    StringBuilder sb = new StringBuilder();
    sb.append("<si><t>");
    if (str == null) str = "";
    str = str.replaceAll("&", "&amp;");
    str = str.replaceAll("<", "&lt;");
    str = str.replaceAll(">", "&gt;");
    sb.append(str);
    sb.append("</t></si>\r\n");
    UtilOffice.writeToChannel(sb.toString(), file.getChannel());
    return index++;
  }
  
  public void close() throws Exception {
    FileChannel fc = file.getChannel();
    
    {
      UtilOffice.writeToChannel("</sst>", fc);
    }
    
    {
      fc.position(0);
      StringBuilder sb = new StringBuilder();
      sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r\n");
      sb.append("<sst xmlns=\"http://schemas.openxmlformats.org/"
          + "spreadsheetml/2006/main\" count=\"" + index + "\" uniqueCount=\"" + index + "\">");
      UtilOffice.writeToChannel(sb.toString(), fc);
    }
    
    file.close();
  }
}
