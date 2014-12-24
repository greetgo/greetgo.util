package kz.greepto.gpen.editors.gpen.prop

import org.eclipse.ui.views.properties.PropertySheetPage
import org.eclipse.ui.IWorkbenchPart
import org.eclipse.jface.viewers.ISelection
import org.eclipse.jface.viewers.Viewer

class GpenPropertySheetPage extends PropertySheetPage {

  override selectionChanged(IWorkbenchPart part, ISelection selection) {
    super.selectionChanged(part, selection)

    var f = class.superclass.declaredFields.findFirst[name == 'viewer']
    f.accessible = true
    var viewer = f.get(this) as Viewer
    viewer.input = #[selection].toArray
  }

}
