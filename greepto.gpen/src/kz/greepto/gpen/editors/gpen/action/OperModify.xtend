package kz.greepto.gpen.editors.gpen.action

import kz.greepto.gpen.editors.gpen.model.Scene
import kz.greepto.gpen.editors.gpen.prop.ValueSetter
import kz.greepto.gpen.editors.gpen.prop.PropList
import java.util.Map

class OperModify extends Oper {

  val ValueSetter setter


  val Object newValue
  val String id
  Object oldValue

  public var PropList modiPropList = null
  val Map<String, Object> oldValueMap = newHashMap


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

    if (modiPropList !== null) {
      for (prop : modiPropList) {
        var value = prop.getter.getValue(object)
        oldValueMap.put(prop.name, value)
      }
    }

    oldValue = setter.setValue(object, newValue)
  }

  override cancel(Scene scene) {
    var object = scene.findByIdOrDie(id)
    if (modiPropList === null) {
      setter.setValue(object, oldValue)
    } else {
      for (prop : modiPropList) {
        prop.setter.setValue(object, oldValueMap.get(prop.name))
      }
    }
  }

  public String displayStr = null

  override getDisplayStr() {
    if(displayStr == null) return "Modify " + setter.name + " = " + newValue;
    return displayStr
  }

  override insteed(Oper oper) {
    if(!(oper instanceof OperModify)) return null
    var a = oper as OperModify

    if(setter != a.setter) return null
    if(id != a.id) return null

    return a
  }

}
