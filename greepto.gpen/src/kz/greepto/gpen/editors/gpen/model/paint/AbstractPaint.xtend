package kz.greepto.gpen.editors.gpen.model.paint

import java.util.ArrayList
import java.util.Collection
import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.drawport.Kursor
import kz.greepto.gpen.drawport.Rect
import kz.greepto.gpen.drawport.Size
import kz.greepto.gpen.drawport.Vec2
import kz.greepto.gpen.editors.gpen.action.Oper
import kz.greepto.gpen.editors.gpen.action.OperGroup
import kz.greepto.gpen.editors.gpen.action.OperModify
import kz.greepto.gpen.editors.gpen.model.IdFigure
import kz.greepto.gpen.editors.gpen.model.PointFigure
import kz.greepto.gpen.editors.gpen.model.RectFigure
import kz.greepto.gpen.editors.gpen.prop.ValueSetter
import kz.greepto.gpen.editors.gpen.style.StyleCalc

import static kz.greepto.gpen.editors.gpen.model.paint.AbstractPaint.*
import kz.greepto.gpen.drawport.Kolor

abstract class AbstractPaint implements PaintFigure {
  protected var DrawPort dp
  protected var StyleCalc styleCalc
  protected Kolor dragingKolor = Kolor.GRAY
  protected SelChecker selChecker

  new() {
  }

  override void setEnvironment(DrawPort dp, StyleCalc styleCalc, SelChecker selChecker) {
    this.dp = dp
    this.styleCalc = styleCalc
    this.selChecker = selChecker
  }

  protected def boolean isSel(IdFigure figure) { selChecker?.isSelected(figure) }

  override Rect getPlace() { work(null).place }

  override PaintResult paint(Vec2 mouse) { work(mouse) }

  abstract def PaintResult work(Vec2 mouse)

