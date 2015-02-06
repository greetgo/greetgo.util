package kz.greepto.gpen.editors.gpen.model.visitor

import kz.greepto.gpen.editors.gpen.model.FigureGeom
import kz.greepto.gpen.editors.gpen.model.Scene
import kz.greepto.gpen.editors.gpen.model.Label
import kz.greepto.gpen.editors.gpen.model.Combo
import kz.greepto.gpen.editors.gpen.model.Button
import kz.greepto.gpen.editors.gpen.model.Table
import kz.greepto.gpen.drawport.Rect

class VisitorGeom implements FigureVisitor<FigureGeom> {
  val VisitorPlacer placer

  new(VisitorPlacer placer) {
    this.placer = placer
  }

  override visitScene(Scene scene) { null }

  override visitLabel(Label a) { toGeom(a.id, a.visit(placer), true, true) }

  static def FigureGeom toGeom(String figureId, Rect r, boolean fixedWidth, boolean fixedHeight) {
    new FigureGeom(figureId, r.x, r.y, r.width, r.height, fixedWidth, fixedHeight)
  }

  override visitCombo(Combo a) { toGeom(a.id, a.visit(placer), true, true) }

  override visitButton(Button a) { toGeom(a.id, a.visit(placer), a.autoWidth, a.autoHeight) }

  override visitTable(Table a) { toGeom(a.id, a.visit(placer), false, false) }
}
