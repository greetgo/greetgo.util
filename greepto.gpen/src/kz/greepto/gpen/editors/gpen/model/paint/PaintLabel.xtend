package kz.greepto.gpen.editors.gpen.model.paint

import org.eclipse.swt.graphics.Point
import kz.greepto.gpen.editors.gpen.model.Label
import kz.greepto.gpen.editors.gpen.style.StyleCalc
import kz.greepto.gpen.editors.gpen.style.PaintStatus
import kz.greepto.gpen.drawport.Rect
import kz.greepto.gpen.drawport.DrawPort

class PaintLabel extends AbstractPaint {

  new(DrawPort dp, StyleCalc styleCalc) {
    super(dp, styleCalc)
  }

  def Rect placePaint(Label label, Point mouse) {
    var calc = styleCalc.calcForLabel(label, PaintStatus.sel(label.sel))

    dp.style.foreground = calc.color
    dp.font = calc.font

    var bounds = Rect.pointSize(label.point, dp.str(label.text).size);

    if(mouse == null) return bounds

    var hover = bounds.contains(mouse)

    if (hover) {
      calc = styleCalc.calcForLabel(label, PaintStatus.selHover(label.sel))

      dp.style.foreground = calc.color
      dp.font = calc.font
    }

    dp.str(label.text).draw(label.point)

    if (label.sel && calc.focusColor !== null){
      dp.style.foreground = calc.focusColor
      drawAroundFocus(bounds)
    }

    return bounds
  }
}
