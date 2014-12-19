package kz.greepto.gpen.editors.gpen.model.visitor

import kz.greepto.gpen.drawport.Vec2
import kz.greepto.gpen.editors.gpen.model.Button
import kz.greepto.gpen.editors.gpen.model.Combo
import kz.greepto.gpen.editors.gpen.model.Label
import kz.greepto.gpen.editors.gpen.model.Scene
import kz.greepto.gpen.editors.gpen.model.Table
import kz.greepto.gpen.editors.gpen.model.paint.MouseInfo
import kz.greepto.gpen.editors.gpen.model.paint.PaintButton
import kz.greepto.gpen.editors.gpen.model.paint.PaintFigure
import kz.greepto.gpen.editors.gpen.model.paint.PaintLabel
import kz.greepto.gpen.editors.gpen.model.paint.PaintTable

class VisitorPaint implements FigureVisitor<Void> {

  final VisitorPlacer placer
  public Vec2 mouse

  public MouseInfo mouseInfo
  public String mouseFigureId

  public String draggingFigureId
  public Object draggingChangeType
  public Vec2 mouseDownedAt

  new(VisitorPlacer placer) {
    this.placer = placer
  }

  override visitScene(Scene scene) {
    scene.list.forEach[visit(this)]
    null
  }

  private def Void visit(PaintFigure paint) {
    paint.setEnvironment(placer.dp, placer.styleCalc)
    var mouseInfo = paint.paint(mouse)
    if (mouseInfo !== null) {
      this.mouseInfo = mouseInfo
      mouseFigureId = paint.figureId
    }
    null
  }

  override visitLabel(Label label) { visit(new PaintLabel(label)) }

  override visitCombo(Combo combo) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub")
  }

  override visitButton(Button button) { visit(new PaintButton(button)) }

  override visitTable(Table table) { visit(new PaintTable(table)) }

}
