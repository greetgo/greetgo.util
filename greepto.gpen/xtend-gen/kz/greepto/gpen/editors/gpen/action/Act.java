package kz.greepto.gpen.editors.gpen.action;

import com.google.common.base.Objects;
import java.util.List;
import kz.greepto.gpen.editors.gpen.action.Action;
import kz.greepto.gpen.editors.gpen.action.ActionAppend;
import kz.greepto.gpen.editors.gpen.action.UnknownAction;
import kz.greepto.gpen.editors.gpen.action.UnknownFigure;
import kz.greepto.gpen.editors.gpen.model.Button;
import kz.greepto.gpen.editors.gpen.model.Combo;
import kz.greepto.gpen.editors.gpen.model.IdFigure;
import kz.greepto.gpen.editors.gpen.model.Label;
import kz.greepto.gpen.editors.gpen.model.PointFigure;
import org.eclipse.xtext.xbase.lib.Conversions;

@SuppressWarnings("all")
public class Act {
  public static Action p(final String act, final String... params) {
    Action _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (Objects.equal(act, "append")) {
        _matched=true;
        String _get = params[0];
        String _get_1 = params[1];
        int _size = ((List<String>)Conversions.doWrapArray(params)).size();
        List<String> _subList = ((List<String>)Conversions.doWrapArray(params)).subList(2, _size);
        _switchResult = Act.append(_get, _get_1, ((String[])Conversions.unwrapArray(_subList, String.class)));
      }
    }
    if (!_matched) {
      throw new UnknownAction(act);
    }
    return _switchResult;
  }
  
  public static Action append(final String fig, final String id, final String... params) {
    IdFigure _createFigure = Act.createFigure(fig, id, params);
    return new ActionAppend(_createFigure);
  }
  
  public static PointFigure createNewFigure(final String fig, final String id) {
    PointFigure _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (Objects.equal(fig, "Label")) {
        _matched=true;
        _switchResult = new Label(id);
      }
    }
    if (!_matched) {
      if (Objects.equal(fig, "Combo")) {
        _matched=true;
        _switchResult = new Combo(id);
      }
    }
    if (!_matched) {
      if (Objects.equal(fig, "Button")) {
        _matched=true;
        _switchResult = new Button(id);
      }
    }
    if (!_matched) {
      throw new UnknownFigure(fig);
    }
    return _switchResult;
  }
  
  public static IdFigure createFigure(final String fig, final String id, final String[] params) {
    PointFigure ret = Act.createNewFigure(fig, id);
    return ret;
  }
}
