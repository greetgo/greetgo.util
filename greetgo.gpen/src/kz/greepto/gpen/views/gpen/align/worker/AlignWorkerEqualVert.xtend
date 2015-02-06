package kz.greepto.gpen.views.gpen.align.worker

import java.util.List
import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.drawport.FontDef
import kz.greepto.gpen.drawport.Kolor
import kz.greepto.gpen.drawport.Vec2
import kz.greepto.gpen.editors.gpen.model.FigureGeom

class AlignWorkerEqualVert implements AlignWorker {

  override paintIcon(DrawPort dp, int width, int height) {
    dp.style.foreground = Kolor.BLUE

    dp.font = FontDef.timesNewRoman.h(6).b

    var t = Vec2.from(0, 1)

    {
      var s = dp.str("РОВНО")
      s.draw(t + #[11, 25])
    }

    dp.from(t + #[53, 5]).shift(-5, 5).move.shift(5, -5).line.shift(5, 5).line.shift(-5, -5).move//
    .shift(0, 50).line//
    .shift(-5, -5).move.shift(5, 5).line.shift(5, -5).line

    dp.from(t + #[4, 4]).shift(40, 10).rect.draw

    dp.from(t + #[9, 18]).shift(30, 5).rect.draw
    dp.from(t + #[9, 36]).shift(30, 5).rect.draw

    dp.from(t + #[4, 45]).shift(40, 10).rect.draw
  }

  override canDoFor(List<FigureGeom> geomList) {
    println(getClass.simpleName + '.canDoFor ' + geomList)
    return true;
  }

}
