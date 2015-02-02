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
    var from = this.from
    if (_size === null) {
      if(toList.size == 0) throw new NoToPoints
      if(toList.size > 1) throw new TooManyToPoints(toList.size)
      var to = toList.get(0)
      var copied = false
      if (from.x > to.x) {
        to = to.copy
        from = from.copy
        copied = true
        var tmp = to.x;
        to.x = from.x;
        from.x = tmp
      }
      if (from.y > to.y) {
        if (!copied) {
          to = to.copy
          from = from.copy
        }
        var tmp = to.y;
        to.y = from.y;
        from.y = tmp
      }
      _size = Size.fromTo(from, to);
    }
    return new SwtRectGeom(gc, from, _size)
  }
}
