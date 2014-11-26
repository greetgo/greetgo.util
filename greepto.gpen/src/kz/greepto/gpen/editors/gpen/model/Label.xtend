package kz.greepto.gpen.editors.gpen.model

class Label extends PointFigure {

  public String text

  new(String id) {
    super(id)
  }

  override <T> visit(FigureVisitor<T> v) {
    v.visitLabel(this);
  }

}