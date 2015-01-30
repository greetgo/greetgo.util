package kz.greepto.gpen.views.gpen.align.worker

import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.drawport.Kolor
import kz.greepto.gpen.util.ColorManager

class AlignWorkerToLeftFromBottom implements AlignWorker {

  override paintIcon(DrawPort dp, ColorManager colors, int width, int height) {
    dp.style.foreground = Kolor.BLUE
    dp.from(0, 1).shift(0, 58).line

    dp.from(5, 40).shift(50, 15).rect.draw

    var y = -3

    dp.from(5, y + 10).shift(20, 5).rect.draw
    dp.from(5, y + 20).shift(25, 5).rect.draw
    dp.from(5, y + 30).shift(20, 5).rect.draw

    dp.from(55, 19).shift(-20, 0).line//
    .shift(5, -5).line.shift(0, 10).move.shift(-5, -5).line
  }

}
