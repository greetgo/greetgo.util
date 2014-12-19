package kz.greepto.gpen.editors.gpen.model.visitor

import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.drawport.Rect
import kz.greepto.gpen.editors.gpen.model.Button
import kz.greepto.gpen.editors.gpen.model.Combo
import kz.greepto.gpen.editors.gpen.model.Label
import kz.greepto.gpen.editors.gpen.model.Scene
import kz.greepto.gpen.editors.gpen.model.paint.PaintButton
import kz.greepto.gpen.editors.gpen.model.paint.PaintLabel
import kz.greepto.gpen.editors.gpen.style.StyleCalc
import kz.greepto.gpen.editors.gpen.model.Table
import kz.greepto.gpen.editors.gpen.model.paint.PaintTable
import kz.greepto.gpen.editors.gpen.model.paint.PaintFigure

class VisitorPlacer implements FigureVisitor<Rect> {
  package val DrawPort dp
  package val StyleCalc styleCalc

  new(DrawPort dp, StyleCalc styleCalc) {
    this.dp = dp
    this.styleCalc = styleCalc
  }

  override visitScene(Scene scene) {
    val ret = Rect.zero
    scene.list.forEach[ret += visit(this)]
    return ret
  }

  private def Rect visit(PaintFigure paint) {
    paint.setEnvironment(dp, styleCalc)
    return paint.place
  }

  override visitLabel(Label label) { visit(new PaintLabel(label)) }

  override visitButton(Button button) { visit(new PaintButton(button)) }

  override visitCombo(Combo combo) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub")
  }

  override visitTable(Table table) { visit(new PaintTable(table)) }
}
