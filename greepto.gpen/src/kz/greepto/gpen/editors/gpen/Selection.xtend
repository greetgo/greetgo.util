package kz.greepto.gpen.editors.gpen

import java.util.List
import kz.greepto.gpen.editors.gpen.model.IdFigure
import kz.greepto.gpen.util.Repainter
import org.eclipse.jface.viewers.ISelection

class Selection implements ISelection {

  public val List<IdFigure> figureList
  public val Repainter repainter

  new(List<IdFigure> figureList, Repainter repainter) {
    this.figureList = figureList
    this.repainter = repainter
  }

  override isEmpty() {
    figureList.size == 0
  }
}
