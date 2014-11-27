package kz.greepto.gpen.editors.gpen.action;

import kz.greepto.gpen.editors.gpen.action.Action;
import kz.greepto.gpen.editors.gpen.model.IdFigure;
import kz.greepto.gpen.editors.gpen.model.Scene;

@SuppressWarnings("all")
public class ActionAppend extends Action {
  public final IdFigure newFigure;
  
  public ActionAppend(final IdFigure newFigure) {
    this.newFigure = newFigure;
  }
  
  public void apply(final Scene scene) {
    scene.list.add(this.newFigure);
  }
  
  public void cancel(final Scene scene) {
    int _size = scene.list.size();
    int _minus = (_size - 1);
    scene.list.remove(_minus);
  }
}
