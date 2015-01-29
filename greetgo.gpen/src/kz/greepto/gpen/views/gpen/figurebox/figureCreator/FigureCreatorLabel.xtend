package kz.greepto.gpen.views.gpen.figurebox.figureCreator

import java.util.UUID
import kz.greepto.gpen.drawport.Size
import kz.greepto.gpen.editors.gpen.model.Label
import kz.greepto.gpen.editors.gpen.model.PointFigure

class FigureCreatorLabel extends FigureCreator {

  override getGroup() { 'Главная' }

  override getName() { 'Метка' }

  override PointFigure createFigure() {
    var ret = new Label(UUID.randomUUID.toString)

    ret.text = 'Мет'
    ret.x = 10
    ret.y = 10

    return ret
  }

  override Size holstSize() { Size.from(800, 100) }
}
