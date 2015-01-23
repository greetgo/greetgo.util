package kz.greepto.gpen.editors.gpen.model.visitor

import kz.greepto.gpen.drawport.Vec2
import kz.greepto.gpen.editors.gpen.model.Button
import kz.greepto.gpen.editors.gpen.model.Combo
import kz.greepto.gpen.editors.gpen.model.Label
import kz.greepto.gpen.editors.gpen.model.Scene
import kz.greepto.gpen.editors.gpen.model.Table
import kz.greepto.gpen.editors.gpen.model.paint.PaintButton
import kz.greepto.gpen.editors.gpen.model.paint.PaintFigure
import kz.greepto.gpen.editors.gpen.model.paint.PaintLabel
import kz.greepto.gpen.editors.gpen.model.paint.PaintResult
import kz.greepto.gpen.editors.gpen.model.paint.PaintTable

class VisitorPaint implements FigureVisitor<PaintResult> {

  final VisitorPlacer placer
  public Vec2 mouse

  new(VisitorPlacer placer) {
    this.placer = placer
  }

  override visitScene(Scene scene) {
    if (mouse === null) {
      scene.list.forEach[visit(this)]
      null
    } else {
      scene.list.map[visit(this)].filter[hasOper && place.contains(mouse)].last
    }
  }

  private def PaintResult visit(PaintFigure paint) {
    paint.setEnvironment(placer.dp, placer.styleCalc, placer.selChecker)
    return paint.paint(mouse)
  }

  override visitLabel(Label label) { visit(new PaintLabel(label)) }

  override visitCombo(Combo combo) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub")
  }

  override visitButton(Button button) { visit(new PaintButton(button)) }

  override visitTable(Table table) { visit(new PaintTable(table)) }
}
