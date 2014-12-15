package kz.greepto.gpen.drawport;

import kz.greepto.gpen.drawport.Vec2;

@SuppressWarnings("all")
public class Size {
  public int width;
  
  public int height;
  
  public static Size from(final int width, final int height) {
    return new Size(width, height);
  }
  
  public static Size zero() {
    return Size.from(0, 0);
  }
  
  public Size copy() {
    return new Size(this);
  }
  
  private Size(final int width, final int height) {
    this.width = width;
    this.height = height;
  }
  
  private Size() {
    this(0, 0);
  }
  
  private Size(final Size a) {
    this(a.width, a.height);
  }
  
  public static Size fromTo(final Vec2 from, final Vec2 to) {
    boolean _tripleEquals = (from == null);
    if (_tripleEquals) {
      throw new NullPointerException("from == null");
    }
    boolean _tripleEquals_1 = (to == null);
    if (_tripleEquals_1) {
      throw new NullPointerException("to == null");
    }
    int w = (from.x - to.x);
    int h = (from.y - to.y);
    if ((w < 0)) {
      w = (-w);
    }
    if ((h < 0)) {
      h = (-h);
    }
    return new Size(w, h);
  }
  
  public Size operator_plus(final Vec2 a) {
    return Size.from((this.width + a.x), (this.height + a.y));
  }
  
  public Size operator_minus(final Vec2 a) {
    return Size.from((this.width - a.x), (this.height - a.y));
  }
  
  public Size operator_plus(final int[] a) {
    int _get = a[0];
    int _plus = (this.width + _get);
    int _get_1 = a[1];
    int _plus_1 = (this.height + _get_1);
    return Size.from(_plus, _plus_1);
  }
  
  public Size operator_minus(final int[] a) {
    int _get = a[0];
    int _minus = (this.width - _get);
    int _get_1 = a[1];
    int _minus_1 = (this.height - _get_1);
    return Size.from(_minus, _minus_1);
  }
  
  public Size operator_add(final int[] a) {
    Size _xblockexpression = null;
    {
      int _width = this.width;
      int _get = a[0];
      this.width = (_width + _get);
      int _height = this.height;
      int _get_1 = a[1];
      this.height = (_height + _get_1);
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public Size operator_substract(final int[] a) {
    Size _xblockexpression = null;
    {
      int _width = this.width;
      int _get = a[0];
      this.width = (_width - _get);
      int _height = this.height;
      int _get_1 = a[1];
      this.height = (_height - _get_1);
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
}
