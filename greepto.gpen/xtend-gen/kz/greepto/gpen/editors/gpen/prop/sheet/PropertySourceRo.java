package kz.greepto.gpen.editors.gpen.prop.sheet;

import kz.greepto.gpen.editors.gpen.prop.PropAccessor;
import kz.greepto.gpen.editors.gpen.prop.sheet.DescriptorRo;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class PropertySourceRo implements IPropertySource {
  public Object getEditableValue() {
    return this;
  }
  
  private final PropertyDescriptor[] propertyDescriptors;
  
  private PropAccessor prop;
  
  public PropertySourceRo(final PropAccessor prop) {
    this.prop = prop;
    DescriptorRo _descriptorRo = new DescriptorRo(prop);
    this.propertyDescriptors = new PropertyDescriptor[] { _descriptorRo };
  }
  
  public IPropertyDescriptor[] getPropertyDescriptors() {
    return this.propertyDescriptors;
  }
  
  public Object getPropertyValue(final Object id) {
    InputOutput.<String>println(("-- getPropertyValue id = " + id));
    Object _value = this.prop.getValue();
    return ("" + _value);
  }
  
  public boolean isPropertySet(final Object id) {
    return true;
  }
  
  public void resetPropertyValue(final Object id) {
  }
  
  public void setPropertyValue(final Object id, final Object value) {
  }
}
