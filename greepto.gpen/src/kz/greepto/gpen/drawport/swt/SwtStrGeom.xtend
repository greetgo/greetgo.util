package kz.greepto.gpen.drawport.swt

import org.eclipse.swt.graphics.GC
import kz.greepto.gpen.drawport.StrGeom
import kz.greepto.gpen.drawport.Vec2
import kz.greepto.gpen.drawport.Size

class SwtStrGeom implements StrGeom {
  val GC gc
  val String str
  val FontPreparator fp

  new(GC gc, String str, FontPreparator fp) {
    this.gc = gc
    this.str = str
    this.fp = fp
  }

  override draw(int x, int y) {
    fp.prepareFont
    gc.drawText(str, x, y, true)
  }

  override draw(Vec2 point) {
    draw(point.x, point.y)
  }

  override size() {
    fp.prepareFont
    var s = gc.stringExtent(str)
    return new Size(s.x, s.y)
  }

}