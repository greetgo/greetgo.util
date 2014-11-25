package kz.greepto.gpen.editors.gpen.model;

import kz.greepto.gpen.editors.gpen.model.Figure;
import kz.greepto.gpen.editors.gpen.model.FigureVisitor;

@SuppressWarnings("all")
public class Scene extends Figure {
  public <T extends Object> T visit(final FigureVisitor<T> v) {
    return v.visitScene(this);
  }
}
