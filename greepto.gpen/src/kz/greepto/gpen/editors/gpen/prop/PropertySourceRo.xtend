package kz.greepto.gpen.editors.gpen.prop

import org.eclipse.ui.views.properties.IPropertySource
import org.eclipse.ui.views.properties.PropertyDescriptor

class PropertySourceRo implements IPropertySource {

  override getEditableValue() {
    println('~~~~ getEditableValue')
    return this
  }

  PropAccessor propAccessor

  new(PropAccessor propAccessor) {
    this.propAccessor = propAccessor
    propertyDescriptors = #[new DescriptorRo(propAccessor.name, propAccessor.name)]
  }

  val PropertyDescriptor[] propertyDescriptors

  override getPropertyDescriptors() { propertyDescriptors }

  override getPropertyValue(Object id) {
    println('-- getPropertyValue id = ' + id)
    return '' + propAccessor.value
  }

  override isPropertySet(Object id) { true }

  override resetPropertyValue(Object id) {}

  override setPropertyValue(Object id, Object value) {}

}
