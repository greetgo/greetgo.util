package kz.greepto.gpen.editors.gpen.style.dev

import kz.greepto.gpen.drawport.FontDef
import kz.greepto.gpen.drawport.Kolor
import kz.greepto.gpen.editors.gpen.model.Button
import kz.greepto.gpen.editors.gpen.model.Label
import kz.greepto.gpen.editors.gpen.style.ButtonStyle
import kz.greepto.gpen.editors.gpen.style.LabelStyle
import kz.greepto.gpen.editors.gpen.style.PaintStatus
import kz.greepto.gpen.editors.gpen.style.StyleCalc
import kz.greepto.gpen.editors.gpen.style.Padding

class DevStyleCalc implements StyleCalc {
  override LabelStyle calcForLabel(Label label, PaintStatus ps) {
    val ret = new LabelStyle

    if (ps.hover) {
      ret.color = Kolor.from(250, 0, 0)
    } else {
      ret.color = Kolor.from(50, 50, 50)
    }

    ret.font = FontDef.arial.h(30)

    return ret
  }

  override calcForButton(Button button, PaintStatus ps) {
    val ret = new ButtonStyle

    ret.padding = new Padding
    ret.padding.left = 10
    ret.padding.right = 10
    ret.padding.top = 3
    ret.padding.bottom = 7

    ret.backgroundColor = Kolor.from(200, 200, 200)

    ret.borderColor = Kolor.from(100, 100, 100)

    if (ps.selected) {
      ret.color = Kolor.from(0, 0, 150)
    } else {
      ret.color = Kolor.from(50, 50, 50)
    }

    ret.font = FontDef.timesNewRoman.h(30)

    if (ps.hover) {
      ret.borderColor = Kolor.from(0, 0, 0)
    }

    return ret
  }
}
