package kz.greepto.gpen.drawport.swt;

import com.google.common.base.Objects;
import kz.greepto.gpen.drawport.ArcGeom;
import kz.greepto.gpen.drawport.RectGeom;
import kz.greepto.gpen.drawport.RoundRectGeom;
import kz.greepto.gpen.drawport.Size;
import kz.greepto.gpen.drawport.Vec2;
import kz.greepto.gpen.drawport.swt.SwtArcGeom;
import kz.greepto.gpen.drawport.swt.SwtRoundRectGeom;
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
  
  private SwtArcGeom swtArcGeom = null;
  
  public ArcGeom arc(final int from, final int angle) {
    boolean _equals = Objects.equal(this.swtArcGeom, null);
    if (_equals) {
      SwtArcGeom _swtArcGeom = new SwtArcGeom(this.gc);
      this.swtArcGeom = _swtArcGeom;
    }
    this.swtArcGeom.point = this.point;
    this.swtArcGeom.size = this.size;
    this.swtArcGeom.from = from;
    this.swtArcGeom.angle = angle;
    return this.swtArcGeom;
  }
  
  public RectGeom drawOval() {
    SwtRectGeom _xblockexpression = null;
    {
      this.gc.drawOval(this.point.x, this.point.y, this.size.width, this.size.height);
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public RectGeom fillOval() {
    SwtRectGeom _xblockexpression = null;
    {
      this.gc.fillOval(this.point.x, this.point.y, this.size.width, this.size.height);
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public RoundRectGeom round(final int arcWidthAndHeight) {
    return this.round(arcWidthAndHeight, arcWidthAndHeight);
  }
  
  public RoundRectGeom round(final int arcWidth, final int arcHeight) {
    Size _from = Size.from(arcWidth, arcHeight);
    return this.round(_from);
  }
  
  private SwtRoundRectGeom swtRoundRectGeom = null;
  
  public RoundRectGeom round(final Size arcSize) {
    boolean _equals = Objects.equal(this.swtRoundRectGeom, null);
    if (_equals) {
      SwtRoundRectGeom _swtRoundRectGeom = new SwtRoundRectGeom(this.gc);
      this.swtRoundRectGeom = _swtRoundRectGeom;
    }
    this.swtRoundRectGeom.point = this.point;
    this.swtRoundRectGeom.size = this.size;
    this.swtRoundRectGeom.arcSize = arcSize;
    return this.swtRoundRectGeom;
  }
}
