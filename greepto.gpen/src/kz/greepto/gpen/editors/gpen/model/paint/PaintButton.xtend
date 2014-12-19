package kz.greepto.gpen.editors.gpen.model.paint

import kz.greepto.gpen.drawport.Rect
import kz.greepto.gpen.drawport.Vec2
import kz.greepto.gpen.editors.gpen.model.Button
import kz.greepto.gpen.editors.gpen.style.PaintStatus

class PaintButton extends AbstractPaint {
  Button b

  new(Button b) {
    this.b = b
  }

  override getFigureId() { b.id }

  override PlaceInfo work(Vec2 mouse) {
    var style = styleCalc.calcForButton(b, PaintStatus.sel(b.sel))

    var place = Rect.from(b.x, b.y, b.width, b.height)

    if (!b.autoWidth && !b.autoHeight && mouse === null) {
      return new PlaceInfo(place, rectMouseInfo(mouse, place, true))
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

    if(b.autoWidth) place.width = size.width
    if(b.autoHeight) place.height = size.height

    b.size = size

    if(mouse == null) return new PlaceInfo(place, rectMouseInfo(mouse, place, true))

    if (place.contains(mouse)) {
      style = styleCalc.calcForButton(b, PaintStatus.selHover(b.sel))
    }

    dp.font = style.font
    dp.style.background = style.backgroundColor

    var r = 5

    dp.from(place).round(r).fill

    dp.style.foreground = style.borderColor.brighter

    dp.from(place.point).size(r, r).rect.arc(90, 90).draw
    dp.from(place.point + #[place.size.width, 0] - #[r, 0]).size(r, r).rect.arc(45, 45).draw

    dp.from(place.point + #[r / 2, 0]).shift(place.size.width - r, 0).line

    dp.from(place.point + #[0, r / 2]).shift(0, place.size.height - r).line

    dp.style.foreground = style.borderColor.darker

    dp.from(place.point + #[place.size.width, 0] - #[r, 0]).size(r, r).rect.arc(0, 45).draw
    dp.from(place.point + place.size - #[r, r]).size(r, r).rect.arc(3 * 90, 90).draw
    dp.from(place.point + #[0, place.height] + #[0, -r]).size(r, r).rect.arc(2 * 90 + 45, 45).draw

    dp.from(place.point + #[place.size.width, r / 2]).shift(0, place.size.height - r).line
    dp.from(place.point + place.size - #[r / 2, 0]).shift(-place.size.width + r, 0).line

    dp.style.foreground = style.borderColor.brighter

    dp.from(place.point + #[0, place.height] + #[0, -r]).size(r, r).rect.arc(2 * 90, 45).draw

    dp.style.foreground = style.color

    var dx = (place.width - size.width) / 2 + paddingLeft
    var dy = (place.height - size.height) / 2 + paddingTop

    dp.str(b.text).draw(place.x + dx, place.y + dy)

    if (b.sel && style.focusColor !== null) {
      dp.style.foreground = style.focusColor
      drawAroundFocus(place)
    }

    return new PlaceInfo(place, rectMouseInfo(mouse, place, true))
  }
}
