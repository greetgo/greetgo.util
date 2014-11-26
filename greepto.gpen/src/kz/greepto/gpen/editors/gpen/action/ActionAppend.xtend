package kz.greepto.gpen.editors.gpen.action

import kz.greepto.gpen.editors.gpen.model.Scene
import kz.greepto.gpen.editors.gpen.model.IdFigure

class ActionAppend extends Action {

  public final IdFigure newFigure;

  new(IdFigure newFigure) {
    this.newFigure = newFigure
  }

  override apply(Scene scene) {
    scene.list.add(newFigure)
  }

}