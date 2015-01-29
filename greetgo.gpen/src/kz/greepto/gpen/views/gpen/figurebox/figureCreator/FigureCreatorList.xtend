package kz.greepto.gpen.views.gpen.figurebox.figureCreator

import java.util.ArrayList
import kz.greepto.gpen.views.gpen.figurebox.figureCreator.FigureCreator
import kz.greepto.gpen.views.gpen.figurebox.figureCreator.FigureCreatorButton

class FigureCreatorList extends ArrayList<FigureCreator> {
  new() {
    this += new FigureCreatorButton
    this += new FigureCreatorLabel
    this += new FigureCreatorTable
  }
}