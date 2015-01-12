package kz.greepto.gpen.editors.gpen.model.visitor

import java.util.ArrayList
import java.util.List
import kz.greepto.gpen.drawport.Vec2
import kz.greepto.gpen.editors.gpen.model.IdFigure
import kz.greepto.gpen.editors.gpen.model.Scene

class Hit {
  val Scene scene

  private new(Scene scene) {
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

    def List<IdFigure> to(Vec2 point) {
      val ret = new ArrayList<IdFigure>
      ret += hit.scene.list.filter[visit(placer).contains(point)]
      return ret
    }
  }
}
