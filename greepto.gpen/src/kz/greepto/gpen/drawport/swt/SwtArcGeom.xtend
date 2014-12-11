package kz.greepto.gpen.drawport.swt

import kz.greepto.gpen.drawport.ArcGeom
import kz.greepto.gpen.drawport.Size
import kz.greepto.gpen.drawport.Vec2
import org.eclipse.swt.graphics.GC

class SwtArcGeom implements ArcGeom {

  val GC gc

  package Vec2 point
  package Size size
  package int from
  package int angle

  new(GC gc) {
    this.gc = gc
  }

  override draw() {
    gc.drawArc(point.x, point.y, size.width, size.height, from, angle)
    this
  }

  override fill() {
    gc.fillArc(point.x, point.y, size.width, size.height, from, angle)
    this
  }

}
