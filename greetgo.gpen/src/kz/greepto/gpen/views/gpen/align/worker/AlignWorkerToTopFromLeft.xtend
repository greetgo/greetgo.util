package kz.greepto.gpen.views.gpen.align.worker

import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.drawport.Kolor
import kz.greepto.gpen.util.ColorManager

class AlignWorkerToTopFromLeft implements AlignWorker {

  override paintIcon(DrawPort dp, ColorManager colors, int width, int height) {
    dp.style.foreground = Kolor.BLUE

    dp.from(5, 5).shift(50, 0).line

    dp.from(10, 10).shift(20, 50).rect.draw

    dp.from(40, 10).shift(10, 25).rect.draw

    dp.from(45, 60).shift(0, -20).line//
    .shift(-5, 5).line.shift(10, 0).move.shift(-5, -5).line
  }

}
