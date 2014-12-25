package kz.greepto.gpen.editors.gpen.prop.sheet

import org.eclipse.ui.views.properties.PropertyDescriptor
import kz.greepto.gpen.editors.gpen.prop.PropAccessor

class DescriptorRo extends PropertyDescriptor {

  new(PropAccessor pa) {
    super(pa.name, pa.name)
    category = 'Basic'
  }

}
