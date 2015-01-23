package kz.greepto.gpen.editors.gpen

import kz.greepto.gpen.util.AbstractAdapterFactory
import org.eclipse.ui.views.properties.IPropertySheetPage
import org.eclipse.ui.views.contentoutline.IContentOutlinePage

class GpenEditorAdapterFactory extends AbstractAdapterFactory {

  new() {
    println('Creating GpenEditorAdapterFactory .....................')
  }

  override protected servableTypes() {
    println('.................................... GpenEditorAdapterFactory.servableTypes')
    #[IPropertySheetPage]
  }

  override protected <T> T adapter(Object adaptableObject, Class<T> adapterType) {
    if (adaptableObject instanceof GpenEditor && adapterType.isAssignableFrom(IPropertySheetPage)) {
      return createIPropertySheetPage(adaptableObject as GpenEditor) as T
    }
    if (adaptableObject instanceof GpenEditor && adapterType.isAssignableFrom(IContentOutlinePage)) {
      return createIContentOutlinePage(adaptableObject as GpenEditor) as T
    }
    return null
  }

  def IContentOutlinePage createIContentOutlinePage(GpenEditor editor) {
    return editor.contentOutlinePage
  }

  def IPropertySheetPage createIPropertySheetPage(GpenEditor editor) {
    return editor.propertySheetPage
  }
}
