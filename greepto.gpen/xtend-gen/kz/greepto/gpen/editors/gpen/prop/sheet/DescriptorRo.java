package kz.greepto.gpen.editors.gpen.prop.sheet;

import kz.greepto.gpen.editors.gpen.prop.PropAccessor;
import kz.greepto.gpen.editors.gpen.prop.sheet.GpenPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

@SuppressWarnings("all")
public class DescriptorRo extends PropertyDescriptor implements GpenPropertyDescriptor {
  private final PropAccessor pa;
  
  public DescriptorRo(final PropAccessor pa) {
    super(pa.getName(), pa.getName());
    this.pa = pa;
    this.setCategory("RO");
  }
  
  public Object getValue() {
    return this.pa.getValue();
  }
  
  public boolean isPropertySet() {
    return false;
  }
  
  public void resetPropertyValue() {
  }
  
  public void setValue(final Object value) {
  }
}
