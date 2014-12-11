package kz.greepto.gpen.editors.gpen.style.dev

import kz.greepto.gpen.drawport.FontDef
import kz.greepto.gpen.drawport.Kolor
import kz.greepto.gpen.editors.gpen.model.Button
import kz.greepto.gpen.editors.gpen.model.Label
import kz.greepto.gpen.editors.gpen.style.ButtonStyle
import kz.greepto.gpen.editors.gpen.style.LabelStyle
import kz.greepto.gpen.editors.gpen.style.PaintStatus
import kz.greepto.gpen.editors.gpen.style.StyleCalc

class DevStyleCalc implements StyleCalc {

  override LabelStyle calcForLabel(Label label, PaintStatus ps) {
    val ret = new LabelStyle

    if (ps.hover) {
      ret.color = Kolor.from(255, 0, 0)
    } else {
      ret.color = Kolor.from(0, 0, 0)
    }

    ret.font = FontDef.arial.h(30)

    return ret
  }

  override calcForButton(Button button, PaintStatus ps) {
    val ret = new ButtonStyle

    ret.backgroundColor = Kolor.from(100, 255, 100)
    ret.borderColor = Kolor.from(0, 0, 0)
    ret.color = Kolor.from(0, 0, 0)

    ret.font = FontDef.arial.h(30)

    if (ps.hover) {
      ret.borderColor = Kolor.from(255, 0, 0)
    }

    return ret
  }

}
