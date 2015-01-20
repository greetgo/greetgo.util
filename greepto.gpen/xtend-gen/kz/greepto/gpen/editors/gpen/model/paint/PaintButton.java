package kz.greepto.gpen.editors.gpen.model.paint;

import com.google.common.base.Objects;
import kz.greepto.gpen.drawport.ArcGeom;
import kz.greepto.gpen.drawport.Geom;
import kz.greepto.gpen.drawport.Kolor;
import kz.greepto.gpen.drawport.Rect;
import kz.greepto.gpen.drawport.RectGeom;
import kz.greepto.gpen.drawport.RoundRectGeom;
import kz.greepto.gpen.drawport.Size;
import kz.greepto.gpen.drawport.StrGeom;
import kz.greepto.gpen.drawport.Style;
import kz.greepto.gpen.drawport.Vec2;
import kz.greepto.gpen.editors.gpen.model.Button;
import kz.greepto.gpen.editors.gpen.model.paint.AbstractPaint;
import kz.greepto.gpen.editors.gpen.model.paint.PaintResult;
import kz.greepto.gpen.editors.gpen.style.ButtonStyle;
import kz.greepto.gpen.editors.gpen.style.PaintStatus;

@SuppressWarnings("all")
public class PaintButton extends AbstractPaint {
  private Button b;
  
  public PaintButton(final Button b) {
    this.b = b;
  }
  
  public String getFigureId() {
    return this.b.id;
  }
  
