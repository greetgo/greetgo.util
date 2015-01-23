package kz.greepto.gpen.editors.gpen.model.paint

import java.util.ArrayList
import java.util.List
import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.drawport.Rect
import kz.greepto.gpen.drawport.Vec2
import kz.greepto.gpen.editors.gpen.model.IdFigure
import kz.greepto.gpen.drawport.Kursor
import kz.greepto.gpen.drawport.Kolor
import kz.greepto.gpen.editors.gpen.action.Oper
import kz.greepto.gpen.editors.gpen.prop.ValueSetter
import kz.greepto.gpen.editors.gpen.model.PointFigure
import kz.greepto.gpen.editors.gpen.action.OperModify
import kz.greepto.gpen.editors.gpen.action.OperGroup

class MoveDragging implements DraggingThing {

  private static class Dot {
    val String figureId
    val Rect rect

    new(IdFigure figure, Rect rect) {
      figureId = figure.id
      this.rect = rect
    }

    def void draw(DrawPort dp, Vec2 step) {
      dp.style.foreground = Kolor.GRAY
      dp.from(rect + step).draw
    }
  }

  val static ValueSetter X_SETTER = new ValueSetter() {
    override getType() { Integer }

    override getName() { 'x' }

    override setValue(Object object, Object value) {
      var pf = object as PointFigure
      var oldValue = pf.x
      pf.x = value as Integer
      return oldValue
    }
  }

  val static ValueSetter Y_SETTER = new ValueSetter() {
    override getType() { Integer }

    override getName() { 'y' }

    override setValue(Object object, Object value) {
      var pf = object as PointFigure
      var oldValue = pf.y
      pf.y = value as Integer
      return oldValue
    }
  }

  val List<Dot> dotList = new ArrayList
  val Vec2 mouseDownedAt

  new(Vec2 mouseDownedAt) {
    this.mouseDownedAt = mouseDownedAt
  }

  override createOper(Vec2 mouseMovedTo) {
    val step = mouseMovedTo - mouseDownedAt
    if(step.x == 0 && step.y == 0) return null
    val List<Oper> operList = new ArrayList
    for (dot : dotList) {
      if (step.x != 0) {
        operList += new OperModify(X_SETTER, dot.rect.x + step.x, dot.figureId)
      }
      if (step.y != 0) {
        operList += new OperModify(Y_SETTER, dot.rect.y + step.y, dot.figureId)
      }
    }
    if(operList.size === 0) return null
    if(operList.size === 1) return operList.get(0)
    return new OperGroup(operList, "Move " + operList.size + " objects")
  }

  override getKursor() { Kursor.HAND }

  override paintDrag(DrawPort dp, Vec2 mouseMovedTo) {
    val step = mouseMovedTo - mouseDownedAt
    dotList.forEach[draw(dp, step)]
  }

  def void add(IdFigure figure, Rect rect) {
    dotList += new Dot(figure, rect)
  }
}
