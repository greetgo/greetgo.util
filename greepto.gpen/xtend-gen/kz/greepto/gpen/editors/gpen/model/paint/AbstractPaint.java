package kz.greepto.gpen.editors.gpen.model.paint;

import kz.greepto.gpen.editors.gpen.style.StyleCalc;
import org.eclipse.swt.graphics.GC;

@SuppressWarnings("all")
public abstract class AbstractPaint {
  protected final GC gc;
  
  protected final StyleCalc styleCalc;
  
  public AbstractPaint(final GC gc, final StyleCalc styleCalc) {
    this.gc = gc;
    this.styleCalc = styleCalc;
  }
}
