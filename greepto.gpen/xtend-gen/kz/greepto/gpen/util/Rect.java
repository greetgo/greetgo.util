package kz.greepto.gpen.util;

import com.google.common.base.Objects;
import org.eclipse.swt.graphics.Point;

@SuppressWarnings("all")
public class Rect {
  public int x = 0;
  
  public int y = 0;
  
  public int width = 0;
  
  public int height = 0;
  
  public static Rect zero() {
    return new Rect();
  }
  
  public static Rect copy(final Rect r) {
    return new Rect(r);
  }
  
  public static Rect from(final int x, final int y, final int width, final int height) {
    return new Rect(x, y, width, height);
  }
  
  public static Rect pointSize(final Point point, final Point size) {
    return Rect.from(point.x, point.y, size.x, size.y);
  }
  
  private Rect() {
  }
  
  private Rect(final int x, final int y, final int width, final int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }
  
  private Rect(final Rect a) {
    this.x = a.x;
    this.y = a.y;
    this.width = a.width;
    this.height = a.height;
  }
  
  public Rect asd() {
    Rect _xblockexpression = null;
    {
      Rect a = Rect.zero();
      Rect b = Rect.zero();
      _xblockexpression = b.operator_add(a);
    }
    return _xblockexpression;
  }
  
  public Rect operator_add(final Rect r) {
    int x1 = Math.min(this.x, r.x);
    int x2 = Math.max((this.x + this.width), (r.x + r.width));
    int y1 = Math.min(this.y, r.y);
    int y2 = Math.max((this.y + this.height), (r.y + r.height));
    this.x = x1;
    this.y = y1;
    this.width = (x2 - x1);
    this.height = (y2 - y1);
    return this;
  }
  
  public Rect operator_plus(final Rect r) {
    Rect _copy = Rect.copy(this);
    return _copy.operator_add(r);
  }
  
  public boolean contains(final Point p) {
    boolean _equals = Objects.equal(p, null);
    if (_equals) {
      return false;
    }
    if ((p.x < this.x)) {
      return false;
    }
    if ((p.y < this.y)) {
      return false;
    }
    if ((p.x > (this.x + this.width))) {
      return false;
    }
    if ((p.y > (this.y + this.height))) {
      return false;
    }
    return true;
  }
}
