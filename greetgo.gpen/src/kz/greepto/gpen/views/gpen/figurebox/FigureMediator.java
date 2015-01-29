package kz.greepto.gpen.views.gpen.figurebox;

import kz.greepto.gpen.editors.gpen.model.IdFigure;

public interface FigureMediator {
  IdFigure createFigure();
  
  void setState(State state);
  
  State getState();
}
