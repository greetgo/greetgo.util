package kz.greepto.gpen.views.gpen.align.worker

import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.drawport.Kolor
import kz.greepto.gpen.util.ColorManager
import kz.greepto.gpen.drawport.Vec2

class AlignWorkerToTopFromLeft implements AlignWorker {

  override paintIcon(DrawPort dp, ColorManager colors, int width, int height) {
    dp.style.foreground = Kolor.BLUE

    var t = Vec2.from(3, 5)

    dp.from(t + #[2, 0]).shift(50, 0).line

    dp.from(t + #[5, 5]).shift(15, 45).rect.draw

    var u = t + #[11, 5]

    dp.from(u + #[13, 0]).shift(5, 20).rect.draw
    dp.from(u + #[22, 0]).shift(5, 18).rect.draw
    dp.from(u + #[31, 0]).shift(5, 21).rect.draw

    dp.from(t + #[36, 50]).shift(0, -20).line//
    .shift(-5, 5).line.shift(10, 0).move.shift(-5, -5).line
  }

}
