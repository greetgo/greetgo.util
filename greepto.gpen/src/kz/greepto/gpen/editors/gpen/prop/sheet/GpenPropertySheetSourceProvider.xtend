package kz.greepto.gpen.editors.gpen.prop.sheet

import kz.greepto.gpen.editors.gpen.GpenSelection
import org.eclipse.ui.views.properties.IPropertySourceProvider

class GpenPropertySheetSourceProvider implements IPropertySourceProvider {

  override getPropertySource(Object object) {
    if (object instanceof GpenSelection) {
      val sel = object as GpenSelection
      if(sel.empty) return null
      return new PropertySourceRoot(sel)
    }
    return null
  }

}
