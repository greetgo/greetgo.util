package kz.greepto.gpen.drawport;

import kz.greepto.gpen.drawport.Size;

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
  
  public String toString() {
    return (((("Vec2(" + Integer.valueOf(this.x)) + ", ") + Integer.valueOf(this.y)) + ")");
  }
  
  public Vec2 copy() {
    return new Vec2(this);
  }
  
  public static Vec2 from(final int x, final int y) {
    return new Vec2(x, y);
  }
  
  public Vec2 operator_plus(final Vec2 a) {
    return Vec2.from((this.x + a.x), (this.y + a.y));
  }
  
  public Vec2 operator_plus(final int[] a) {
    int _get = a[0];
    int _plus = (this.x + _get);
    int _get_1 = a[1];
    int _plus_1 = (this.y + _get_1);
    return Vec2.from(_plus, _plus_1);
  }
  
  public Vec2 operator_plus(final Size s) {
    return Vec2.from((this.x + s.width), (this.y + s.height));
  }
  
  public Vec2 operator_minus(final Vec2 a) {
    return Vec2.from((this.x - a.x), (this.y - a.y));
  }
  
  public Vec2 operator_minus(final int[] a) {
    int _get = a[0];
    int _minus = (this.x - _get);
    int _get_1 = a[1];
    int _minus_1 = (this.y - _get_1);
    return Vec2.from(_minus, _minus_1);
  }
  
  public Vec2 operator_minus(final Size s) {
    return Vec2.from((this.x - s.width), (this.y - s.height));
  }
  
  public double getLen() {
    double _xblockexpression = (double) 0;
    {
      double X = ((double) this.x);
      double Y = ((double) this.y);
      _xblockexpression = Math.sqrt(((X * X) + (Y * Y)));
    }
    return _xblockexpression;
  }
}
