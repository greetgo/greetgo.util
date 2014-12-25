package kz.greepto.gpen.editors.gpen.prop.sheet;

import org.eclipse.ui.views.properties.PropertyDescriptor;

@SuppressWarnings("all")
public class DescriptorRo extends PropertyDescriptor {
  public DescriptorRo(final Object id, final String displayName) {
    super(id, displayName);
    this.setCategory("Basic");
  }
}
