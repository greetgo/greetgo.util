package kz.greepto.gpen.drawport.swt

import kz.greepto.gpen.drawport.RectGeom
import kz.greepto.gpen.drawport.Vec2
import kz.greepto.gpen.drawport.Size
import org.eclipse.swt.graphics.GC
import org.eclipse.swt.graphics.Rectangle

class SwtRectGeom implements RectGeom {

  val GC gc
  val Vec2 point
  val Size size

  package new(GC gc, Vec2 point, Size size) {
    this.gc = gc
    this.point = point
    this.size = size

    if (point === null) throw new NullPointerException
    if (size === null) throw new NullPointerException
  }

  override draw() {
    gc.drawRectangle(point.x, point.y, size.width, size.height)
    this
  }

  override fill() {
    gc.fillRectangle(point.x, point.y, size.width, size.height)
    this
  }

  override clip() {
    gc.clipping = new Rectangle(point.x, point.y, size.width, size.height)
    this
  }

}