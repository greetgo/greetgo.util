package kz.greepto.gpen.editors.gpen.prop.sheet

import kz.greepto.gpen.editors.gpen.prop.PropAccessor
import org.eclipse.ui.views.properties.TextPropertyDescriptor

class DescriptorStr extends TextPropertyDescriptor implements GpenPropertyDescriptor {

  val PropAccessor pa

  new(PropAccessor pa) {
    super(pa.name, pa.name)
    this.pa = pa
    category = 'Basic'
  }

  override getValue() { pa.value }

  override isPropertySet() { false }

  override resetPropertyValue() {}

  override setValue(Object value) { pa.value = value }
}
