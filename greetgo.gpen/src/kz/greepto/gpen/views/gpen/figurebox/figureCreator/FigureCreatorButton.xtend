package kz.greepto.gpen.views.gpen.figurebox.figureCreator

import java.util.UUID
import kz.greepto.gpen.drawport.Size
import kz.greepto.gpen.editors.gpen.model.Button
import kz.greepto.gpen.editors.gpen.model.IdFigure

class FigureCreatorButton extends FigureCreator {

  override getGroup() { 'Главная' }

  override getName() { 'Кнопка' }

  override IdFigure createFigure() {
    var but = new Button(UUID.randomUUID.toString)

    but.text = 'OK'
    but.x = 10
    but.y = 10
    but.width = 10
    but.width = 10
    but.autoWidth = true
    but.autoHeight = true

    return but
  }

  override Size holstSize() { Size.from(100, 100) }
}
