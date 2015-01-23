package kz.greepto.gpen.editors.gpen.prop.sheet

import kz.greepto.gpen.editors.gpen.prop.PropAccessor
import org.eclipse.ui.views.properties.TextPropertyDescriptor

class DescriptorInt extends TextPropertyDescriptor implements GpenPropertyDescriptor {

  val PropAccessor pa
  val boolean longValue

  new(PropAccessor pa, boolean longValue) {
    super(pa.name, pa.name)
    this.pa = pa
    this.longValue = longValue
    category = 'Basic'
  }

  override getValue() { '' + pa.value }

  override isPropertySet() { false }

  override resetPropertyValue() {}

  Object prevValue = null

  override setValue(Object value) {
    if (prevValue == value) return;
    prevValue = value
    val str = value as String
    try {
      pa.value = if(longValue) Long.valueOf(str) else Integer.valueOf(str)
    } catch (NumberFormatException e) {
      throw new RuntimeException('Неверный формат числа: ' + e.message);
    }
  }
}
