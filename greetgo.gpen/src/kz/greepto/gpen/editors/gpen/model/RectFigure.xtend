package kz.greepto.gpen.editors.gpen.model

import kz.greepto.gpen.drawport.Rect
import kz.greepto.gpen.drawport.Size

abstract class RectFigure extends PointFigure {

  private int width
  private int height

  def void setSize(Size size) {
    width = size?.width
    height = size?.height
  }

  def Size getSize() {
    return Size.from(width, height)
  }

  def void setRect(Rect rect) {
    x = y = width = height = 0

    if(rect === null) return;

    x = rect.x
    y = rect.y
    width = rect.width
    height = rect.height
  }

  def Rect getRect() { Rect.from(x, y, width, height) }

  def int getWidth() { width }

  def int getHeight() { height }

  def void setWidth(int width) { this.width = width }

  def void setHeight(int height) { this.height = height }

  new(String id) {
    super(id)
  }

  new(RectFigure a) {
    super(a)
    width = a.width
    height = a.height
  }
}
