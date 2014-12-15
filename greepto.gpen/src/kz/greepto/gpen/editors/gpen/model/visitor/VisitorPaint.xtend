package kz.greepto.gpen.editors.gpen.model.visitor

import kz.greepto.gpen.editors.gpen.model.Button
import kz.greepto.gpen.editors.gpen.model.Combo
import kz.greepto.gpen.editors.gpen.model.Label
import kz.greepto.gpen.editors.gpen.model.Scene
import kz.greepto.gpen.editors.gpen.model.paint.PaintButton
import kz.greepto.gpen.editors.gpen.model.paint.PaintLabel
import org.eclipse.swt.graphics.Point
import kz.greepto.gpen.editors.gpen.model.Table
import kz.greepto.gpen.editors.gpen.model.paint.PaintTable

class VisitorPaint implements FigureVisitor<Void> {

  final VisitorPlacer placer
  public Point mouse

  new(VisitorPlacer placer) {
    this.placer = placer
  }

  override visitScene(Scene scene) {
    scene.list.forEach[it => this]
    null
  }

  override visitLabel(Label label) {
    new PaintLabel(placer.dp, placer.styleCalc).placePaint(label, mouse)
    null
  }

  override visitCombo(Combo combo) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub")
  }

  override visitButton(Button button) {
    new PaintButton(placer.dp, placer.styleCalc).placePaint(button, mouse)
    null
  }

  override visitTable(Table table) {
    new PaintTable(placer.dp, placer.styleCalc).placePaint(table, mouse)
    null
  }

}
