package kz.greepto.gpen.editors.gpen.model

abstract class Figure {
  def <T> T visit(FigureVisitor<T> v);
}
