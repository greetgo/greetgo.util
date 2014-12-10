package kz.greepto.gpen.drawport.swt

import kz.greepto.gpen.drawport.AbstractGeom
import kz.greepto.gpen.drawport.Vec2
import org.eclipse.swt.graphics.GC
import kz.greepto.gpen.drawport.Size
import kz.greepto.gpen.drawport.NoToPoints
import kz.greepto.gpen.drawport.TooManyToPoints

class SwtGeom extends AbstractGeom {
  val GC gc
  val FontPreparator fp

  new(GC gc, Vec2 from, FontPreparator fp) {
    this.gc = gc
    this.from = from
    this.fp = fp
  }

  override drawLine(Vec2 from, Vec2 to) {
    gc.drawLine(from.x, from.y, to.x, to.y)
  }

  override rect() {
    var _size = size
    if (_size === null) {
      if (toList.size == 0) throw new NoToPoints
      if (toList.size >= 1) throw new TooManyToPoints
      _size = Size.fromTo(from, toList.get(0));
    }
    return new SwtRectGeom(gc, from, _size)
  }

  override str(String str) {
    return new SwtStrGeom(gc, str, fp)
  }

}
