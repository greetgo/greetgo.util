package kz.greepto.gpen.util;

@SuppressWarnings("all")
public class Rect {
  public int x = 0;
  
  public int y = 0;
  
  public int width = 0;
  
  public int height = 0;
  
  public static Rect zero() {
    return new Rect();
  }
  
  private Rect() {
  }
  
  private Rect(final Rect a) {
    this.x = a.x;
    this.y = a.y;
    this.width = a.width;
    this.height = a.height;
  }
}
