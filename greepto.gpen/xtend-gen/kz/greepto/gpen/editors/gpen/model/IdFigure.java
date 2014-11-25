package kz.greepto.gpen.editors.gpen.model;

import kz.greepto.gpen.editors.gpen.model.Figure;

@SuppressWarnings("all")
public abstract class IdFigure extends Figure {
  public final String id;
  
  public IdFigure(final String id) {
    this.id = id;
  }
}
