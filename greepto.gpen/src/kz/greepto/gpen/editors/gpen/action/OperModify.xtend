package kz.greepto.gpen.editors.gpen.action

import kz.greepto.gpen.editors.gpen.model.Scene
import kz.greepto.gpen.editors.gpen.prop.ValueSetter

class OperModify extends Oper {

  val ValueSetter setter
  val Object newValue
  val String id
  Object oldValue

  new(ValueSetter setter, Object newValue, String id) {
    this.setter = setter
    this.newValue = newValue
    this.id = id
  }

  override apply(Scene scene) {
    var object = scene.findById(id)
    oldValue = setter.setValue(object, newValue)
  }

  override cancel(Scene scene) {
    var object = scene.findById(id)
    setter.setValue(object, oldValue)
  }

  override getDisplayStr() {
    return "modify"
  }
}