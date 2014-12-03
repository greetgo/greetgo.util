package kz.greepto.gpen.editors.gpen.model.visitor

import kz.greepto.gpen.editors.gpen.model.Scene
import java.util.List
import org.eclipse.swt.graphics.Point
import kz.greepto.gpen.editors.gpen.model.IdFigure
import java.util.ArrayList

class Hit {
  val Scene scene

  new(Scene scene) {
    this.scene = scene
  }

  def static on(Scene scene) {
    return new Hit(scene)
  }

  def HittingScene with(VisitorPlacer placer) {
    return new HittingScene(this, placer)
  }

  public static class HittingScene {
    val Hit hit
    val VisitorPlacer placer

    new(Hit hit, VisitorPlacer placer) {
      this.hit = hit
      this.placer = placer
    }

    def List<IdFigure> to(Point point) {
      val ret = new ArrayList<IdFigure>
      ret += hit.scene.list.filter[(it => placer).contains(point)]
      return ret
    }

  }
}
