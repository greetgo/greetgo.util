package kz.greepto.gpen.views.gpen.align.worker

import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.drawport.Kolor
import kz.greepto.gpen.drawport.Vec2
import java.util.List
import kz.greepto.gpen.editors.gpen.model.FigureGeom

class AlignWorkerToRightFromBottom implements AlignWorker {

  override paintIcon(DrawPort dp, int width, int height) {
    dp.style.foreground = Kolor.BLUE

    var t = Vec2.from(3, 1)

    dp.from(t + #[55, 0]).shift(0, 58).line

    dp.from(t + #[0, 38]).shift(51, 15).rect.draw

    var y = -4

    dp.from(t + #[27, y + 10]).shift(23, 5).rect.draw
    dp.from(t + #[30, y + 20]).shift(20, 5).rect.draw
    dp.from(t + #[30, y + 30]).shift(20, 5).rect.draw

    dp.from(t + #[0, 18]).shift(20, 0).line//
    .shift(-5, -5).move.shift(5, 5).line.shift(-5, 5).line
  }

  override canDoFor(List<FigureGeom> geomList) {
    println(getClass.simpleName + '.canDoFor ' + geomList)
    return true;
  }

}