  protected def void drawAroundFocus(Rect rect) {
    val int step = 20
    val int period = 600
    var ofs = -((System.currentTimeMillis % period) as double / period ) * step
    var skvaj = 0.5

    var d = 2

    var x = dp.from(rect.rightBottom + #[d, d])
    ofs = x.to(rect.leftBottom + #[-d, d]).dashLine(ofs, skvaj, step)
    ofs = x.to(rect.leftTop + #[-d, -d]).dashLine(ofs, skvaj, step)
    ofs = x.to(rect.rightTop + #[d, -d]).dashLine(ofs, skvaj, step)
    ofs = x.to(rect.rightBottom + #[d, d]).dashLine(ofs, skvaj, step)
  }

  protected def PaintResult simpleRect(Rect rect) {
    new PaintResult() {
      override createOper(Vec2 mouseMovedTo) {
        throw new UnsupportedOperationException("No oper")
      }

      override getKursor() { Kursor.ARROW }

      override getPlace() { rect }

      override isHasOper() { false }

      override toString() { "simpleRect" }

      override paintDrag(DrawPort dp, Vec2 mouseMovedTo) {
        throw new UnsupportedOperationException("No paintDrag")
      }
    }
  }

  protected def PaintResult modiBounds(Vec2 mouseDownedAt, Rect rect, IdFigure figure) {
    if(!rect.contains(mouseDownedAt)) return simpleRect(rect)

    if (figure instanceof RectFigure) {
      var res = modiBoundsRect(mouseDownedAt, rect, figure)
      if(res !== null) return res
    }

    if (figure instanceof PointFigure) {
      return modiPosition(mouseDownedAt, rect, figure)
    }

    return simpleRect(rect)
  }

  protected def PaintResult modiPosition(Vec2 mouseDownedAt, Rect rect, PointFigure figure) {
    new PaintResult() {
      override getKursor() { Kursor.ARROW }

      override getPlace() { rect }

      override isHasOper() { true }

      override createOper(Vec2 mouseMovedTo) {
        var d = mouseMovedTo - mouseDownedAt
        var dxOper = createDxOper(d.x)
        var dyOper = createDyOper(d.y)
        if(dxOper === null) return dyOper
        if(dyOper === null) return dxOper
        return new OperGroup(#[dxOper, dyOper], "Move dx, dy = " + d.x + ', ' + d.y)
      }

      def Oper createDxOper(int dx) {
        if(dx === 0) return null
        return new OperModify(SETTER_X, rect.x + dx, figure.id, 'Move dx = ' + dx)
      }

      def Oper createDyOper(int dy) {
        if(dy === 0) return null
        return new OperModify(SETTER_Y, rect.y + dy, figure.id, 'Move dy = ' + dy)
      }

      override toString() { "modiPosition" }

      override paintDrag(DrawPort dp, Vec2 mouseMovedTo) {
        var r = rect.copy
        r.point = r.point + (mouseMovedTo - mouseDownedAt)

        dp.style.foreground = dragingKolor
        dp.from(r).draw
      }

    }
  }

  private static val SETTER_X = new ValueSetter() {
    override getType() { Integer.TYPE }

    override getName() { "x" }

    override setValue(Object object, Object value) {
      var fig = object as PointFigure
      var ret = fig.x
      fig.x = value as Integer
      return ret
    }
  }

  private static val SETTER_Y = new ValueSetter() {
    override getType() { Integer.TYPE }

    override getName() { "y" }

    override setValue(Object object, Object value) {
      var fig = object as PointFigure
      var ret = fig.y
      fig.y = value as Integer
      return ret
    }
  }

  private static val SETTER_WIDTH = new ValueSetter() {
    override getType() { Integer.TYPE }

    override getName() { "width" }

    override setValue(Object object, Object value) {
      var fig = object as RectFigure
      var ret = fig.width
      fig.width = value as Integer
      return ret
    }
  }

  private static val SETTER_HEIGHT = new ValueSetter() {
    override getType() { Integer.TYPE }

    override getName() { "height" }

    override setValue(Object object, Object value) {
      var fig = object as RectFigure
      var ret = fig.height
      fig.height = value as Integer
      return ret
    }
  }

  private def PaintResult modiBoundsRect(Vec2 mouseDownedAt, Rect rect, RectFigure figure) {

    //CORNERS
    if (calcDirRect(rect.point - #[2, 2], CORNER_LEN, Direction.DOWN)//
    .contains(mouseDownedAt)) {
      return newCornerLeftTop(mouseDownedAt, rect, figure)
    }
    if (calcDirRect(rect.point - #[2, 2], CORNER_LEN, Direction.RIGHT)//
    .contains(mouseDownedAt)) {
      return newCornerLeftTop(mouseDownedAt, rect, figure)
    }

    if (calcDirRect(rect.point + #[-2, rect.height + 2], CORNER_LEN, Direction.UP)//
    .contains(mouseDownedAt)) {
      return newCornerLeftBottom(mouseDownedAt, rect, figure)
    }
    if (calcDirRect(rect.point + #[-2, rect.height + 2], CORNER_LEN, Direction.RIGHT)//
    .contains(mouseDownedAt)) {
      return newCornerLeftBottom(mouseDownedAt, rect, figure)
    }

    if (calcDirRect(rect.point + #[rect.width + 2, 2], CORNER_LEN, Direction.LEFT)//
    .contains(mouseDownedAt)) {
      return newCornerRightTop(mouseDownedAt, rect, figure)
    }
    if (calcDirRect(rect.point + #[rect.width + 2, 2], CORNER_LEN, Direction.DOWN)//
    .contains(mouseDownedAt)) {
      return newCornerRightTop(mouseDownedAt, rect, figure)
    }

    if (calcDirRect(rect.point + #[rect.width + 2, rect.height + 2], CORNER_LEN, Direction.UP)//
    .contains(mouseDownedAt)) {
      return newCornerRightBottom(mouseDownedAt, rect, figure)
    }
    if (calcDirRect(rect.point + #[rect.width + 2, rect.height + 2], CORNER_LEN, Direction.LEFT)//
    .contains(mouseDownedAt)) {
      return newCornerRightBottom(mouseDownedAt, rect, figure)
    }

    //SIDES
    if (calcDirRect(rect.point - #[2, 2], rect.width, Direction.RIGHT)//
    .contains(mouseDownedAt)) {
      return newSideTop(mouseDownedAt, rect, figure)
    }
    if (calcDirRect(rect.point - #[2, 2], rect.height, Direction.DOWN)//
    .contains(mouseDownedAt)) {
      return newSideLeft(mouseDownedAt, rect, figure)
    }

    if (calcDirRect(rect.point + #[rect.width - 2, -2], rect.height, Direction.DOWN)//
    .contains(mouseDownedAt)) {
      return newSideRight(mouseDownedAt, rect, figure)
    }
    if (calcDirRect(rect.point + #[-2, rect.height - 2], rect.width, Direction.RIGHT)//
    .contains(mouseDownedAt)) {
      return newSideBottom(mouseDownedAt, rect, figure)
    }

    null
  }

  private static def Oper group(Collection<Oper> opers, String name) {
    if(opers.size === 0) return null
    if(opers.size === 1) return opers.iterator.next
    new OperGroup(opers, name)
  }

  private def PaintResult newCornerLeftTop(Vec2 mouseDownedAt, Rect rect, RectFigure figure) {
    new PaintResult() {
      override getKursor() { Kursor.SIZENW }

      override getPlace() { rect }

      override isHasOper() { true }

      override toString() { "newCornerLeftTop" }

      override createOper(Vec2 mouseMovedTo) {
        var d = mouseMovedTo - mouseDownedAt
        var opers = new ArrayList<Oper>

        if (d.x !== 0) {
          opers += new OperModify(SETTER_X, rect.x + d.x, figure.id)
          opers += new OperModify(SETTER_WIDTH, rect.width - d.x, figure.id)
        }

        if (d.y !== 0) {
          opers += new OperModify(SETTER_Y, rect.y + d.y, figure.id)
          opers += new OperModify(SETTER_HEIGHT, rect.height - d.y, figure.id)
        }

        return group(opers, "Corner Left Top")
      }

      override paintDrag(DrawPort dp, Vec2 mouseMovedTo) {
        var d = mouseMovedTo - mouseDownedAt

        dp.style.foreground = dragingKolor
        dp.from(rect.point + d).size(rect.size - d).rect.draw
      }

    }
  }

  private def PaintResult newCornerLeftBottom(Vec2 mouseDownedAt, Rect rect, RectFigure figure) {
    new PaintResult() {
      override getKursor() { Kursor.SIZESW }

      override getPlace() { rect }

      override isHasOper() { true }

      override toString() { "newCornerLeftBottom" }

      override createOper(Vec2 mouseMovedTo) {
        var d = mouseMovedTo - mouseDownedAt
        var opers = new ArrayList<Oper>

        if (d.x !== 0) {
          opers += new OperModify(SETTER_X, rect.x + d.x, figure.id)
          opers += new OperModify(SETTER_WIDTH, rect.width - d.x, figure.id)
        }

        if (d.y !== 0) {
          opers += new OperModify(SETTER_HEIGHT, rect.height + d.y, figure.id)
        }

        return group(opers, "Corner Left Bottom")
      }

      override paintDrag(DrawPort dp, Vec2 mouseMovedTo) {
        dp.style.foreground = dragingKolor

        var d = mouseMovedTo - mouseDownedAt

        dp.from(rect.x + d.x, rect.y).size(rect.width - d.x, rect.height + d.y).rect.draw
      }
    }
  }

  private def PaintResult newCornerRightTop(Vec2 mouseDownedAt, Rect rect, RectFigure figure) {
    new PaintResult() {
      override getKursor() { Kursor.SIZENE }

      override getPlace() { rect }

      override isHasOper() { true }

      override toString() { "newCornerRightTop" }

      override createOper(Vec2 mouseMovedTo) {
        var d = mouseMovedTo - mouseDownedAt
        var opers = new ArrayList<Oper>

        if (d.x !== 0) {
          opers += new OperModify(SETTER_WIDTH, rect.width + d.x, figure.id)
        }

        if (d.y !== 0) {
          opers += new OperModify(SETTER_Y, rect.y + d.y, figure.id)
          opers += new OperModify(SETTER_HEIGHT, rect.height - d.y, figure.id)
        }

        return group(opers, "Corner Right Top")
      }

      override paintDrag(DrawPort dp, Vec2 mouseMovedTo) {
        dp.style.foreground = dragingKolor

        var d = mouseMovedTo - mouseDownedAt

        dp.from(rect.x, rect.y + d.y).size(rect.width + d.x, rect.height - d.y).rect.draw
      }
    }

  }

  private def PaintResult newCornerRightBottom(Vec2 mouseDownedAt, Rect rect, RectFigure figure) {
    new PaintResult() {
      override getKursor() { Kursor.SIZESE }

      override getPlace() { rect }

      override isHasOper() { true }

      override toString() { "newCornerRightBottom" }

      override createOper(Vec2 mouseMovedTo) {
        var d = mouseMovedTo - mouseDownedAt
        var opers = new ArrayList<Oper>

        if (d.x !== 0) {
          opers += new OperModify(SETTER_WIDTH, rect.width + d.x, figure.id)
        }

        if (d.y !== 0) {
          opers += new OperModify(SETTER_HEIGHT, rect.height + d.y, figure.id)
        }

        return group(opers, "Corner Right Bottom")
      }

      override paintDrag(DrawPort dp, Vec2 mouseMovedTo) {
        dp.style.foreground = dragingKolor

        var d = mouseMovedTo - mouseDownedAt

        dp.from(rect.point).size(rect.size + d).rect.draw
      }
    }
  }

  private def PaintResult newSideTop(Vec2 mouseDownedAt, Rect rect, RectFigure figure) {
    new PaintResult() {
      override getKursor() { Kursor.SIZEN }

      override getPlace() { rect }

      override isHasOper() { true }

      override toString() { "newSideTop" }

      override createOper(Vec2 mouseMovedTo) {
        var dy = mouseMovedTo.y - mouseDownedAt.y

        if(dy === 0) return null

        var opers = new ArrayList<Oper>
        opers += new OperModify(SETTER_Y, rect.y + dy, figure.id)
        opers += new OperModify(SETTER_HEIGHT, rect.height - dy, figure.id)
        return group(opers, "Side Top")
      }

      override paintDrag(DrawPort dp, Vec2 mouseMovedTo) {
        dp.style.foreground = dragingKolor

        var d = mouseMovedTo - mouseDownedAt

        dp.from(rect.x, rect.y + d.y).size(rect.width, rect.height - d.y).rect.draw
      }
    }
  }

  private def PaintResult newSideLeft(Vec2 mouseDownedAt, Rect rect, RectFigure figure) {
    new PaintResult() {
      override getKursor() { Kursor.SIZEW }

      override getPlace() { rect }

      override isHasOper() { true }

      override toString() { "newSideLeft" }

      override createOper(Vec2 mouseMovedTo) {
        var dx = mouseMovedTo.x - mouseDownedAt.x

        if(dx === 0) return null

        var opers = new ArrayList<Oper>
        opers += new OperModify(SETTER_X, rect.x + dx, figure.id)
        opers += new OperModify(SETTER_WIDTH, rect.width - dx, figure.id)
        return group(opers, "Side Left")
      }

      override paintDrag(DrawPort dp, Vec2 mouseMovedTo) {
        dp.style.foreground = dragingKolor

        var d = mouseMovedTo - mouseDownedAt

        dp.from(rect.x + d.x, rect.y).size(rect.width - d.x, rect.height).rect.draw
      }
    }

  }

  private def PaintResult newSideRight(Vec2 mouseDownedAt, Rect rect, RectFigure figure) {
    new PaintResult() {
      override getKursor() { Kursor.SIZEE }

      override getPlace() { rect }

      override isHasOper() { true }

      override toString() { "newSideRight" }

      override createOper(Vec2 mouseMovedTo) {
        var dx = mouseMovedTo.x - mouseDownedAt.x

        if(dx === 0) return null

        return new OperModify(SETTER_WIDTH, rect.width + dx, figure.id)
      }

      override paintDrag(DrawPort dp, Vec2 mouseMovedTo) {
        dp.style.foreground = dragingKolor

        var d = mouseMovedTo - mouseDownedAt

        dp.from(rect.point).size(rect.width + d.x, rect.height).rect.draw
      }
    }
  }

  private def PaintResult newSideBottom(Vec2 mouseDownedAt, Rect rect, RectFigure figure) {
    new PaintResult() {
      override getKursor() { Kursor.SIZES }

      override getPlace() { rect }

      override isHasOper() { true }

      override toString() { "newSideBottom" }

      override createOper(Vec2 mouseMovedTo) {
        var dy = mouseMovedTo.y - mouseDownedAt.y

        if(dy === 0) return null

        return new OperModify(SETTER_HEIGHT, rect.height + dy, figure.id)
      }

      override paintDrag(DrawPort dp, Vec2 mouseMovedTo) {
        dp.style.foreground = dragingKolor

        var d = mouseMovedTo - mouseDownedAt

        dp.from(rect.point).size(rect.width, rect.height + d.y).rect.draw
      }
    }
  }

  enum Direction {
    LEFT,
    UP,
    RIGHT,
    DOWN
  }

  val POL_TOL = 4 //половина толщины чувствительного слоя границы
  val CORNER_LEN = 15 //размер угла

  def Rect calcDirRect(Vec2 from, int len, Direction direction) {
    switch (direction) {
      case LEFT: Rect.pointSize(from - #[len, POL_TOL], Size.from(len, 2 * POL_TOL + 1))
      case UP: Rect.pointSize(from - #[POL_TOL, len], Size.from(2 * POL_TOL + 1, len))
      case RIGHT: Rect.pointSize(from - #[0, POL_TOL], Size.from(len, 2 * POL_TOL + 1))
      case DOWN: Rect.pointSize(from - #[POL_TOL, 0], Size.from(2 * POL_TOL + 1, len))
    }
  }
}
