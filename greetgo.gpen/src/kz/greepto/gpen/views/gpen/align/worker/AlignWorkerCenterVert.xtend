package kz.greepto.gpen.views.gpen.align.worker

import java.util.List
import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.drawport.Kolor
import kz.greepto.gpen.drawport.Vec2
import kz.greepto.gpen.editors.gpen.model.FigureGeom

class AlignWorkerCenterVert implements AlignWorker {

  override paintIcon(DrawPort dp, int width, int height) {
    dp.style.foreground = Kolor.BLUE

    var t = Vec2.from(4, 5)

    dp.from(t + #[3, 0]).shift(47, 13).rect.draw
    dp.from(t + #[3, 35]).shift(47, 13).rect.draw

    dp.from(t + #[9, 17]).shift(35, 5).rect.draw
    dp.from(t + #[9, 26]).shift(35, 5).rect.draw

    dp.from(t + #[27, -3]).shift(0, 55).line
  }

  override canDoFor(List<FigureGeom> geomList) {
    println(getClass.simpleName + '.canDoFor ' + geomList)
    return true;
  }

}
