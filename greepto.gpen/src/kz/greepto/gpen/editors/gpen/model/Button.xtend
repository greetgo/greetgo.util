package kz.greepto.gpen.editors.gpen.model

import kz.greepto.gpen.editors.gpen.model.visitor.FigureVisitor

class Button extends RectFigure {

  public String caption
  public boolean autoWidth
  public boolean autoHeight

  new(String id) {
    super(id)
  }

  override <T> visit(FigureVisitor<T> v) {
    return v.visitButton(this)
  }

}