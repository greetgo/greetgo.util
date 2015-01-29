package kz.greepto.gpen.views.gpen.figurebox;

import kz.greepto.gpen.util.HandlerListInstrument;

public class FigureMediatorHandlerList extends HandlerListInstrument<FigureMediatorHandler> {
  public void fire(FigureMediator figureMediator) {
    for (FigureMediatorHandler x : this) {
      x.handle(figureMediator);
    }
  }
}
