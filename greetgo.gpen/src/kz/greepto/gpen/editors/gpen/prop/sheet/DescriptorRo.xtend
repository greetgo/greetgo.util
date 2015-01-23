package kz.greepto.gpen.editors.gpen.prop.sheet

import org.eclipse.ui.views.properties.PropertyDescriptor
import kz.greepto.gpen.editors.gpen.prop.PropAccessor

class DescriptorRo extends PropertyDescriptor implements GpenPropertyDescriptor {

  val PropAccessor pa

  new(PropAccessor pa) {
    super(pa.name, pa.name)
    this.pa = pa
    category = 'RO'
  }

  override getValue() { pa.value }

  override isPropertySet() { false }

  override resetPropertyValue() {}

  override setValue(Object value) {}
}
