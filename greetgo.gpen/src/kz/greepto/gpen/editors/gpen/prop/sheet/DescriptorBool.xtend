package kz.greepto.gpen.editors.gpen.prop.sheet

import kz.greepto.gpen.editors.gpen.prop.PropAccessor
import org.eclipse.jface.viewers.CellEditor
import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.events.SelectionListener
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Composite
import org.eclipse.ui.views.properties.PropertyDescriptor

class DescriptorBool extends PropertyDescriptor implements GpenPropertyDescriptor {

  val PropAccessor pa

  new(PropAccessor pa) {
    super(pa.name, pa.name)
    this.pa = pa
    category = 'Basic'
  }

  override getValue() {
    var value = pa.value
    if (value instanceof Boolean) {
      return if(value as Boolean) 'ДА' else 'НЕТ'
    }
    return '' + value
  }

  override isPropertySet() { false }

  override resetPropertyValue() {}

  Object prevValue = null

  override setValue(Object value) {
    if(prevValue == value) return;
    pa.value = prevValue = value
  }

  override CellEditor createPropertyEditor(Composite parent) {

    return new CellEditor(parent) {

      Button trueButton
      Button falseButton

      override protected createControl(Composite parent) {
        var Composite wall = new Composite(parent, SWT.NONE)

        var lay = new GridLayout
        lay.numColumns = 2
        wall.layout = lay

        trueButton = new Button(wall, SWT.RADIO)
        trueButton.text = 'ДА'
        falseButton = new Button(wall, SWT.RADIO)
        falseButton.text = 'НЕТ'

        val selectionListener = new SelectionListener() {
          override widgetDefaultSelected(SelectionEvent e) {
            doFireApplyEditorValue
          }

          override widgetSelected(SelectionEvent e) {
            doFireApplyEditorValue
          }
        }

        trueButton.addSelectionListener(selectionListener)
        falseButton.addSelectionListener(selectionListener)

        return wall
      }

      def doFireApplyEditorValue() {
        fireApplyEditorValue
      }

      override protected doGetValue() {
        if (trueButton?.selection) return true
        if (falseButton?.selection) return false
        return null
      }

      override protected doSetFocus() {
        if(trueButton !== null) trueButton.setFocus
      }

      override protected doSetValue(Object valueLeft) {
        if(trueButton === null) return;
        if(falseButton === null) return;
        val value = pa.value
        if (value instanceof Boolean) {
          var bool = value as Boolean
          trueButton.selection = bool
          falseButton.selection = !bool
        } else {
          trueButton.selection = false
          falseButton.selection = false
        }
      }
    }
  }

}
