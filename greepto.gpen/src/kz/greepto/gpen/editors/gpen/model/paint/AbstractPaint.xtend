package kz.greepto.gpen.editors.gpen.model.paint

import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.editors.gpen.style.StyleCalc

abstract class AbstractPaint {
  protected val DrawPort dp
  protected val StyleCalc styleCalc

  new(DrawPort dp, StyleCalc styleCalc) {
    this.dp = dp
    this.styleCalc = styleCalc
  }
}
