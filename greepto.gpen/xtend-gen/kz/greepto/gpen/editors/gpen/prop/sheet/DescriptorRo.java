package kz.greepto.gpen.editors.gpen.prop.sheet;

import kz.greepto.gpen.editors.gpen.prop.PropAccessor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

@SuppressWarnings("all")
public class DescriptorRo extends PropertyDescriptor {
  public DescriptorRo(final PropAccessor pa) {
    super(pa.getName(), pa.getName());
    this.setCategory("Basic");
  }
}
