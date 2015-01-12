package kz.greepto.gpen.editors.gpen.outline

import kz.greepto.gpen.editors.gpen.model.visitor.OutlineDisplayStrVisitor
import kz.greepto.gpen.util.ColorManager
import org.eclipse.jface.viewers.IColorProvider
import org.eclipse.jface.viewers.LabelProvider
import kz.greepto.gpen.drawport.Kolor

class GpenContentOutlineLabelProvider extends LabelProvider implements IColorProvider {
  val GpenContentOutlinePage page

  val colors = new ColorManager

  new(GpenContentOutlinePage page) {
    this.page = page
  }

  override getText(Object element) {
    if(page.sceneWorker === null) return '' + element
    if(!(element instanceof String)) return ''
    var id = element as String
    return page.sceneWorker.findByIdOrDie(id).visit(OutlineDisplayStrVisitor.INST)
  }

  override getBackground(Object element) {
    null//colors.from(Kolor.GRAY)
  }

  override getForeground(Object element) {
    if(page.sceneWorker === null) return null
    if(!(element instanceof String)) return null
    var id = element as String
    var fig = page.sceneWorker.findByIdOrDie(id)
    if(fig.freeze) colors.from(Kolor.GRAY) else null
  }

  override dispose() {
    colors.dispose
    super.dispose()
  }

}
