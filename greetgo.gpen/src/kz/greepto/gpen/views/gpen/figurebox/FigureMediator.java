package kz.greepto.gpen.views.gpen.figurebox;

import kz.greepto.gpen.editors.gpen.model.PointFigure;

public interface FigureMediator {
  PointFigure createFigure();
  
  void setState(State state);
  
  State getState();
}
