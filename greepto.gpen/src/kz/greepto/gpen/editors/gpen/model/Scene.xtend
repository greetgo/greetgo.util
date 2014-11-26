package kz.greepto.gpen.editors.gpen.model

import java.util.List
import java.util.ArrayList
import kz.greepto.gpen.editors.gpen.model.visitor.FigureVisitor

class Scene extends Figure {

  public List<IdFigure> list = new ArrayList;

  override <T> visit(FigureVisitor<T> v) {
    return v.visitScene(this);
  }

}
