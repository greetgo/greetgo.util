package kz.greepto.gpen.views.gpen.figurebox

import org.eclipse.ui.part.ViewPart
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.SWT
import org.eclipse.ui.ISelectionListener
import org.eclipse.jface.viewers.ISelection
import org.eclipse.ui.IWorkbenchPart
import kz.greepto.gpen.editors.gpen.GpenEditor

class FigureBoxView extends ViewPart {

  override setFocus() {}

  Composite parent = null
  GpenEditor gpenEditor = null

  override createPartControl(Composite parent) {
    this.parent = parent
    updateUI
    site.workbenchWindow.selectionService.addSelectionListener(listener)
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
    if(parent === null) return;
    parent.children.forEach[dispose]

    new Label(parent, SWT.NONE).text = 'gpenEditor = ' + gpenEditor

    parent.layout(true)
  }

}
