package kz.greepto.gpen.editors.gpen.prop.sheet

import kz.greepto.gpen.editors.gpen.GpenEditor
import kz.greepto.gpen.editors.gpen.prop.PropFactory
import kz.greepto.gpen.editors.gpen.prop.SceneWorker
import org.eclipse.jface.viewers.ISelection
import org.eclipse.jface.viewers.Viewer
import org.eclipse.ui.IWorkbenchPart
import org.eclipse.ui.views.properties.IPropertySourceProvider
import org.eclipse.ui.views.properties.PropertySheetPage
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.ui.views.contentoutline.ContentOutline
import kz.greepto.gpen.editors.gpen.outline.GpenContentOutlinePage

class GpenPropertySheetPage extends PropertySheetPage {
  var SceneWorker sceneWorker = null

  new() {
    propertySourceProvider = new IPropertySourceProvider() {
      override getPropertySource(Object object) {
        if(sceneWorker === null) return null

        if (object instanceof IStructuredSelection) {
          val sel = object as IStructuredSelection
          if(sel.empty) return null
          var list = sel.iterator.map[sceneWorker.findByIdOrDie(it)].toList
          val propList = PropFactory.parseObjectList(list, sceneWorker)
          return new PropertySourceRoot(propList)
        }

        return null
      }
    }
  }

  def Viewer getViewer() {
    var f = class.superclass.declaredFields.findFirst[name == 'viewer']
    f.accessible = true
    return f.get(this) as Viewer
  }

  override selectionChanged(IWorkbenchPart part, ISelection selection) {
    super.selectionChanged(part, selection)

    if (part instanceof GpenEditor) {
      sceneWorker = (part as GpenEditor).sceneWorker
    }
    if (part instanceof ContentOutline) {
      var out = part as ContentOutline
      if (out.currentPage instanceof GpenContentOutlinePage) {
        var page = out.currentPage as GpenContentOutlinePage
        sceneWorker = page.sceneWorker
      }
    }

    if (sceneWorker !== null && selection instanceof IStructuredSelection) {
      viewer.input = #[selection].toArray
    }
  }

}
