package kz.greepto.gpen.editors.gpen

import org.eclipse.jface.viewers.ISelection

class EmptySelection  implements ISelection {

  override isEmpty() {
    return true
  }

}