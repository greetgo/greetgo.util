package kz.greepto.gpen.views.gpen.align.worker

import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.drawport.Kolor
import kz.greepto.gpen.util.ColorManager

class AlignWorkerToLeft implements AlignWorker {

   override paintIcon(DrawPort dp, ColorManager colors, int width, int height) {
    dp.style.foreground = Kolor.BLUE
    dp.from(5, 5).shift(0, 50).line
    dp.from(10, 10).shift(45, 20).rect.draw
    dp.from(10, 40).shift(25, 10).rect.draw

    dp.from(40, 45).shift(15, 0).line
    dp.from(45, 50).shift(-5, -5).line.shift(5, -5).line
  }

}
