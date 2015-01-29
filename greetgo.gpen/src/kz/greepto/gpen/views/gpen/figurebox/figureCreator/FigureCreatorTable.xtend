package kz.greepto.gpen.views.gpen.figurebox.figureCreator

import java.util.UUID
import kz.greepto.gpen.drawport.Size
import kz.greepto.gpen.editors.gpen.model.PointFigure
import kz.greepto.gpen.editors.gpen.model.Table

class FigureCreatorTable extends FigureCreator {

  override getGroup() { 'Главная' }

  override getName() { 'Таблица' }

  override PointFigure createFigure() {
    var ret = new Table(UUID.randomUUID.toString)

    ret.content = "#col1|col2\nr11|r12\nr21|r22"
    ret.colWidths = "100|100"
    ret.x = 10
    ret.y = 10
    ret.width = 200
    ret.height = 100

    return ret
  }

  override Size holstSize() { Size.from(300, 300) }
}
