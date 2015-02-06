package kz.greepto.gpen.views.gpen.align.worker

import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.drawport.Kolor
import kz.greepto.gpen.drawport.Vec2
import java.util.List
import kz.greepto.gpen.editors.gpen.model.FigureGeom

class AlignWorkerToBottomFromLeft implements AlignWorker {

  override paintIcon(DrawPort dp, int width, int height) {
    dp.style.foreground = Kolor.BLUE

    var t = Vec2.from(1, 5)

    dp.from(t + #[2, 50]).shift(50, 0).line

    dp.from(t + #[5, 0]).shift(15, 45).rect.draw

    var u = t + #[11, 45]

    dp.from(u + #[13, 0]).shift(5, -20).rect.draw
    dp.from(u + #[23, 0]).shift(5, -18).rect.draw
    dp.from(u + #[33, 0]).shift(5, -22).rect.draw

    dp.from(t + #[37, 0]).shift(0, 20).line//
    .shift(-5, -5).move.shift(5, 5).line.shift(5, -5).line
  }

  override canDoFor(List<FigureGeom> geomList) {
    println(getClass.simpleName + '.canDoFor ' + geomList)
    return true;
  }

}
