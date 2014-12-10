package kz.greepto.gpen.drawport;

@SuppressWarnings("all")
public class Vec2 {
  public int x;
  
  public int y;
  
  public Vec2(final int x, final int y) {
    this.x = x;
    this.y = y;
  }
  
  public Vec2() {
    this(0, 0);
  }
  
  public Vec2(final Vec2 a) {
    this(a.x, a.y);
  }
}
