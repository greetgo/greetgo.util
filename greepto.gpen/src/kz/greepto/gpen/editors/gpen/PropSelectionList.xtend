package kz.greepto.gpen.editors.gpen

import java.util.ArrayList
import java.util.Collection
import java.util.List
import kz.greepto.gpen.editors.gpen.prop.PropAccessor
import org.eclipse.jface.viewers.IStructuredSelection

class PropSelectionList implements IStructuredSelection {
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
    if(a == null) return false;
    if(!(a instanceof PropSelection)) return false;
    var b = a as PropSelectionList
    return figureId == b.figureId
  }

  override hashCode() {
    return figureId?.hashCode
  }

  val oneList = #[this]

  override getFirstElement() { oneList.get(0) }

  override iterator() { oneList.iterator }

  override size() { 1 }

  override toArray() { oneList.toArray }

  override toList() { oneList }
}
