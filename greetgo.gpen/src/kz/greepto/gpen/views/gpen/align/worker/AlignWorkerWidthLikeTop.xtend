package kz.greepto.gpen.views.gpen.align.worker

import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.drawport.Kolor
import kz.greepto.gpen.drawport.Vec2
import java.util.List
import kz.greepto.gpen.editors.gpen.model.FigureGeom

class AlignWorkerWidthLikeTop implements AlignWorker {

  override paintIcon(DrawPort dp, int width, int height) {
    dp.style.foreground = Kolor.BLUE

    var t = Vec2.from(5, 9)

    dp.from(t + #[2, 10]).shift(40, -15).rect.draw

    dp.from(t + #[2, 5]).shift(0, 40).dashLine(0.1, 0.6, 5)
    dp.from(t + #[42, 5]).shift(0, 40).dashLine(0.1, 0.6, 5)

    var u = t + #[0, 10]

    dp.from(u + #[2, 16]).shift(45, -10).rect.draw
    dp.from(u + #[7, 30]).shift(35, -10).rect.draw

    dp.from(u + #[58, 11]).shift(-15, 0).line//
    .shift(2, -2).move.shift(-2, 2).line.shift(2, 2).line

    dp.from(u + #[15, 25]).shift(-12, 0).line//
    .shift(2, -2).move.shift(-2, 2).line.shift(2, 2).line
  }

  override canDoFor(List<FigureGeom> geomList) {
    println(getClass.simpleName + '.canDoFor ' + geomList)
    return true;
  }

}
