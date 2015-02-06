package kz.greepto.gpen.views.gpen.align

import kz.greepto.gpen.editors.gpen.GpenEditor
import kz.greepto.gpen.util.ColorManager
import kz.greepto.gpen.util.FontManager
import kz.greepto.gpen.views.gpen.align.worker.FigureAlignType
import org.eclipse.jface.viewers.ISelection
import org.eclipse.swt.widgets.Composite
import org.eclipse.ui.ISelectionListener
import org.eclipse.ui.IWorkbenchPart
import org.eclipse.ui.part.ViewPart

class FigureAlignView extends ViewPart {

  val colors = new ColorManager
  val fonts = new FontManager

  GpenEditor gpenEditor = null

  val ISelectionListener listener = [ IWorkbenchPart part, ISelection selection |
    if (part instanceof GpenEditor) {
      gpenEditor = (part as GpenEditor)
    } else {
      gpenEditor = null
    }
    if (gpenEditor === null) {
      for (fat : FigureAlignType.values) {
        ui.setEnable(fat, false)
      }
      return
    }

    var selectedGeomList = gpenEditor.selectedGeomList
    {
      for (fat : FigureAlignType.values) {
        fat.worker.canDoFor(selectedGeomList)
        ui.setEnable(fat, true)
      }
    }
  ]

  val ui = new FigureAlignUI

  override createPartControl(Composite parent) {
    site.workbenchWindow.selectionService.addSelectionListener(listener)

    ui.update(parent)
  }

  override setFocus() {
  }

  override dispose() {
    site.workbenchWindow.selectionService.removeSelectionListener(listener)
    colors.dispose
    fonts.dispose
    super.dispose
  }
}
