package kz.greepto.gpen.drawport.swt;

import kz.greepto.gpen.drawport.RectGeom;
import kz.greepto.gpen.drawport.Size;
import kz.greepto.gpen.drawport.Vec2;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

@SuppressWarnings("all")
public class SwtRectGeom implements RectGeom {
  private final GC gc;
  
  private final Vec2 point;
  
  private final Size size;
  
  SwtRectGeom(final GC gc, final Vec2 point, final Size size) {
    this.gc = gc;
    this.point = point;
    this.size = size;
    boolean _tripleEquals = (point == null);
    if (_tripleEquals) {
      throw new NullPointerException();
    }
    boolean _tripleEquals_1 = (size == null);
    if (_tripleEquals_1) {
      throw new NullPointerException();
    }
  }
  
  public RectGeom draw() {
    SwtRectGeom _xblockexpression = null;
    {
      this.gc.drawRectangle(this.point.x, this.point.y, this.size.width, this.size.height);
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public RectGeom fill() {
    SwtRectGeom _xblockexpression = null;
    {
      this.gc.fillRectangle(this.point.x, this.point.y, this.size.width, this.size.height);
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public RectGeom clip() {
    SwtRectGeom _xblockexpression = null;
    {
      Rectangle _rectangle = new Rectangle(this.point.x, this.point.y, this.size.width, this.size.height);
      this.gc.setClipping(_rectangle);
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
}
