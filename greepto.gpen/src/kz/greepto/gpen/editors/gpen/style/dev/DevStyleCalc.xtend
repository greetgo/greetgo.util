package kz.greepto.gpen.editors.gpen.style.dev

import kz.greepto.gpen.editors.gpen.style.StyleCalc
import kz.greepto.gpen.editors.gpen.model.Label
import kz.greepto.gpen.editors.gpen.style.PaintStatus
import kz.greepto.gpen.util.FontManager
import kz.greepto.gpen.util.ColorManager
import kz.greepto.gpen.editors.gpen.style.LabelStyle

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

}