package kz.greepto.gpen.editors.gpen.model

import kz.greepto.gpen.editors.gpen.model.visitor.FigureVisitor

class Label extends PointFigure {

  public String text

  new(String id) {
    super(id)
  }

  override <T> visit(FigureVisitor<T> v) {
    return v.visitLabel(this);
  }

}