  public PaintResult work(final Vec2 mouse) {
    PaintStatus ps = new PaintStatus();
    boolean _isSel = this.isSel(this.b);
    ps.selected = _isSel;
    ps.disabled = this.b.disabled;
    ButtonStyle style = this.styleCalc.calcForButton(this.b, ps);
    int _x = this.b.getX();
    int _y = this.b.getY();
    int _width = this.b.getWidth();
    int _height = this.b.getHeight();
    Rect place = Rect.from(_x, _y, _width, _height);
    boolean _and = false;
    if (!((!this.b.autoWidth) && (!this.b.autoHeight))) {
      _and = false;
    } else {
      boolean _tripleEquals = (mouse == null);
      _and = _tripleEquals;
    }
    if (_and) {
      return this.modiBounds(mouse, place, this.b);
    }
    this.dp.setFont(style.font);
    StrGeom _str = this.dp.str(this.b.text);
    Size size = _str.size();
    int paddingLeft = 0;
    int paddingTop = 0;
    boolean _notEquals = (!Objects.equal(style.padding, null));
    if (_notEquals) {
      paddingLeft = style.padding.left;
      paddingTop = style.padding.top;
      int _width_1 = size.width;
      size.width = (_width_1 + (style.padding.left + style.padding.right));
      int _height_1 = size.height;
      size.height = (_height_1 + (style.padding.top + style.padding.bottom));
    }
    boolean _or = false;
    if (this.b.autoWidth) {
      _or = true;
    } else {
      int _width_2 = this.b.getWidth();
      boolean _lessThan = (_width_2 < size.width);
      _or = _lessThan;
    }
    if (_or) {
      place.width = size.width;
    }
    boolean _or_1 = false;
    if (this.b.autoHeight) {
      _or_1 = true;
    } else {
      int _height_2 = this.b.getHeight();
      boolean _lessThan_1 = (_height_2 < size.height);
      _or_1 = _lessThan_1;
    }
    if (_or_1) {
      place.height = size.height;
    }
    boolean _equals = Objects.equal(mouse, null);
    if (_equals) {
      return this.modiBounds(mouse, place, this.b);
    }
    boolean _contains = place.contains(mouse);
    if (_contains) {
      PaintStatus ps2 = new PaintStatus();
      boolean _isSel_1 = this.isSel(this.b);
      ps2.selected = _isSel_1;
      ps2.disabled = this.b.disabled;
      ps2.hover = true;
      ButtonStyle _calcForButton = this.styleCalc.calcForButton(this.b, ps2);
      style = _calcForButton;
    }
    this.dp.setFont(style.font);
    Style _style = this.dp.style();
    _style.setBackground(style.backgroundColor);
    int r = 5;
    RectGeom _from = this.dp.from(place);
    RoundRectGeom _round = _from.round(r);
    _round.fill();
    Style _style_1 = this.dp.style();
    Kolor _brighter = style.borderColor.brighter();
    _style_1.setForeground(_brighter);
    Vec2 _point = place.getPoint();
    Geom _from_1 = this.dp.from(_point);
    Geom _size = _from_1.size(r, r);
    RectGeom _rect = _size.rect();
    ArcGeom _arc = _rect.arc(90, 90);
    _arc.draw();
    Vec2 _point_1 = place.getPoint();
    Size _size_1 = place.getSize();
    Vec2 _plus = _point_1.operator_plus(new int[] { _size_1.width, 0 });
    Vec2 _minus = _plus.operator_minus(new int[] { r, 0 });
    Geom _from_2 = this.dp.from(_minus);
    Geom _size_2 = _from_2.size(r, r);
    RectGeom _rect_1 = _size_2.rect();
    ArcGeom _arc_1 = _rect_1.arc(45, 45);
    _arc_1.draw();
    Vec2 _point_2 = place.getPoint();
    Vec2 _plus_1 = _point_2.operator_plus(new int[] { (r / 2), 0 });
    Geom _from_3 = this.dp.from(_plus_1);
    Geom _shift = _from_3.shift((place.getSize().width - r), 0);
    _shift.line();
    Vec2 _point_3 = place.getPoint();
    Vec2 _plus_2 = _point_3.operator_plus(new int[] { 0, (r / 2) });
    Geom _from_4 = this.dp.from(_plus_2);
    Geom _shift_1 = _from_4.shift(0, (place.getSize().height - r));
    _shift_1.line();
    Style _style_2 = this.dp.style();
    Kolor _darker = style.borderColor.darker();
    _style_2.setForeground(_darker);
    Vec2 _point_4 = place.getPoint();
    Size _size_3 = place.getSize();
    Vec2 _plus_3 = _point_4.operator_plus(new int[] { _size_3.width, 0 });
    Vec2 _minus_1 = _plus_3.operator_minus(new int[] { r, 0 });
    Geom _from_5 = this.dp.from(_minus_1);
    Geom _size_4 = _from_5.size(r, r);
    RectGeom _rect_2 = _size_4.rect();
    ArcGeom _arc_2 = _rect_2.arc(0, 45);
    _arc_2.draw();
    Vec2 _point_5 = place.getPoint();
    Size _size_5 = place.getSize();
    Vec2 _plus_4 = _point_5.operator_plus(_size_5);
    Vec2 _minus_2 = _plus_4.operator_minus(new int[] { r, r });
    Geom _from_6 = this.dp.from(_minus_2);
    Geom _size_6 = _from_6.size(r, r);
    RectGeom _rect_3 = _size_6.rect();
    ArcGeom _arc_3 = _rect_3.arc((3 * 90), 90);
    _arc_3.draw();
    Vec2 _point_6 = place.getPoint();
    Vec2 _plus_5 = _point_6.operator_plus(new int[] { 0, place.height });
    Vec2 _plus_6 = _plus_5.operator_plus(new int[] { 0, (-r) });
    Geom _from_7 = this.dp.from(_plus_6);
    Geom _size_7 = _from_7.size(r, r);
    RectGeom _rect_4 = _size_7.rect();
    ArcGeom _arc_4 = _rect_4.arc(((2 * 90) + 45), 45);
    _arc_4.draw();
    Vec2 _point_7 = place.getPoint();
    Size _size_8 = place.getSize();
    Vec2 _plus_7 = _point_7.operator_plus(new int[] { _size_8.width, (r / 2) });
    Geom _from_8 = this.dp.from(_plus_7);
    Geom _shift_2 = _from_8.shift(0, (place.getSize().height - r));
    _shift_2.line();
    Vec2 _point_8 = place.getPoint();
    Size _size_9 = place.getSize();
    Vec2 _plus_8 = _point_8.operator_plus(_size_9);
    Vec2 _minus_3 = _plus_8.operator_minus(new int[] { (r / 2), 0 });
    Geom _from_9 = this.dp.from(_minus_3);
    Geom _shift_3 = _from_9.shift(((-place.getSize().width) + r), 0);
    _shift_3.line();
    Style _style_3 = this.dp.style();
    Kolor _brighter_1 = style.borderColor.brighter();
    _style_3.setForeground(_brighter_1);
    Vec2 _point_9 = place.getPoint();
    Vec2 _plus_9 = _point_9.operator_plus(new int[] { 0, place.height });
    Vec2 _plus_10 = _plus_9.operator_plus(new int[] { 0, (-r) });
    Geom _from_10 = this.dp.from(_plus_10);
    Geom _size_10 = _from_10.size(r, r);
    RectGeom _rect_5 = _size_10.rect();
    ArcGeom _arc_5 = _rect_5.arc((2 * 90), 45);
    _arc_5.draw();
    Style _style_4 = this.dp.style();
    _style_4.setForeground(style.color);
    int dx = (((place.width - size.width) / 2) + paddingLeft);
    int dy = (((place.height - size.height) / 2) + paddingTop);
    StrGeom _str_1 = this.dp.str(this.b.text);
    _str_1.draw((place.x + dx), (place.y + dy));
    boolean _and_1 = false;
    boolean _isSel_2 = this.isSel(this.b);
    if (!_isSel_2) {
      _and_1 = false;
    } else {
      boolean _tripleNotEquals = (style.focusColor != null);
      _and_1 = _tripleNotEquals;
    }
    if (_and_1) {
      Style _style_5 = this.dp.style();
      _style_5.setForeground(style.focusColor);
      this.drawAroundFocus(place);
    }
    return this.modiBounds(mouse, place, this.b);
  }
}
