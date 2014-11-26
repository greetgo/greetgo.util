package kz.greepto.gpen.editors.gpen.model

import java.util.List
import java.util.ArrayList

class Combo extends RectFigure {

  public final List<String> lines = new ArrayList;

  public boolean opened
  public boolean autoHeight

  new(String id) {
    super(id)
  }

  override <T> visit(FigureVisitor<T> v) {
    v.visitCombo(this);
  }

}
