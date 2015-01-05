package kz.greepto.gpen.editors.gpen.prop.sheet;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

public interface GpenPropertyDescriptor extends IPropertyDescriptor {
  boolean isPropertySet();
  
  void resetPropertyValue();
  
  void setValue(Object value);
  
  Object getValue();
}
