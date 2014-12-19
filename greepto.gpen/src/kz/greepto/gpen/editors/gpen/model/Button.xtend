package kz.greepto.gpen.editors.gpen.model

import kz.greepto.gpen.editors.gpen.model.visitor.FigureVisitor

class Button extends RectFigure {

  public String text
  public boolean autoWidth = true
  public boolean autoHeight = true

  new(String id) {
    super(id)
  }

  new(Button a) {
    super(a)
    text = a.text
    autoHeight = a.autoHeight
    autoWidth = a.autoWidth
  }

  override <T> visit(FigureVisitor<T> v) {
    return v.visitButton(this)
  }

  override Button copy() {
    return new Button(this)
  }
}