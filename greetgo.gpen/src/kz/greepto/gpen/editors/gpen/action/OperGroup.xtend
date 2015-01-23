package kz.greepto.gpen.editors.gpen.action

import kz.greepto.gpen.editors.gpen.model.Scene
import java.util.List
import java.util.ArrayList
import java.util.Collection

class OperGroup extends Oper {

  public val List<Oper> group = new ArrayList;
  val String displayStr

  new(Collection<Oper> group, String displayStr) {
    this.group += group
    this.displayStr = displayStr
  }

  override apply(Scene scene) {
    group.forEach[apply(scene)]
  }

  override cancel(Scene scene) {
    group.reverseView.forEach[cancel(scene)]
  }

  override getDisplayStr() {
    return displayStr
  }

  override insteed(Oper oper) {
    if (!(oper instanceof OperGroup)) {
      if(group.size !== 1) return null
      return group.get(0).insteed(oper)
    }
    var a = oper as OperGroup
    if(group.size != a.group.size) return null
    if(group.size == 0) return null
    val List<Oper> newList = new ArrayList
    for (var i = 0, var C = group.size; i < C; i++) {
      var newOp = group.get(i).insteed(a.group.get(i))
      if (newOp === null) return null
      newList += newOp
    }
    return new OperGroup(newList, displayStr)
  }
}
