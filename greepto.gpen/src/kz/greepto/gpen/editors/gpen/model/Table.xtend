package kz.greepto.gpen.editors.gpen.model

import kz.greepto.gpen.editors.gpen.model.visitor.FigureVisitor
import kz.greepto.gpen.editors.gpen.prop.Polilines

class Table extends RectFigure {

  public var String colWidths
  @Polilines
  public var String content

  new(String id) {
    super(id)
  }

  new(Table a) {
    super(a)
    colWidths = a.colWidths
    content = a.content
  }

  override <T> operator_doubleArrow(FigureVisitor<T> v) {
    return v.visitTable(this);
  }

  override Table copy() { new Table(this) }

  def void line(String line) {
    if (content?.length == 0) {
      content = line
    } else {
      content += "\n" + line
    }
  }

}
