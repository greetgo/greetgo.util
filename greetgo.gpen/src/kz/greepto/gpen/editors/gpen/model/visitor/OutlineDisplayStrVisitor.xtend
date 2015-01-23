package kz.greepto.gpen.editors.gpen.model.visitor

import kz.greepto.gpen.editors.gpen.model.Scene
import kz.greepto.gpen.editors.gpen.model.Label
import kz.greepto.gpen.editors.gpen.model.Combo
import kz.greepto.gpen.editors.gpen.model.Button
import kz.greepto.gpen.editors.gpen.model.Table
import kz.greepto.gpen.editors.gpen.model.IdFigure
import kz.greepto.gpen.editors.gpen.model.RectFigure
import kz.greepto.gpen.editors.gpen.model.PointFigure

class OutlineDisplayStrVisitor  implements FigureVisitor<String> {

  private new() {}

  public static val OutlineDisplayStrVisitor INST = new OutlineDisplayStrVisitor

  override visitScene(Scene scene) {
    '' + scene
  }

  def String pos(IdFigure fig) {
    if (fig instanceof RectFigure) {
      var a = fig as RectFigure
      return ' @(' + a.x + ', ' + a.y + ') ' + a.width + 'x' + a.height
    }

    if (fig instanceof PointFigure) {
      var a = fig as PointFigure
      return ' @(' + a.x + ', ' + a.y + ')'
    }

    return ''
  }

  override visitLabel(Label label) {
    'Label ' + label.text + ' ' + pos(label)
  }

  override visitCombo(Combo combo) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub")
  }

  override visitButton(Button button) {
    'Button ' + button.text + ' ' + pos(button)
  }

  val static MAX_CONTENT_LEN = 10

  override visitTable(Table table) {
    var s = (table.content ?: '')
    if (s.length > MAX_CONTENT_LEN) {
      s = s.substring(0, MAX_CONTENT_LEN) + '...'
    }
    'Table ' + s + ' ' + pos(table)
  }

}