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

}
