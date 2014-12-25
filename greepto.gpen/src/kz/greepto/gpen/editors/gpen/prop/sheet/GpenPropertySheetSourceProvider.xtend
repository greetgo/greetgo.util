package kz.greepto.gpen.editors.gpen.prop.sheet

import kz.greepto.gpen.editors.gpen.GpenSelection
import kz.greepto.gpen.editors.gpen.prop.PropAccessor
import org.eclipse.ui.views.properties.IPropertySourceProvider

class GpenPropertySheetSourceProvider implements IPropertySourceProvider {

  override getPropertySource(Object object) {
    if (object instanceof GpenSelection) {
      val sel = object as GpenSelection
      if(sel.empty) return null
      println('PropertySourceRoot ' + sel)
      return new PropertySourceRoot(sel)
    }
    if (object instanceof PropAccessor) {
      println('prop ' + object)
      return new PropertySourceRo(object as PropAccessor)
    }
    return null
  }

}
