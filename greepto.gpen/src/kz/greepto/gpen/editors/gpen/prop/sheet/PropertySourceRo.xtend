package kz.greepto.gpen.editors.gpen.prop.sheet

import org.eclipse.ui.views.properties.IPropertySource
import org.eclipse.ui.views.properties.PropertyDescriptor
import kz.greepto.gpen.editors.gpen.prop.PropAccessor

class PropertySourceRo implements IPropertySource {

  override getEditableValue() { this }

  PropAccessor prop

  new(PropAccessor prop) {
    this.prop = prop
    propertyDescriptors = #[new DescriptorRo(prop.name, prop.name)]
  }

  val PropertyDescriptor[] propertyDescriptors

  override getPropertyDescriptors() { propertyDescriptors }

  override getPropertyValue(Object id) {
    println('-- getPropertyValue id = ' + id)
    return '' + prop.value
  }

  override isPropertySet(Object id) { true }

  override resetPropertyValue(Object id) {}

  override setPropertyValue(Object id, Object value) {}
}
