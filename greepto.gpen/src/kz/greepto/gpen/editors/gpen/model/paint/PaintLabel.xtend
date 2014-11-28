package kz.greepto.gpen.editors.gpen.model.paint

import org.eclipse.swt.graphics.Point
import kz.greepto.gpen.editors.gpen.model.Label
import kz.greepto.gpen.editors.gpen.style.StyleCalc
import org.eclipse.swt.graphics.GC
import kz.greepto.gpen.editors.gpen.style.PaintStatus
import kz.greepto.gpen.util.Rect

class PaintLabel extends AbstractPaint {

  new(GC gc, StyleCalc styleCalc) {
    super(gc, styleCalc)
  }

  def Rect placePaint(Label label, Point mouse) {
    var calc = styleCalc.calcForLabel(label, PaintStatus.sel(label.sel))

    gc.foreground = calc.color
    gc.font = calc.font

    var size = Rect.pointSize(label.point, gc.textExtent(label.text));

    if(mouse == null) return size

    var hover = size.contains(mouse)

    if (hover) {
      calc = styleCalc.calcForLabel(label, PaintStatus.selHover(label.sel))

      gc.foreground = calc.color
      gc.font = calc.font
    }

    gc.drawText(label.text, label.x, label.y, true)

    return size
  }
}
