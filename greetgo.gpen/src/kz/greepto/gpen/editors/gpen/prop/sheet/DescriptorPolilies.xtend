package kz.greepto.gpen.editors.gpen.prop.sheet

import kz.greepto.gpen.editors.gpen.prop.PropAccessor
import org.eclipse.jface.viewers.CellEditor
import org.eclipse.jface.viewers.TextCellEditor
import org.eclipse.swt.widgets.Composite
import org.eclipse.ui.views.properties.PropertyDescriptor
import org.eclipse.swt.SWT

class DescriptorPolilies extends PropertyDescriptor implements GpenPropertyDescriptor {

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

  override def CellEditor createPropertyEditor(Composite parent) {
    var editor = new TextCellEditor(parent, SWT.MULTI);
    if (validator !== null) {
      editor.validator = validator
    }
    return editor;
  }
}
