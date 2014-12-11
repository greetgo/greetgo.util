package kz.greepto.gpen.editors.gpen.model.paint

import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.drawport.Rect
import kz.greepto.gpen.editors.gpen.model.Button
import kz.greepto.gpen.editors.gpen.style.PaintStatus
import kz.greepto.gpen.editors.gpen.style.StyleCalc
import org.eclipse.swt.graphics.Point

class PaintButton extends AbstractPaint {

  new(DrawPort dp, StyleCalc styleCalc) {
    super(dp, styleCalc)
  }

  def Rect placePaint(Button b, Point mouse) {
    var style = styleCalc.calcForButton(b, PaintStatus.sel(b.sel))

    var ret = Rect.from(b.x, b.y, b.width, b.height)

    if (!b.autoWidth && !b.autoHeight && mouse == null) {
      return ret
    }

    dp.font = style.font

    var size = dp.str(b.text).size
    var paddingLeft = 0
    var paddingTop = 0
    if (style.padding != null) {
      paddingLeft = style.padding.left
      paddingTop = style.padding.top
      size.width += style.padding.left + style.padding.right
      size.height += style.padding.top + style.padding.bottom
    }

    if(b.autoWidth) ret.width = size.width
    if(b.autoHeight) ret.height = size.height

    b.size = size

    if(mouse == null) return ret

    if (ret.contains(mouse)) {
      style = styleCalc.calcForButton(b, PaintStatus.selHover(b.sel))
    }

    dp.font = style.font
    dp.style.background = style.backgroundColor

    dp.from(ret).fill

    dp.style.foreground = style.borderColor
    dp.from(ret).draw

    dp.style.foreground = style.color

    var dx = (ret.width - size.width) / 2 + paddingLeft
    var dy = (ret.height - size.height) / 2 + paddingTop

    dp.str(b.text).draw(ret.x + dx, ret.y + dy)

    return ret
  }
}
