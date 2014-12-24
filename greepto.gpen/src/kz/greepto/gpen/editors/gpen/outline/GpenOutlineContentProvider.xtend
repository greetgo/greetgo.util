package kz.greepto.gpen.editors.gpen.outline

import org.eclipse.jface.viewers.ITreeContentProvider
import org.eclipse.jface.viewers.Viewer

class GpenOutlineContentProvider implements ITreeContentProvider {
  override getElements(Object inputElement) { getChildren(null) }

  override getChildren(Object parent) {
    if (parent === null) {
      return #['root asd', 'root dsa']
    }
    if (parent == 'root asd') {
      return #['asd1', 'asd2']
    }
    if (parent == 'root dsa') {
      return #['dsa1', 'dsa2']
    }
  }

  override getParent(Object element) {
    if(element === null) return null
    switch (element) {
      case 'asd1': 'root asd'
      case 'asd2': 'root asd'
      case 'dsa1': 'root dsa'
      case 'dsa2': 'root dsa'
      default: null
    }
  }

  override hasChildren(Object element) {
    if(element === null) return true
    switch (element) {
      case 'root asd': true
      case 'root dsa': true
      default: false
    }
  }

  override dispose() {}

  override inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    println('GpenOutline inputChanged from ' + oldInput + " to " + newInput)
  }
}
