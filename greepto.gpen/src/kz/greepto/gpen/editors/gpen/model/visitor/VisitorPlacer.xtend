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

class VisitorPlacer implements FigureVisitor<Rect> {
  package val DrawPort dp
  package val StyleCalc styleCalc

  new(DrawPort dp, StyleCalc styleCalc) {
    this.dp = dp
    this.styleCalc = styleCalc
  }

  override visitScene(Scene scene) {
    val ret = Rect.zero
    scene.list.forEach[ret += it => this]
    return ret
  }

  override visitLabel(Label label) {
    new PaintLabel(dp, styleCalc).placePaint(label, null)
  }

  override visitButton(Button button) {
    new PaintButton(dp, styleCalc).placePaint(button, null)
  }

  override visitCombo(Combo combo) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub")
  }
}
