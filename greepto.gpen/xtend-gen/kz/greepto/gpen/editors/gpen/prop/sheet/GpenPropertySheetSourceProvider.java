package kz.greepto.gpen.editors.gpen.prop.sheet;

import kz.greepto.gpen.editors.gpen.GpenSelection;
import kz.greepto.gpen.editors.gpen.prop.PropAccessor;
import kz.greepto.gpen.editors.gpen.prop.sheet.PropertySourceRo;
import kz.greepto.gpen.editors.gpen.prop.sheet.PropertySourceRoot;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class GpenPropertySheetSourceProvider implements IPropertySourceProvider {
  public IPropertySource getPropertySource(final Object object) {
    if ((object instanceof GpenSelection)) {
      final GpenSelection sel = ((GpenSelection) object);
      boolean _isEmpty = sel.isEmpty();
      if (_isEmpty) {
        return null;
      }
      InputOutput.<String>println(("PropertySourceRoot " + sel));
      return new PropertySourceRoot(sel);
    }
    if ((object instanceof PropAccessor)) {
      InputOutput.<String>println(("prop " + object));
      return new PropertySourceRo(((PropAccessor) object));
    }
    return null;
  }
}
