package kz.greepto.gpen.editors.gpen.model

import kz.greepto.gpen.editors.gpen.model.visitor.FigureVisitor

class Button extends RectFigure {

  public String text
  public boolean autoWidth = true
  public boolean autoHeight = true

  new(String id) {
    super(id)
  }

  override <T> operator_doubleArrow(FigureVisitor<T> v) {
    return v.visitButton(this)
  }

}