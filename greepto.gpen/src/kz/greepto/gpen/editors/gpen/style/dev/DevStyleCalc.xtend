package kz.greepto.gpen.editors.gpen.style.dev

import kz.greepto.gpen.editors.gpen.style.StyleCalc
import kz.greepto.gpen.editors.gpen.model.Label
import kz.greepto.gpen.editors.gpen.style.PaintStatus
import kz.greepto.gpen.util.FontManager
import kz.greepto.gpen.util.ColorManager
import kz.greepto.gpen.editors.gpen.style.LabelStyle
import kz.greepto.gpen.editors.gpen.model.Button
import kz.greepto.gpen.editors.gpen.style.ButtonStyle

class DevStyleCalc implements StyleCalc {

  val FontManager fm
  val ColorManager cm

  new(FontManager fm, ColorManager cm) {
    this.fm = fm
    this.cm = cm
  }

  override LabelStyle calcForLabel(Label label, PaintStatus ps) {
    val ret = new LabelStyle

    if (ps.hover) {
      ret.color = cm.rgb(255, 0, 0)
    } else {
      ret.color = cm.rgb(0, 0, 0)
    }

    ret.font = fm.arial.height(30)

    return ret
  }

  override calcForButton(Button button, PaintStatus ps) {
    val ret = new ButtonStyle

    ret.backgroundColor = cm.rgb(255, 255, 255)
    ret.borderColor = cm.rgb(0, 0, 0)
    ret.color = cm.rgb(0, 0, 0)

    ret.font = fm.arial.height(30)

    if (ps.hover) {
      ret.borderColor = cm.rgb(255, 0, 0)
    }

    return ret
  }

}