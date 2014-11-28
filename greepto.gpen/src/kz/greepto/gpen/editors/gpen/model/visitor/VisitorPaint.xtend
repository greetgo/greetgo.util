package kz.greepto.gpen.editors.gpen.model.visitor

import kz.greepto.gpen.editors.gpen.model.Button
import kz.greepto.gpen.editors.gpen.model.Combo
import kz.greepto.gpen.editors.gpen.model.Label
import kz.greepto.gpen.editors.gpen.model.Scene
import org.eclipse.swt.graphics.Point
import kz.greepto.gpen.editors.gpen.style.PaintStatus

class VisitorPaint implements FigureVisitor<Void> {

  final VisitorSizer sizer
  public Point mouse

  new(VisitorSizer sizer) {
    this.sizer = sizer
  }

  override visitScene(Scene scene) {
    scene.list.forEach[it ?: this]
    null
  }

  override visitLabel(Label label) {
    var size = label ?: sizer
    var ps = if (size.contains(mouse)) PaintStatus.hover else PaintStatus.normal
    var calc = sizer.styleCalc.calcForLabel(label, ps)

    sizer.gc.foreground = calc.color
    sizer.gc.font = calc.font

    sizer.gc.drawText(label.text, label.x, label.y, true)

    null
  }

  override visitCombo(Combo combo) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub")
  }

  override visitButton(Button button) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub")
  }

}
