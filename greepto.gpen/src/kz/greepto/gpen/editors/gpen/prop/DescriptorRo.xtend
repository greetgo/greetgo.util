package kz.greepto.gpen.editors.gpen.prop

import org.eclipse.ui.views.properties.PropertyDescriptor

class DescriptorRo extends PropertyDescriptor {

  new(Object id, String displayName) {
    super(id, displayName)
    category = 'Basic'
  }

}
