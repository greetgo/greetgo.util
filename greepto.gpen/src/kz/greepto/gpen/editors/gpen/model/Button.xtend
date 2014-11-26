package kz.greepto.gpen.editors.gpen.model

class Button extends RectFigure {

  public String caption
  public boolean autoWidth
  public boolean autoHeight

  new(String id) {
    super(id)
  }

  override <T> visit(FigureVisitor<T> v) {
    v.visitButton(this);
  }

}