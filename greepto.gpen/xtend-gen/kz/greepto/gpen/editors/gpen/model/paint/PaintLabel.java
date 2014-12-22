package kz.greepto.gpen.editors.gpen.model.paint;

import com.google.common.base.Objects;
import kz.greepto.gpen.drawport.Rect;
import kz.greepto.gpen.drawport.Size;
import kz.greepto.gpen.drawport.StrGeom;
import kz.greepto.gpen.drawport.Style;
import kz.greepto.gpen.drawport.Vec2;
import kz.greepto.gpen.editors.gpen.model.Label;
import kz.greepto.gpen.editors.gpen.model.paint.AbstractPaint;
import kz.greepto.gpen.editors.gpen.model.paint.PaintResult;
import kz.greepto.gpen.editors.gpen.style.LabelStyle;
import kz.greepto.gpen.editors.gpen.style.PaintStatus;

@SuppressWarnings("all")
public class PaintLabel extends AbstractPaint {
  private Label label;
  
  public PaintLabel(final Label label) {
    this.label = label;
  }
  
  public String getFigureId() {
    return this.label.id;
  }
  
  public PaintResult work(final Vec2 mouse) {
    boolean _isSel = this.isSel(this.label);
    PaintStatus _sel = PaintStatus.sel(_isSel);
    LabelStyle calc = this.styleCalc.calcForLabel(this.label, _sel);
    Style _style = this.dp.style();
    _style.setForeground(calc.color);
    this.dp.setFont(calc.font);
    Vec2 _point = this.label.getPoint();
    StrGeom _str = this.dp.str(this.label.text);
    Size _size = _str.size();
    Rect place = Rect.pointSize(_point, _size);
    boolean _equals = Objects.equal(mouse, null);
    if (_equals) {
      return this.simpleRect(place);
    }
    boolean hover = place.contains(mouse);
    if (hover) {
      boolean _isSel_1 = this.isSel(this.label);
      PaintStatus _selHover = PaintStatus.selHover(_isSel_1);
      LabelStyle _calcForLabel = this.styleCalc.calcForLabel(this.label, _selHover);
      calc = _calcForLabel;
      Style _style_1 = this.dp.style();
      _style_1.setForeground(calc.color);
      this.dp.setFont(calc.font);
    }
    StrGeom _str_1 = this.dp.str(this.label.text);
    Vec2 _point_1 = this.label.getPoint();
    _str_1.draw(_point_1);
    boolean _and = false;
    boolean _isSel_2 = this.isSel(this.label);
    if (!_isSel_2) {
      _and = false;
    } else {
      boolean _tripleNotEquals = (calc.focusColor != null);
      _and = _tripleNotEquals;
    }
    if (_and) {
      Style _style_2 = this.dp.style();
      _style_2.setForeground(calc.focusColor);
      this.drawAroundFocus(place);
    }
    return this.modiPosition(mouse, place, this.label);
  }
}
