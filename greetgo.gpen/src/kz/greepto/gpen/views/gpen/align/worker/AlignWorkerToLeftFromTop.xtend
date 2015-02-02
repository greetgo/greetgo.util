package kz.greepto.gpen.views.gpen.align.worker

import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.drawport.Kolor
import kz.greepto.gpen.util.ColorManager
import kz.greepto.gpen.drawport.Vec2

class AlignWorkerToLeftFromTop implements AlignWorker {

  override paintIcon(DrawPort dp, ColorManager colors, int width, int height) {
    dp.style.foreground = Kolor.BLUE

    var t = Vec2.from(2, 1)

    dp.from(t).shift(0, 58).line

    dp.from(t + #[5, 5]).shift(50, 15).rect.draw

    var y = 17

    dp.from(t + #[5, y + 10]).shift(20, 5).rect.draw
    dp.from(t + #[5, y + 20]).shift(23, 5).rect.draw
    dp.from(t + #[5, y + 30]).shift(18, 5).rect.draw

    dp.from(t + #[55, 39]).shift(-20, 0).line//
    .shift(5, -5).line.shift(0, 10).move.shift(-5, -5).line
  }

}
