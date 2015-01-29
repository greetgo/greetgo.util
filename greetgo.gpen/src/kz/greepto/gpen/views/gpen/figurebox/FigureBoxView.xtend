package kz.greepto.gpen.views.gpen.figurebox

import kz.greepto.gpen.editors.gpen.GpenEditor
import org.eclipse.jface.viewers.ISelection
import org.eclipse.swt.widgets.Composite
import org.eclipse.ui.ISelectionListener
import org.eclipse.ui.IWorkbenchPart
import org.eclipse.ui.part.ViewPart

class FigureBoxView extends ViewPart {

  override setFocus() {}

  GpenEditor gpenEditor = null
  FigureBoxUI ui = null

  override createPartControl(Composite parent) {
    site.workbenchWindow.selectionService.addSelectionListener(listener)

    ui = new FigureBoxUI(parent)
  }

  val ISelectionListener listener = [ IWorkbenchPart part, ISelection selection |
    if (part instanceof GpenEditor) {
      gpenEditor = (part as GpenEditor)
    } else {
      gpenEditor = null
    }
    updateUI
  ]

  def updateUI() {
  }

  override dispose() {
    super.dispose()
    if(ui !== null) ui.dispose
  }
}
