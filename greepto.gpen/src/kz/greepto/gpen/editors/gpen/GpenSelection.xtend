package kz.greepto.gpen.editors.gpen

import java.util.List
import kz.greepto.gpen.editors.gpen.model.Scene
import kz.greepto.gpen.editors.gpen.prop.PropFactory
import kz.greepto.gpen.editors.gpen.prop.PropList
import kz.greepto.gpen.editors.gpen.prop.SceneWorker
import org.eclipse.jface.viewers.ISelection

class GpenSelection implements ISelection {

  public val SceneWorker sceneWorker
  public val Scene scene
  public val List<String> ids = newArrayList

  new(Scene scene, SceneWorker sceneWorker) {
    this.scene = scene
    this.sceneWorker = sceneWorker
  }

  override isEmpty() { ids.size === 0 }

  def PropList getPropList() {
    PropFactory.parseObjectList(ids.map[scene.findByIdOrDie(it)], sceneWorker)
  }
}
