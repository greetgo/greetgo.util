package kz.greepto.gpen.editors.gpen.prop.sheet;

import com.google.common.base.Objects;
import java.lang.reflect.Field;
import java.util.Collections;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class GpenPropertySheetPage extends PropertySheetPage {
  public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
    try {
      super.selectionChanged(part, selection);
      Class<? extends GpenPropertySheetPage> _class = this.getClass();
      Class<?> _superclass = _class.getSuperclass();
      Field[] _declaredFields = _superclass.getDeclaredFields();
      final Function1<Field, Boolean> _function = new Function1<Field, Boolean>() {
        public Boolean apply(final Field it) {
          String _name = it.getName();
          return Boolean.valueOf(Objects.equal(_name, "viewer"));
        }
      };
      Field f = IterableExtensions.<Field>findFirst(((Iterable<Field>)Conversions.doWrapArray(_declaredFields)), _function);
      f.setAccessible(true);
      Object _get = f.get(this);
      Viewer viewer = ((Viewer) _get);
      boolean _tripleEquals = (viewer == null);
      if (_tripleEquals) {
        return;
      }
      Object[] _array = Collections.<ISelection>unmodifiableList(CollectionLiterals.<ISelection>newArrayList(selection)).toArray();
      viewer.setInput(_array);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
