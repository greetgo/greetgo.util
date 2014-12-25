package kz.greepto.gpen.editors.gpen.prop.sheet;

import kz.greepto.gpen.editors.gpen.GpenSelection;
import kz.greepto.gpen.editors.gpen.prop.sheet.PropertySourceRoot;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

@SuppressWarnings("all")
public class GpenPropertySheetSourceProvider implements IPropertySourceProvider {
  public IPropertySource getPropertySource(final Object object) {
    if ((object instanceof GpenSelection)) {
      final GpenSelection sel = ((GpenSelection) object);
      boolean _isEmpty = sel.isEmpty();
      if (_isEmpty) {
        return null;
      }
      return new PropertySourceRoot(sel);
    }
    return null;
  }
}
