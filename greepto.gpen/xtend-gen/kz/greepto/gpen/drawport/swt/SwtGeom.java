package kz.greepto.gpen.drawport.swt;

import kz.greepto.gpen.drawport.AbstractGeom;
import kz.greepto.gpen.drawport.NoToPoints;
import kz.greepto.gpen.drawport.RectGeom;
import kz.greepto.gpen.drawport.Size;
import kz.greepto.gpen.drawport.StrGeom;
import kz.greepto.gpen.drawport.TooManyToPoints;
import kz.greepto.gpen.drawport.Vec2;
import kz.greepto.gpen.drawport.swt.FontPreparator;
import kz.greepto.gpen.drawport.swt.SwtRectGeom;
import kz.greepto.gpen.drawport.swt.SwtStrGeom;
import org.eclipse.swt.graphics.GC;

@SuppressWarnings("all")
public class SwtGeom extends AbstractGeom {
  private final GC gc;
  
  private final FontPreparator fp;
  
  public SwtGeom(final GC gc, final Vec2 from, final FontPreparator fp) {
    this.gc = gc;
    this.from = from;
    this.fp = fp;
  }
  
  public void drawLine(final Vec2 from, final Vec2 to) {
    this.gc.drawLine(from.x, from.y, to.x, to.y);
  }
  
  public RectGeom rect() {
    Size _size = this.size;
    boolean _tripleEquals = (_size == null);
    if (_tripleEquals) {
      int _size_1 = this.toList.size();
      boolean _equals = (_size_1 == 0);
      if (_equals) {
        throw new NoToPoints();
      }
      int _size_2 = this.toList.size();
      boolean _greaterEqualsThan = (_size_2 >= 1);
      if (_greaterEqualsThan) {
        throw new TooManyToPoints();
      }
      Vec2 _get = this.toList.get(0);
      Size _fromTo = Size.fromTo(this.from, _get);
      _size = _fromTo;
    }
    return new SwtRectGeom(this.gc, this.from, _size);
  }
  
  public StrGeom str(final String str) {
    return new SwtStrGeom(this.gc, str, this.fp);
  }
}
