package kz.greepto.gpen.editors.gpen.model;

import kz.greepto.gpen.editors.gpen.model.Scene;

@SuppressWarnings("all")
public interface FigureVisitor<T extends Object> {
  public abstract T visitScene(final Scene scene);
}
