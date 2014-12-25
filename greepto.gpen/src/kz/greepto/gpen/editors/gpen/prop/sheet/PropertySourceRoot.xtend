package kz.greepto.gpen.editors.gpen.prop.sheet

import kz.greepto.gpen.editors.gpen.GpenSelection
import kz.greepto.gpen.editors.gpen.prop.PropFactory
import kz.greepto.gpen.editors.gpen.prop.PropList
import org.eclipse.ui.views.properties.IPropertySource
import org.eclipse.ui.views.properties.PropertyDescriptor

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
    var id = selection.ids.head
    if(id === null) return

    var figure = selection.scene.findByIdOrDie(id)
    propList = PropFactory.parseObject(figure, selection.sceneWorker)

    var list = newArrayList()
    for (pa : propList) {
      list += new DescriptorRo(pa.name, pa.name)
    }

    propertyDescriptors = list
  }

  override getPropertyDescriptors() { propertyDescriptors }

  override getPropertyValue(Object id) { propList.byName(id as String).value }

  override isPropertySet(Object id) { false }

  override resetPropertyValue(Object id) {}

  override setPropertyValue(Object id, Object value) {}

}
