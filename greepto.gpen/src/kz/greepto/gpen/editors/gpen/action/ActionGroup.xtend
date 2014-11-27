package kz.greepto.gpen.editors.gpen.action

import kz.greepto.gpen.editors.gpen.model.Scene
import java.util.List
import java.util.ArrayList

class ActionGroup extends Action {

  public final List<Action> group = new ArrayList;

  override apply(Scene scene) {
    group.forEach[apply(scene)]
  }

  override cancel(Scene scene) {
    group.reverseView.forEach[cancel(scene)]
  }

}
