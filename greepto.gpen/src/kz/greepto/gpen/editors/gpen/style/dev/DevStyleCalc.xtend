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
import kz.greepto.gpen.editors.gpen.model.Table
import kz.greepto.gpen.editors.gpen.style.TableStyle

class DevStyleCalc implements StyleCalc {
  override LabelStyle calcForLabel(Label label, PaintStatus ps) {
    val ret = new LabelStyle
    ret.focusColor = Kolor.GRAY
    ret.font = FontDef.arial.h(30)

    if (ps.disabled) {
      ret.color = Kolor.GRAY
      return ret
    }

    if (ps.hover) {
      ret.color = Kolor.rgb(0, 0, 0)
    } else {
      ret.color = Kolor.rgb(50, 50, 50)
    }

    if (ps.selected) {
      if (ps.hover) {
        ret.color = Kolor.rgb(0, 0, 200)
      } else {
        ret.color = Kolor.rgb(0, 0, 255)
      }
    }

    return ret
  }

  override calcForButton(Button button, PaintStatus ps) {
    val ret = new ButtonStyle
    ret.focusColor = Kolor.GRAY

    ret.padding = new Padding
    ret.padding.left = 10
    ret.padding.right = 10
    ret.padding.top = 3
    ret.padding.bottom = 7

    ret.backgroundColor = Kolor.rgb(200, 200, 230)

    ret.font = FontDef.timesNewRoman.h(30)

    if (ps.selected) {
      ret.color = Kolor.rgb(0, 0, 255)
      ret.borderColor = Kolor.rgb(100, 100, 100).darker

    //ret.font.b
    } else {
      ret.color = Kolor.rgb(50, 50, 50)
      ret.borderColor = Kolor.rgb(100, 100, 100)
    }

    if (ps.disabled) {
      ret.backgroundColor = Kolor.rgb(255, 255, 255)
      ret.color = Kolor.GRAY
      ret.borderColor = Kolor.GRAY
    }

    if (ps.hover && !ps.disabled) {
      ret.borderColor = Kolor.rgb(0, 0, 0)
    }

    return ret
  }

  override calcForTable(Table table, PaintStatus ps) {
    val ret = new TableStyle
    ret.focusColor = Kolor.GRAY
    ret.bgColor = Kolor.WHITE

    ret.headerFont = FontDef.arial.h(14).b
    ret.contentFont = FontDef.arial.h(14)
    ret.selFont = FontDef.arial.h(14)

    if (ps.selected && ps.hover) {
      ret.borderColor = Kolor.MAGENTA
    } else if (!ps.selected && ps.hover) {
      ret.borderColor = Kolor.MAGENTA
    } else if (ps.selected && !ps.hover) {
      ret.borderColor = Kolor.BLACK
    } else {
      ret.borderColor = Kolor.BLACK
    }

    ret.selBgColor = Kolor.BLUE
    ret.selFgColor = Kolor.WHITE

    ret.contentBgColor = Kolor.WHITE
    ret.contentFgColor = Kolor.BLACK

    ret.headerBgColor = Kolor.GREEN
    ret.headerFgColor = Kolor.BLACK

    ret.cellPadding = new Padding
    ret.cellPadding.top = 3
    ret.cellPadding.bottom = 3

    if (ps.disabled) {
      ret.selBgColor = Kolor.rgb(220, 220, 220)
      ret.selFgColor = Kolor.GRAY

      ret.contentBgColor = Kolor.WHITE
      ret.contentFgColor = Kolor.GRAY

      ret.headerBgColor = Kolor.WHITE
      ret.headerFgColor = Kolor.GRAY

      ret.borderColor = Kolor.GRAY
    }

    return ret
  }
}
