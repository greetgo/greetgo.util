package kz.greepto.gpen.editors.gpen.prop

import kz.greepto.gpen.editors.gpen.PropSelection
import org.eclipse.ui.views.properties.IPropertySource
import org.eclipse.ui.views.properties.PropertyDescriptor

class PropertySourceRoot implements IPropertySource {

  override getEditableValue() {
    return this
  }

  PropSelection selection

  new(PropSelection selection) {
    this.selection = selection
    var list = newArrayList()
    for (pa : selection.list) {
      list += new DescriptorRo(pa.name, pa.name)
    }
    propertyDescriptors = list
  }

  val PropertyDescriptor[] propertyDescriptors

  override getPropertyDescriptors() { propertyDescriptors }

  override getPropertyValue(Object id) {
    var pa = selection.list.findFirst[name == id]
    if(pa === null) return null
    return pa.value
  }

  override isPropertySet(Object id) { false }

  override resetPropertyValue(Object id) {}

  override setPropertyValue(Object id, Object value) {}

}
