package kz.greepto.gpen.editors.gpen.model.visitor;

import kz.greepto.gpen.editors.gpen.model.Button;
import kz.greepto.gpen.editors.gpen.model.Combo;
import kz.greepto.gpen.editors.gpen.model.Label;
import kz.greepto.gpen.editors.gpen.model.Scene;

@SuppressWarnings("all")
public interface FigureVisitor<T extends Object> {
  public abstract T visitScene(final Scene scene);
  
  public abstract T visitLabel(final Label label);
  
  public abstract T visitCombo(final Combo combo);
  
  public abstract T visitButton(final Button button);
}
