package kz.greepto.gpen.editors.gpen.model

import kz.greepto.gpen.editors.gpen.model.visitor.FigureVisitor

abstract class Figure {
  def <T> T visit(FigureVisitor<T> v)
  def <T extends Figure> T copy();
}
