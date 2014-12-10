package kz.greepto.gpen.editors.gpen.model

import kz.greepto.gpen.editors.gpen.prop.Skip

abstract class IdFigure extends Figure {

  public final String id
  @Skip
  public boolean sel = false

  new(String id) {
    this.id = id
  }

  new(IdFigure a) {
    id = a.id
    sel = a.sel
  }
}