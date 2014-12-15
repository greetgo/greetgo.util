package kz.greepto.gpen.editors.gpen.model

import kz.greepto.gpen.drawport.Size
import kz.greepto.gpen.drawport.Rect

abstract class RectFigure extends PointFigure {

  public int width
  public int height

  def void setSize(Size size) {
    width = size?.width
    height = size?.height
  }

  def Size getSize() {
    return Size.from(width, height)
  }

  def void setRect(Rect rect) {
    point = rect?.point
    size = rect?.size
  }

  def Rect getRect() { Rect.pointSize(point, size) }

  new(String id) {
    super(id)
  }

  new(RectFigure a) {
    super(a)
    width = a.width
    height = a.height
  }
}
