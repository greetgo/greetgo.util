package kz.greepto.gpen.drawport.swt

import kz.greepto.gpen.drawport.AbstractGeom
import kz.greepto.gpen.drawport.Vec2
import org.eclipse.swt.graphics.GC
import kz.greepto.gpen.drawport.Size
import kz.greepto.gpen.drawport.NoToPoints
import kz.greepto.gpen.drawport.TooManyToPoints

class SwtGeom extends AbstractGeom {
  val GC gc

  new(GC gc, Vec2 from) {
    this.gc = gc
    this.from = from
  }

  override drawLine(Vec2 from, Vec2 to) {
    gc.drawLine(from.x, from.y, to.x, to.y)
  }

  override rect() {
    var _size = size
    if (_size === null) {
      if (toList.size == 0) throw new NoToPoints
      if (toList.size > 1) throw new TooManyToPoints(toList.size)
      _size = Size.fromTo(from, toList.get(0));
    }
    return new SwtRectGeom(gc, from, _size)
  }
}
