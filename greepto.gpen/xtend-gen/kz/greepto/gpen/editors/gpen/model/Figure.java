package kz.greepto.gpen.editors.gpen.model;

import kz.greepto.gpen.editors.gpen.model.FigureVisitor;

@SuppressWarnings("all")
public abstract class Figure {
  public abstract <T extends Object> T visit(final FigureVisitor<T> v);
}
