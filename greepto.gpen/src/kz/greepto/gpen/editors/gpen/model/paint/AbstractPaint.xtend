package kz.greepto.gpen.editors.gpen.model.paint

import org.eclipse.swt.graphics.GC
import kz.greepto.gpen.editors.gpen.style.StyleCalc

abstract class AbstractPaint {
  protected val GC gc
  protected val StyleCalc styleCalc

  new(GC gc, StyleCalc styleCalc) {
    this.gc = gc
    this.styleCalc = styleCalc
  }
}
