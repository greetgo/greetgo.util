package kz.greepto.gpen.editors.gpen

import java.util.List
import kz.greepto.gpen.editors.gpen.prop.PropAccessor
import org.eclipse.jface.viewers.ISelection
import java.util.ArrayList
import java.util.Collection

class PropSelection implements ISelection {
  public val List<PropAccessor> list = new ArrayList

  val String figureId

  new(Collection<PropAccessor> list, String figureId) {
    this.list += list
    this.figureId = figureId
  }

  override isEmpty() {
    return false
  }

  override equals(Object a) {
    if (a == null) return false;
    if (!(a instanceof PropSelection)) return false;
    var b = a as PropSelection
    return figureId == b.figureId
  }

  override hashCode() {
    return figureId?.hashCode
  }
}
