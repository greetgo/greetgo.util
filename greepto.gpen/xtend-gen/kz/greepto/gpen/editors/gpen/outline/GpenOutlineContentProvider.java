package kz.greepto.gpen.editors.gpen.outline;

import com.google.common.base.Objects;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class GpenOutlineContentProvider implements ITreeContentProvider {
  public Object[] getElements(final Object inputElement) {
    return this.getChildren(null);
  }
  
  public Object[] getChildren(final Object parent) {
    boolean _tripleEquals = (parent == null);
    if (_tripleEquals) {
      return new Object[] { "root asd", "root dsa" };
    }
    boolean _equals = Objects.equal(parent, "root asd");
    if (_equals) {
      return new Object[] { "asd1", "asd2" };
    }
    boolean _equals_1 = Objects.equal(parent, "root dsa");
    if (_equals_1) {
      return new Object[] { "dsa1", "dsa2" };
    }
    return null;
  }
  
  public Object getParent(final Object element) {
    String _xblockexpression = null;
    {
      boolean _tripleEquals = (element == null);
      if (_tripleEquals) {
        return null;
      }
      String _switchResult = null;
      boolean _matched = false;
      if (!_matched) {
        if (Objects.equal(element, "asd1")) {
          _matched=true;
          _switchResult = "root asd";
        }
      }
      if (!_matched) {
        if (Objects.equal(element, "asd2")) {
          _matched=true;
          _switchResult = "root asd";
        }
      }
      if (!_matched) {
        if (Objects.equal(element, "dsa1")) {
          _matched=true;
          _switchResult = "root dsa";
        }
      }
      if (!_matched) {
        if (Objects.equal(element, "dsa2")) {
          _matched=true;
          _switchResult = "root dsa";
        }
      }
      if (!_matched) {
        _switchResult = null;
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  public boolean hasChildren(final Object element) {
    boolean _xblockexpression = false;
    {
      boolean _tripleEquals = (element == null);
      if (_tripleEquals) {
        return true;
      }
      boolean _switchResult = false;
      boolean _matched = false;
      if (!_matched) {
        if (Objects.equal(element, "root asd")) {
          _matched=true;
          _switchResult = true;
        }
      }
      if (!_matched) {
        if (Objects.equal(element, "root dsa")) {
          _matched=true;
          _switchResult = true;
        }
      }
      if (!_matched) {
        _switchResult = false;
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  public void dispose() {
  }
  
  public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
    InputOutput.<String>println(((("GpenOutline inputChanged from " + oldInput) + " to ") + newInput));
  }
}
