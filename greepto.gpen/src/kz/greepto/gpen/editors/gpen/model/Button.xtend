package kz.greepto.gpen.editors.gpen.model

import kz.greepto.gpen.editors.gpen.model.visitor.FigureVisitor
import kz.greepto.gpen.editors.gpen.prop.SetOrderWeight

class Button extends RectFigure {

  public String text
  @SetOrderWeight(200)
  public boolean autoWidth = true
  @SetOrderWeight(200)
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

  override void setWidth(int width) {
    if(this.width === width) return;
    super.width = width
    autoWidth = false
  }

  override void setHeight(int height) {
    if (this.height === height) return;
    super.height = height
    autoHeight = false
  }
}
