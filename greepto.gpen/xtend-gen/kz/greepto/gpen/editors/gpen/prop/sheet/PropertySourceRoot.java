package kz.greepto.gpen.editors.gpen.prop.sheet;

import kz.greepto.gpen.editors.gpen.GpenSelection;
import kz.greepto.gpen.editors.gpen.prop.PropAccessor;
import kz.greepto.gpen.editors.gpen.prop.PropList;
import kz.greepto.gpen.editors.gpen.prop.sheet.DescriptorRo;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class PropertySourceRoot implements IPropertySource {
  public Object getEditableValue() {
    return this;
  }
  
  private final GpenSelection selection;
  
  private PropertyDescriptor[] propertyDescriptors = {};
  
  private PropList propList = PropList.empty();
  
  public PropertySourceRoot(final GpenSelection selection) {
    this.selection = selection;
    this.calcDescriptors();
  }
  
  public void calcDescriptors() {
    int _size = this.selection.ids.size();
    boolean _equals = (_size == 0);
    if (_equals) {
      return;
    }
    PropList _propList = this.selection.getPropList();
    this.propList = _propList;
    final Function1<PropAccessor, PropertyDescriptor> _function = new Function1<PropAccessor, PropertyDescriptor>() {
      public PropertyDescriptor apply(final PropAccessor it) {
        return PropertySourceRoot.descriptorFor(it);
      }
    };
    Iterable<PropertyDescriptor> _map = IterableExtensions.<PropAccessor, PropertyDescriptor>map(this.propList, _function);
    this.propertyDescriptors = ((PropertyDescriptor[])Conversions.unwrapArray(_map, PropertyDescriptor.class));
  }
  
  private static PropertyDescriptor descriptorFor(final PropAccessor pa) {
    return new DescriptorRo(pa);
  }
  
  public IPropertyDescriptor[] getPropertyDescriptors() {
    return this.propertyDescriptors;
  }
  
  public Object getPropertyValue(final Object id) {
    PropAccessor prop = this.propList.byName(((String) id));
    boolean _tripleEquals = (prop == null);
    if (_tripleEquals) {
      throw new IllegalArgumentException(("No property for " + id));
    }
    return prop.getValue();
  }
  
  public boolean isPropertySet(final Object id) {
    return false;
  }
  
  public void resetPropertyValue(final Object id) {
  }
  
  public void setPropertyValue(final Object id, final Object value) {
  }
}
