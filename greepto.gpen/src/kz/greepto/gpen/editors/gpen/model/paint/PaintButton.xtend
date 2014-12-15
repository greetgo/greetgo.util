package kz.greepto.gpen.editors.gpen.model.paint

import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.drawport.Rect
import kz.greepto.gpen.editors.gpen.model.Button
import kz.greepto.gpen.editors.gpen.style.PaintStatus
import kz.greepto.gpen.editors.gpen.style.StyleCalc
import org.eclipse.swt.graphics.Point

class PaintButton extends AbstractPaint {

  new(DrawPort dp, StyleCalc styleCalc) {
    super(dp.copy, styleCalc)
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

    var r = 5

    dp.from(ret).round(r).fill

    dp.style.foreground = style.borderColor.brighter

    dp.from(ret.point).size(r, r).rect.arc(90, 90).draw
    dp.from(ret.point + #[ret.size.width, 0] - #[r, 0]).size(r, r).rect.arc(45, 45).draw

    dp.from(ret.point + #[r / 2, 0]).shift(ret.size.width - r, 0).line

    dp.from(ret.point + #[0, r / 2]).shift(0, ret.size.height - r).line

    dp.style.foreground = style.borderColor.darker

    dp.from(ret.point + #[ret.size.width, 0] - #[r, 0]).size(r, r).rect.arc(0, 45).draw
    dp.from(ret.point + ret.size - #[r, r]).size(r, r).rect.arc(3 * 90, 90).draw
    dp.from(ret.point + #[0, ret.height] + #[0, -r]).size(r, r).rect.arc(2 * 90 + 45, 45).draw

    dp.from(ret.point + #[ret.size.width, r / 2]).shift(0, ret.size.height - r).line
    dp.from(ret.point + ret.size - #[r / 2, 0]).shift(-ret.size.width + r, 0).line

    dp.style.foreground = style.borderColor.brighter

    dp.from(ret.point + #[0, ret.height] + #[0, -r]).size(r, r).rect.arc(2 * 90, 45).draw

    dp.style.foreground = style.color

    var dx = (ret.width - size.width) / 2 + paddingLeft
    var dy = (ret.height - size.height) / 2 + paddingTop

    dp.str(b.text).draw(ret.x + dx, ret.y + dy)

    if (b.sel && style.focusColor !== null) {
      dp.style.foreground = style.focusColor
      drawAroundFocus(ret)
    }

    return ret
  }
}
