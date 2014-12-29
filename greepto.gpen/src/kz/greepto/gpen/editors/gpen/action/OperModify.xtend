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

  new(ValueSetter setter, Object newValue, String id, String displayStr) {
    this(setter, newValue, id)
    this.displayStr = displayStr
  }

  override apply(Scene scene) {
    var object = scene.findByIdOrDie(id)
    oldValue = setter.setValue(object, newValue)
  }

  override cancel(Scene scene) {
    var object = scene.findByIdOrDie(id)
    setter.setValue(object, oldValue)
  }

  public String displayStr = null

  override getDisplayStr() {
    if(displayStr == null) return "Modify " + setter.name + " = " + newValue;
    return displayStr
  }
}
