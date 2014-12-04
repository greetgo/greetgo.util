package kz.greepto.gpen.editors.gpen.action

import kz.greepto.gpen.editors.gpen.model.Scene
import java.util.List
import java.util.ArrayList
import java.util.Collection

class ActionGroup extends Action {

  public final List<Action> group = new ArrayList;

  new(Collection<Action> group) {
    this.group += group
  }

  override apply(Scene scene) {
    group.forEach[apply(scene)]
  }

  override cancel(Scene scene) {
    group.reverseView.forEach[cancel(scene)]
  }

}
