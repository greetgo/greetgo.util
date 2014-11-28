package kz.greepto.gpen.editors.gpen.style;

@SuppressWarnings("all")
public class PaintStatus {
  public boolean hover;
  
  public boolean active;
  
  public static PaintStatus normal() {
    PaintStatus ret = new PaintStatus();
    ret.hover = false;
    ret.active = false;
    return ret;
  }
  
  public static PaintStatus hover() {
    PaintStatus ret = new PaintStatus();
    ret.hover = true;
    ret.active = false;
    return ret;
  }
}
