package kz.greepto.gpen.editors.gpen.style;

import kz.greepto.gpen.editors.gpen.model.Label;
import kz.greepto.gpen.editors.gpen.style.LabelStyle;
import kz.greepto.gpen.editors.gpen.style.PaintStatus;

@SuppressWarnings("all")
public interface StyleCalc {
  public abstract LabelStyle calcForLabel(final Label label, final PaintStatus ps);
}
