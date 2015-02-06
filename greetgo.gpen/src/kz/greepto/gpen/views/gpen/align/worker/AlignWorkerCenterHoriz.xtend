package kz.greepto.gpen.views.gpen.align.worker

import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.drawport.Kolor
import kz.greepto.gpen.drawport.Vec2
import java.util.List
import kz.greepto.gpen.editors.gpen.model.FigureGeom

class AlignWorkerCenterHoriz implements AlignWorker {

  override paintIcon(DrawPort dp, int width, int height) {
    dp.style.foreground = Kolor.BLUE

    var t = Vec2.from(3, 3)

    dp.from(t + #[0, 3]).shift(13, 50).rect.draw
    dp.from(t + #[39, 3]).shift(13, 50).rect.draw

    dp.from(t + #[18, 12]).shift(5, 30).rect.draw
    dp.from(t + #[29, 12]).shift(5, 30).rect.draw

    dp.from(t + #[-3, 28]).shift(58, 0).line
  }

  override canDoFor(List<FigureGeom> geomList) {
    println(getClass.simpleName + '.canDoFor ' + geomList)
    return true;
  }

}
