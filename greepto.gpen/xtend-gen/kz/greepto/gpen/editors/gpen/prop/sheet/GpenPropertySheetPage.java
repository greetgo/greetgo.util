package kz.greepto.gpen.editors.gpen.prop.sheet;

import com.google.common.base.Objects;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kz.greepto.gpen.editors.gpen.GpenEditor;
import kz.greepto.gpen.editors.gpen.model.IdFigure;
import kz.greepto.gpen.editors.gpen.outline.GpenContentOutlinePage;
import kz.greepto.gpen.editors.gpen.prop.PropFactory;
import kz.greepto.gpen.editors.gpen.prop.PropList;
import kz.greepto.gpen.editors.gpen.prop.SceneWorker;
import kz.greepto.gpen.editors.gpen.prop.sheet.PropertySourceRoot;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.views.contentoutline.ContentOutline;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;

@SuppressWarnings("all")
public class GpenPropertySheetPage extends PropertySheetPage {
  private SceneWorker sceneWorker = null;
  
  public GpenPropertySheetPage() {
    this.setPropertySourceProvider(new IPropertySourceProvider() {
      public IPropertySource getPropertySource(final Object object) {
        boolean _tripleEquals = (GpenPropertySheetPage.this.sceneWorker == null);
        if (_tripleEquals) {
          return null;
        }
        if ((object instanceof IStructuredSelection)) {
          final IStructuredSelection sel = ((IStructuredSelection) object);
          boolean _isEmpty = sel.isEmpty();
          if (_isEmpty) {
            return null;
          }
          Iterator _iterator = sel.iterator();
          final Function1<String, IdFigure> _function = new Function1<String, IdFigure>() {
            public IdFigure apply(final String it) {
              return GpenPropertySheetPage.this.sceneWorker.findByIdOrDie(it);
            }
          };
          Iterator<IdFigure> _map = IteratorExtensions.<String, IdFigure>map(_iterator, _function);
          List<IdFigure> list = IteratorExtensions.<IdFigure>toList(_map);
          final PropList propList = PropFactory.<IdFigure>parseObjectList(list, GpenPropertySheetPage.this.sceneWorker);
          return new PropertySourceRoot(propList);
        }
        return null;
      }
    });
  }
  
  public Viewer getViewer() {
    try {
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
      return ((Viewer) _get);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
    super.selectionChanged(part, selection);
    if ((part instanceof GpenEditor)) {
      SceneWorker _sceneWorker = ((GpenEditor) part).getSceneWorker();
      this.sceneWorker = _sceneWorker;
    }
    if ((part instanceof ContentOutline)) {
      ContentOutline out = ((ContentOutline) part);
      IPage _currentPage = out.getCurrentPage();
      if ((_currentPage instanceof GpenContentOutlinePage)) {
        IPage _currentPage_1 = out.getCurrentPage();
        GpenContentOutlinePage page = ((GpenContentOutlinePage) _currentPage_1);
        this.sceneWorker = page.sceneWorker;
      }
    }
    boolean _and = false;
    boolean _tripleNotEquals = (this.sceneWorker != null);
    if (!_tripleNotEquals) {
      _and = false;
    } else {
      _and = (selection instanceof IStructuredSelection);
    }
    if (_and) {
      Viewer _viewer = this.getViewer();
      Object[] _array = Collections.<ISelection>unmodifiableList(CollectionLiterals.<ISelection>newArrayList(selection)).toArray();
      _viewer.setInput(_array);
    }
  }
}
