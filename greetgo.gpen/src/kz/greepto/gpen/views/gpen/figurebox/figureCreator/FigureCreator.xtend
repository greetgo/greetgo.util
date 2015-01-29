package kz.greepto.gpen.views.gpen.figurebox.figureCreator

import kz.greepto.gpen.editors.gpen.model.IdFigure
import kz.greepto.gpen.drawport.Size

abstract class FigureCreator {
  abstract def IdFigure createFigure()

  abstract def Size holstSize()

  abstract def String getGroup();

  abstract def String getName();
}