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

  override PaintResult work(Vec2 mouse) {
    var ps = new PaintStatus
    ps.selected = isSel(label)
    ps.disabled = label.disabled
    var calc = styleCalc.calcForLabel(label, ps)


    dp.style.foreground = calc.color
    dp.font = calc.font

    var place = Rect.pointSize(label.point, dp.str(label.text).size);

    if(mouse == null) return simpleRect(place)

    var hover = place.contains(mouse)

    if (hover) {
      var ps2 = new PaintStatus
      ps2.selected = isSel(label)
      ps2.disabled = label.disabled
      ps2.hover = true
      calc = styleCalc.calcForLabel(label, ps2)

      dp.style.foreground = calc.color
      dp.font = calc.font
    }

    dp.str(label.text).draw(label.point)

    if (label.sel && calc.focusColor !== null) {
      dp.style.foreground = calc.focusColor
      drawAroundFocus(place)
    }

    return modiPosition(mouse, place, label)
  }
}
