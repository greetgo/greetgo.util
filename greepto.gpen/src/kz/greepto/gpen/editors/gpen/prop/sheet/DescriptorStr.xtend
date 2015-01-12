package kz.greepto.gpen.editors.gpen.prop.sheet

import kz.greepto.gpen.editors.gpen.prop.PropAccessor
import org.eclipse.jface.viewers.CellEditor
import org.eclipse.jface.viewers.TextCellEditor
import org.eclipse.swt.widgets.Composite
import org.eclipse.ui.views.properties.PropertyDescriptor

class DescriptorStr extends PropertyDescriptor implements GpenPropertyDescriptor {

  val PropAccessor pa

  new(PropAccessor pa) {
    super(pa.name, pa.name)
    this.pa = pa
    category = 'Basic'
  }

  override getValue() { pa.value }

  override isPropertySet() { false }

  override resetPropertyValue() {}

  override setValue(Object value) {
    if (value instanceof String) {
      pa.value = value
    }
  }

  override CellEditor createPropertyEditor(Composite parent) {
    return new TextCellEditor(parent) {
      override protected doSetValue(Object value) {
        if (value instanceof String) {
          super.doSetValue(value)
        }
      }
    }
  }
}
