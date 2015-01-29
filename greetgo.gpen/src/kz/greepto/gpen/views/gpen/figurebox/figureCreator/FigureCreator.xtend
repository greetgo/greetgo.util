package kz.greepto.gpen.views.gpen.figurebox.figureCreator

import kz.greepto.gpen.drawport.Size
import kz.greepto.gpen.editors.gpen.model.PointFigure

abstract class FigureCreator {
  abstract def PointFigure createFigure()

  abstract def Size holstSize()

  abstract def String getGroup();

  abstract def String getName();
}