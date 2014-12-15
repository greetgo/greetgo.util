package kz.greepto.gpen.editors.gpen.model.paint

import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.drawport.Rect
import kz.greepto.gpen.drawport.Vec2
import kz.greepto.gpen.editors.gpen.style.StyleCalc

abstract class AbstractPaint {
  protected val DrawPort dp
  protected val StyleCalc styleCalc

  new(DrawPort dp, StyleCalc styleCalc) {
    this.dp = dp
    this.styleCalc = styleCalc
  }

  protected def void drawAroundFocus(Rect rect) {
    val int step = 10
    val int period = 300
    var offset = -((System.currentTimeMillis % period) as double / period ) * step
    var skvaj = 0.5

    offset = dp.from(rect.point + rect.size + #[2, 2])//
    .to(Vec2.from(-step, 0))//
    .dashLine(offset, skvaj, rect.width + 4)

    offset = dp.from(rect.point + #[-2, rect.height + 2])//
    .to(Vec2.from(0, -step))//
    .dashLine(offset, skvaj, rect.height + 4)

    offset = dp.from(rect.point - #[2, 2])//
    .to(Vec2.from(step, 0))//
    .dashLine(offset, skvaj, rect.width + 4)

    offset = dp.from(rect.point + #[rect.width + 3, -2])//
    .to(Vec2.from(0, step))//
    .dashLine(offset, skvaj, rect.height + 4)
  }
}
