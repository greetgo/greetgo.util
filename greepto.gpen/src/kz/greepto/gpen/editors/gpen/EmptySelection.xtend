package kz.greepto.gpen.editors.gpen

import org.eclipse.jface.viewers.ISelection

class EmptySelection  implements ISelection {
  override isEmpty() {
    return true
  }

  override hashCode() {0}

  override equals(Object obj) {
    if (obj == null) return false
    return obj instanceof EmptySelection
  }
}