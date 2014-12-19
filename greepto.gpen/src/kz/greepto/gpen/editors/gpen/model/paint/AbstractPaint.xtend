package kz.greepto.gpen.editors.gpen.model.paint

import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.drawport.Kursor
import kz.greepto.gpen.drawport.Rect
import kz.greepto.gpen.drawport.Size
import kz.greepto.gpen.drawport.Vec2
import kz.greepto.gpen.editors.gpen.style.StyleCalc

abstract class AbstractPaint implements PaintFigure {
  protected var DrawPort dp
  protected var StyleCalc styleCalc

  new() {
  }

  override void setEnvironment(DrawPort dp, StyleCalc styleCalc) {
    this.dp = dp
    this.styleCalc = styleCalc
  }

  override Rect getPlace() { work(null).rect }

  override MouseInfo paint(Vec2 mouse) { work(mouse).mouseInfo }

  abstract def PlaceInfo work(Vec2 mouse)

  protected def void drawAroundFocus(Rect rect) {
    val int step = 10
    val int period = 300
    var offset = -((System.currentTimeMillis % period) as double / period ) * step
    var skvaj = 0.5

    offset = dp.from(rect.point + rect.size + #[2, 2])//
    .to(Vec2.from(-step, 0))//
    .dashLine(offset, skvaj, rect.width + 4)

    offset = dp.from(rect.point + #[-2, rect.height + 2])//
    .to(Vec2.from(0, -step))//
    .dashLine(offset, skvaj, rect.height + 4)

    offset = dp.from(rect.point - #[2, 2])//
    .to(Vec2.from(step, 0))//
    .dashLine(offset, skvaj, rect.width + 4)

    offset = dp.from(rect.point + #[rect.width + 3, -2])//
    .to(Vec2.from(0, step))//
    .dashLine(offset, skvaj, rect.height + 4)
  }

  enum RectChangeType {
    POSITION,
    CORNER_LEFT_TOP,
    CORNER_LEFT_BOTTOM,
    CORNER_RIGHT_TOP,
    CORNER_RIGHT_BOTTOM,
    SIDE_TOP,
    SIDE_BOTTOM,
    SIDE_LEFT,
    SIDE_RIGHT
  }

  protected def MouseInfo rectMouseInfo(Vec2 mouse, Rect rect, boolean resizable) {
    if(!rect.contains(mouse)) return null;
    if (resizable) {
      var mi = resizeMouseInfo(mouse, rect)
      if(mi !== null) return mi
    }
    return new MouseInfo(mouse, Kursor.SIZEALL, RectChangeType.POSITION)
  }

  enum Direction {
    LEFT,
    UP,
    RIGHT,
    DOWN
  }

  val POL_TOL = 4 //половина толщины чувствительного слоя границы
  val CORNER_LEN = 15 //размер угла

  private def MouseInfo resizeMouseInfo(Vec2 mouse, Rect rect) {

    //CORNERS
    if (calcDirRect(rect.point - #[2, 2], CORNER_LEN, Direction.DOWN).contains(mouse)) {
      return new MouseInfo(mouse, Kursor.SIZENW, RectChangeType.CORNER_LEFT_TOP)
    }
    if (calcDirRect(rect.point - #[2, 2], CORNER_LEN, Direction.RIGHT).contains(mouse)) {
      return new MouseInfo(mouse, Kursor.SIZENW, RectChangeType.CORNER_LEFT_TOP)
    }

    if (calcDirRect(rect.point + #[-2, rect.height + 2], CORNER_LEN, Direction.UP).contains(mouse)) {
      return new MouseInfo(mouse, Kursor.SIZESW, RectChangeType.CORNER_LEFT_BOTTOM)
    }
    if (calcDirRect(rect.point + #[-2, rect.height + 2], CORNER_LEN, Direction.RIGHT).contains(mouse)) {
      return new MouseInfo(mouse, Kursor.SIZESW, RectChangeType.CORNER_LEFT_BOTTOM)
    }

    if (calcDirRect(rect.point + #[rect.width + 2, 2], CORNER_LEN, Direction.LEFT).contains(mouse)) {
      return new MouseInfo(mouse, Kursor.SIZENE, RectChangeType.CORNER_RIGHT_TOP)
    }
    if (calcDirRect(rect.point + #[rect.width + 2, 2], CORNER_LEN, Direction.DOWN).contains(mouse)) {
      return new MouseInfo(mouse, Kursor.SIZENE, RectChangeType.CORNER_RIGHT_TOP)
    }

    if (calcDirRect(rect.point + #[rect.width + 2, rect.height + 2], CORNER_LEN, Direction.UP).contains(mouse)) {
      return new MouseInfo(mouse, Kursor.SIZESE, RectChangeType.CORNER_RIGHT_BOTTOM)
    }
    if (calcDirRect(rect.point + #[rect.width + 2, rect.height + 2], CORNER_LEN, Direction.LEFT).contains(mouse)) {
      return new MouseInfo(mouse, Kursor.SIZESE, RectChangeType.CORNER_RIGHT_BOTTOM)
    }

    //SIDES
    if (calcDirRect(rect.point - #[2, 2], rect.width, Direction.RIGHT).contains(mouse)) {
      return new MouseInfo(mouse, Kursor.SIZEN, RectChangeType.SIDE_TOP)
    }
    if (calcDirRect(rect.point - #[2, 2], rect.height, Direction.DOWN).contains(mouse)) {
      return new MouseInfo(mouse, Kursor.SIZEW, RectChangeType.SIDE_LEFT)
    }

    if (calcDirRect(rect.point + #[rect.width - 2, -2], rect.height, Direction.DOWN).contains(mouse)) {
      return new MouseInfo(mouse, Kursor.SIZEE, RectChangeType.SIDE_RIGHT)
    }
    if (calcDirRect(rect.point + #[-2, rect.height - 2], rect.width, Direction.RIGHT).contains(mouse)) {
      return new MouseInfo(mouse, Kursor.SIZES, RectChangeType.SIDE_BOTTOM)
    }

    null
  }

  def Rect calcDirRect(Vec2 from, int len, Direction direction) {
    switch (direction) {
      case LEFT: Rect.pointSize(from - #[len, POL_TOL], Size.from(len, 2 * POL_TOL + 1))
      case UP: Rect.pointSize(from - #[POL_TOL, len], Size.from(2 * POL_TOL + 1, len))
      case RIGHT: Rect.pointSize(from - #[0, POL_TOL], Size.from(len, 2 * POL_TOL + 1))
      case DOWN: Rect.pointSize(from - #[POL_TOL, 0], Size.from(2 * POL_TOL + 1, len))
    }
  }

}
