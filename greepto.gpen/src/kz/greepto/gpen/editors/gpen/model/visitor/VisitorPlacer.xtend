package kz.greepto.gpen.editors.gpen.model.visitor

import org.eclipse.swt.graphics.GC
import kz.greepto.gpen.util.Rect
import kz.greepto.gpen.editors.gpen.model.Scene
import kz.greepto.gpen.editors.gpen.model.Label
import kz.greepto.gpen.editors.gpen.model.Combo
import kz.greepto.gpen.editors.gpen.model.Button
import kz.greepto.gpen.editors.gpen.style.StyleCalc
import kz.greepto.gpen.editors.gpen.model.paint.PaintLabel
import kz.greepto.gpen.editors.gpen.model.paint.PaintButton

class VisitorPlacer implements FigureVisitor<Rect> {
  package val GC gc
  package val StyleCalc styleCalc

  new(GC gc, StyleCalc styleCalc) {
    this.gc = gc
    this.styleCalc = styleCalc
  }

  override visitScene(Scene scene) {
    val ret = Rect.zero
    scene.list.forEach[ret += it => this]
    return ret
  }

  override visitLabel(Label label) {
    new PaintLabel(gc, styleCalc).placePaint(label, null)
  }

  override visitButton(Button button) {
    new PaintButton(gc, styleCalc).placePaint(button, null)
  }

  override visitCombo(Combo combo) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub")
  }
}
