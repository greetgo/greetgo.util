package kz.greepto.gpen.drawport.swt

import kz.greepto.gpen.drawport.RectGeom
import kz.greepto.gpen.drawport.Size
import kz.greepto.gpen.drawport.Vec2
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

    if(point === null) throw new NullPointerException
    if(size === null) throw new NullPointerException
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

  SwtArcGeom swtArcGeom = null

  override arc(int from, int angle) {
    if(swtArcGeom == null) swtArcGeom = new SwtArcGeom(gc)
    swtArcGeom.point = point
    swtArcGeom.size = size
    swtArcGeom.from = from
    swtArcGeom.angle = angle
    return swtArcGeom
  }

  override drawOval() {
    gc.drawOval(point.x, point.y, size.width, size.height)
    this
  }

  override fillOval() {
    gc.fillOval(point.x, point.y, size.width, size.height)
    this
  }

  override round(int arcWidthAndHeight) {
    round(arcWidthAndHeight, arcWidthAndHeight)
  }

  override round(int arcWidth, int arcHeight) { round(Size.from(arcWidth, arcHeight)) }

  private SwtRoundRectGeom swtRoundRectGeom = null

  override round(Size arcSize) {
    if(swtRoundRectGeom == null) swtRoundRectGeom = new SwtRoundRectGeom(gc)
    swtRoundRectGeom.point = point
    swtRoundRectGeom.size = size
    swtRoundRectGeom.arcSize = arcSize
    return swtRoundRectGeom
  }
}
