package kz.greepto.gpen.editors.gpen.model.paint

import kz.greepto.gpen.drawport.Rect
import kz.greepto.gpen.drawport.Vec2
import kz.greepto.gpen.editors.gpen.model.Label
import kz.greepto.gpen.editors.gpen.style.PaintStatus

class PaintLabel extends AbstractPaint {
  Label label

  new(Label label) {
    this.label = label
  }

  override getFigureId() { label.id }

  override PlaceInfo work(Vec2 mouse) {
    var calc = styleCalc.calcForLabel(label, PaintStatus.sel(label.sel))

    dp.style.foreground = calc.color
    dp.font = calc.font

    var place = Rect.pointSize(label.point, dp.str(label.text).size);

    if(mouse == null) return new PlaceInfo(place, rectMouseInfo(mouse, place, false))

    var hover = place.contains(mouse)

    if (hover) {
      calc = styleCalc.calcForLabel(label, PaintStatus.selHover(label.sel))

      dp.style.foreground = calc.color
      dp.font = calc.font
    }

    dp.str(label.text).draw(label.point)

    if (label.sel && calc.focusColor !== null) {
      dp.style.foreground = calc.focusColor
      drawAroundFocus(place)
    }

    return new PlaceInfo(place, rectMouseInfo(mouse, place, false))
  }
}
