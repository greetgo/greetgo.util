package kz.greetgo.msoffice.docx;

import java.io.PrintStream;

import kz.greetgo.msoffice.UtilOffice;

public class ImageElement implements RunElement {
  
  private final String id;
  private int cx, cy;
  
  public ImageElement(String id) {
    this.id = id;
  }
  
  @Override
  public void write(PrintStream out) {
    String template = UtilOffice.streamToStr(ImageElement.class
        .getResourceAsStream("ImageElement.data.xml"));
    template = template.replace("[[CX]]", String.valueOf(getCx()));
    template = template.replace("[[CY]]", String.valueOf(getCy()));
    template = template.replace("[[ID]]", getId());
    out.print(template);
  }
  
  public String getId() {
    return id;
  }
  
  public void setCy(int cy) {
    this.cy = cy;
  }
  
  public int getCy() {
    return cy;
  }
  
  public void setCx(int cx) {
    this.cx = cx;
  }
  
  public int getCx() {
    return cx;
  }
}
