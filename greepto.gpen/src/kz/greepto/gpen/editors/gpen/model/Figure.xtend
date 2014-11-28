package kz.greepto.gpen.editors.gpen.model

import kz.greepto.gpen.editors.gpen.model.visitor.FigureVisitor

abstract class Figure {
  def <T> T operator_elvis(FigureVisitor<T> v)
}
