package kz.greepto.gpen.editors.gpen

import java.util.List
import kz.greepto.gpen.editors.gpen.prop.PropAccessor
import org.eclipse.jface.viewers.ISelection

class PropSelection implements ISelection {

  public val List<PropAccessor> list

  new(List<PropAccessor> list) {
    this.list = list
  }

  override isEmpty() {
    return false
  }

}
