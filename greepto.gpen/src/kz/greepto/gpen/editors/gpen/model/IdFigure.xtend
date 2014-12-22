package kz.greepto.gpen.editors.gpen.model

abstract class IdFigure extends Figure {

  public final String id

  public boolean disabled = false

  new(String id) {
    this.id = id
  }

  new(IdFigure a) {
    id = a.id
  }
}