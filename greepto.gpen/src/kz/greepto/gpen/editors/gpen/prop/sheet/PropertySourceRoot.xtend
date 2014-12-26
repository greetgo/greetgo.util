package kz.greepto.gpen.editors.gpen.prop.sheet

import kz.greepto.gpen.editors.gpen.GpenSelection
import kz.greepto.gpen.editors.gpen.prop.PropList
import org.eclipse.ui.views.properties.IPropertySource
import org.eclipse.ui.views.properties.PropertyDescriptor
import kz.greepto.gpen.editors.gpen.prop.PropAccessor

class PropertySourceRoot implements IPropertySource {

  override getEditableValue() {
    return this
  }

  val GpenSelection selection
  var PropertyDescriptor[] propertyDescriptors = #[]
  var PropList propList = PropList.empty

  new(GpenSelection selection) {
    this.selection = selection
    calcDescriptors
  }

  def void calcDescriptors() {
    if(selection.ids.size == 0) return;

    propList = selection.propList

    propertyDescriptors = propList.map[descriptorFor]
  }

  private def static PropertyDescriptor descriptorFor(PropAccessor pa) {
    return new DescriptorRo(pa)
  }

  override getPropertyDescriptors() { propertyDescriptors }

  override getPropertyValue(Object id) {
    var prop = propList.byName(id as String)
    if (prop === null) throw new IllegalArgumentException('No property for ' + id)
    return prop.value
  }

  override isPropertySet(Object id) { false }

  override resetPropertyValue(Object id) {}

  override setPropertyValue(Object id, Object value) {}
}
