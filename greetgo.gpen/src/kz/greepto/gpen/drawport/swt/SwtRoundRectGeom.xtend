package kz.greepto.gpen.drawport.swt

import kz.greepto.gpen.drawport.RoundRectGeom
import org.eclipse.swt.graphics.GC
import kz.greepto.gpen.drawport.Vec2
import kz.greepto.gpen.drawport.Size

class SwtRoundRectGeom implements RoundRectGeom {
  val GC gc

  package var Vec2 point
  package var Size size
  package var Size arcSize

  package new(GC gc) {
    this.gc = gc
  }

  override draw() {
    gc.drawRoundRectangle(point.x, point.y, size.width, size.height, arcSize.width, arcSize.height)
    this
  }

  override fill() {
    gc.fillRoundRectangle(point.x, point.y, size.width, size.height, arcSize.width, arcSize.height)
    this
  }
}
