package kz.greepto.gpen.editors.gpen.model.paint

import kz.greepto.gpen.editors.gpen.model.Button
import kz.greepto.gpen.editors.gpen.style.StyleCalc
import kz.greepto.gpen.util.Rect
import org.eclipse.swt.graphics.GC
import org.eclipse.swt.graphics.Point
import kz.greepto.gpen.editors.gpen.style.PaintStatus

class PaintButton extends AbstractPaint {

  new(GC gc, StyleCalc styleCalc) {
    super(gc, styleCalc)
  }

  def Rect placePaint(Button b, Point mouse) {
    var style = styleCalc.calcForButton(b, PaintStatus.sel(b.sel))

    var ret = Rect.from(b.x, b.y, b.width, b.height)

    if (!b.autoWidth && !b.autoHeight && mouse == null) {
      return ret
    }

    gc.font = style.font

    var size = gc.textExtent(b.text)

    if(b.autoWidth) ret.width = size.x
    if(b.autoHeight) ret.height = size.y

    if(mouse == null) return ret

    if (ret.contains(mouse)) {
      style = styleCalc.calcForButton(b, PaintStatus.selHover(b.sel))
    }

    gc.font = style.font
    gc.background = style.backgroundColor

    gc.fillRectangle(ret.x, ret.y, ret.width, ret.height)

    gc.foreground = style.borderColor
    gc.drawRectangle(ret.x, ret.y, ret.width, ret.height)

    gc.foreground = style.color

    var dx = (ret.width - size.x) / 2
    var dy = (ret.height - size.y) / 2

    gc.drawText(b.text, ret.x + dx, ret.y + dy, true)

    return ret
  }
}
