package kz.greepto.gpen.editors.gpen.model.paint;

import kz.greepto.gpen.drawport.DrawPort;
import kz.greepto.gpen.editors.gpen.style.StyleCalc;

@SuppressWarnings("all")
public abstract class AbstractPaint {
  protected final DrawPort dp;
  
  protected final StyleCalc styleCalc;
  
  public AbstractPaint(final DrawPort dp, final StyleCalc styleCalc) {
    this.dp = dp;
    this.styleCalc = styleCalc;
  }
}
