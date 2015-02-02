package kz.greepto.gpen.views.gpen.align.worker

import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.drawport.Kolor
import kz.greepto.gpen.util.ColorManager
import kz.greepto.gpen.drawport.Vec2

class AlignWorkerCenterVert implements AlignWorker {

  override paintIcon(DrawPort dp, ColorManager colors, int width, int height) {
    dp.style.foreground = Kolor.BLUE

    var t = Vec2.from(4, 5)

    dp.from(t + #[3, 0]).shift(47, 13).rect.draw
    dp.from(t + #[3, 35]).shift(47, 13).rect.draw

    dp.from(t + #[9, 17]).shift(35, 5).rect.draw
    dp.from(t + #[9, 26]).shift(35, 5).rect.draw

    dp.from(t + #[27, -3]).shift(0, 55).line
  }

}
