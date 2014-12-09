package kz.greepto.gpen.editors.gpen.action

import kz.greepto.gpen.editors.gpen.model.Scene
import kz.greepto.gpen.editors.gpen.model.IdFigure

class OperAppend extends Oper {

  public final IdFigure newFigure;

  new(IdFigure newFigure) {
    this.newFigure = newFigure.copy
  }

  override apply(Scene scene) {
    scene.list.add(newFigure)
  }

  override cancel(Scene scene) {
    scene.list.remove(scene.list.size - 1)
  }

  override getDisplayStr() {
    return "Append"
  }

}