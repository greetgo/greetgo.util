package kz.greepto.gpen.editors.gpen.prop.sheet

import kz.greepto.gpen.editors.gpen.prop.PropAccessor
import org.eclipse.jface.viewers.CellEditor
import org.eclipse.jface.viewers.TextCellEditor
import org.eclipse.swt.events.MouseAdapter
import org.eclipse.swt.events.MouseEvent
import org.eclipse.swt.widgets.Composite
import org.eclipse.ui.views.properties.PropertyDescriptor

class DescriptorBool extends PropertyDescriptor implements GpenPropertyDescriptor {

  val PropAccessor pa

  new(PropAccessor pa) {
    super(pa.name, pa.name)
    this.pa = pa
    category = 'Basic'
  }

  override getValue() { '' + pa.value }

  override isPropertySet() { false }

  override resetPropertyValue() {}

  Object prevValue = null

  override setValue(Object value) {
    if(prevValue == value) return;
    prevValue = value
    val str = value as String

    pa.value = toBool(str)
  }

  private static def boolean toBool(Object value) {
    if(value === null) return false
    if(!(value instanceof String )) return false
    var s = (value as String).trim.toLowerCase

    if(s.length == 0) return false
    if(s == '0') return false
    if(s == 'f') return false
    if(s == 'false') return false
    if(s == 'n') return false
    if(s == 'no') return false
    if(s == 'off') return false

    return true
  }

  override CellEditor createPropertyEditor(Composite parent) {
    val editor = new TextCellEditor(parent);
    if(validator !== null) editor.validator = validator

    editor.control.addMouseListener(
      new MouseAdapter() {
        override mouseDoubleClick(MouseEvent e) {
          editor.value = '' + !toBool(editor.value)
        }
      })

    return editor;
  }

}
