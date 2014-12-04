package kz.greepto.gpen.editors.gpen.model

import java.util.List
import java.util.ArrayList
import kz.greepto.gpen.editors.gpen.model.visitor.FigureVisitor

class Scene extends Figure {

  public List<IdFigure> list = new ArrayList;

  override <T> operator_doubleArrow(FigureVisitor<T> v) {
    return v.visitScene(this);
  }

  new() {}

  new(Scene a) {
    list += a.list.map[copy as IdFigure]
  }

  override copy() {
    return new Scene(this)
  }

}
