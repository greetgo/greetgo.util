package kz.greepto.gpen.views.gpen.align.worker

import java.util.List
import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.drawport.FontDef
import kz.greepto.gpen.drawport.Kolor
import kz.greepto.gpen.drawport.Vec2
import kz.greepto.gpen.editors.gpen.model.FigureGeom

class AlignWorkerEqualHoriz implements AlignWorker {

  override paintIcon(DrawPort dp, int width, int height) {
    dp.style.foreground = Kolor.BLUE

    dp.font = FontDef.timesNewRoman.h(7).b

    var t = Vec2.from(1, 0)

    {
      var s = dp.str("РОВНО")
      s.draw(t + #[14, 2])
    }

    dp.from(t + #[10, 12]).shift(-3, 3).line.shift(3, 3).line//
    .shift(-3, -3).move.shift(45, 0).line //
    .shift(-3, -3).move.shift(3, 3).line.shift(-3, 3).line

    dp.from(t + #[2, 22]).shift(10, 35).rect.draw
    dp.from(t + #[47, 22]).shift(10, 35).rect.draw

    var i = t - #[3, 3]

    dp.from(i + #[20, 30]).shift(5, 25).rect.draw
    dp.from(i + #[30, 30]).shift(5, 25).rect.draw
    dp.from(i + #[40, 30]).shift(5, 25).rect.draw
  }

  override canDoFor(List<FigureGeom> geomList) {
    println(getClass.simpleName + '.canDoFor ' + geomList)
    return true;
  }

}
