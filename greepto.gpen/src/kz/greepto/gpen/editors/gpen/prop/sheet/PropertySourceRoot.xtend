package kz.greepto.gpen.editors.gpen.prop.sheet

import java.util.Map
import kz.greepto.gpen.editors.gpen.GpenSelection
import kz.greepto.gpen.editors.gpen.prop.PropAccessor
import kz.greepto.gpen.editors.gpen.prop.PropList
import org.eclipse.ui.views.properties.IPropertySource

class PropertySourceRoot implements IPropertySource {

  override getEditableValue() {
    return this
  }

  val GpenSelection selection
  var GpenPropertyDescriptor[] propertyDescriptors = #[]
  var PropList propList = PropList.empty
  val Map<Object, GpenPropertyDescriptor> descriptorMap = newHashMap

  new(GpenSelection selection) {
    this.selection = selection
    calcDescriptors
  }

  def void calcDescriptors() {
    if(selection.ids.size == 0) return;

    propList = selection.propList

    propertyDescriptors = propList.map[descriptorFor]

    for (gpd : propertyDescriptors) {
      descriptorMap.put(gpd.id, gpd)
    }
  }

  override getPropertyDescriptors() { propertyDescriptors }

  override getPropertyValue(Object id) { descriptorMap.get(id).value }

  override isPropertySet(Object id) { descriptorMap.get(id).propertySet }

  override resetPropertyValue(Object id) { descriptorMap.get(id).resetPropertyValue }

  override setPropertyValue(Object id, Object value) { descriptorMap.get(id).value = value }

  private def static GpenPropertyDescriptor descriptorFor(PropAccessor pa) {
    if (!pa.options.readonly) {
      if (pa.type == String) {
        if (pa.options.polilines) return new DescriptorPolilies(pa)
        return new DescriptorStr(pa)
      }
      if (pa.options.polilines) throw new RuntimeException('Polilines may be only for string field')
    }
    
    return new DescriptorRo(pa)
  }
}
