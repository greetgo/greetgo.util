package kz.greepto.gpen.views.gpen.align.worker

import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.drawport.Kolor
import kz.greepto.gpen.util.ColorManager
import kz.greepto.gpen.drawport.FontDef

class AlignWorkerEqualHoriz implements AlignWorker {

  override paintIcon(DrawPort dp, ColorManager colors, int width, int height) {
    dp.style.foreground = Kolor.BLUE

    dp.font = FontDef.timesNewRoman.h(7).b

    {
      var s = dp.str("РОВНО")
      var w = s.size.width
      s.draw((width - w) / 2 + 1, 5)
    }

    dp.from(13, 15).shift(-3, 3).line.shift(3, 3).line//
    .shift(-3, -3).move.shift(45, 0).line //
    .shift(-3, -3).move.shift(3, 3).line.shift(-3, 3).line

    dp.from(5, 25).shift(10, 35).rect.draw
    dp.from(50, 25).shift(10, 35).rect.draw

    dp.from(20, 30).shift(5, 25).rect.draw
    dp.from(30, 30).shift(5, 25).rect.draw
    dp.from(40, 30).shift(5, 25).rect.draw
  }

}
