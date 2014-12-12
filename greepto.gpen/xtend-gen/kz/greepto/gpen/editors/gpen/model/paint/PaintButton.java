package kz.greepto.gpen.editors.gpen.model.paint;

import com.google.common.base.Objects;
import kz.greepto.gpen.drawport.ArcGeom;
import kz.greepto.gpen.drawport.DrawPort;
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
import kz.greepto.gpen.editors.gpen.style.ButtonStyle;
import kz.greepto.gpen.editors.gpen.style.PaintStatus;
import kz.greepto.gpen.editors.gpen.style.StyleCalc;
import org.eclipse.swt.graphics.Point;

@SuppressWarnings("all")
public class PaintButton extends AbstractPaint {
  public PaintButton(final DrawPort dp, final StyleCalc styleCalc) {
    super(dp.copy(), styleCalc);
  }
  
  public Rect placePaint(final Button b, final Point mouse) {
    PaintStatus _sel = PaintStatus.sel(b.sel);
    ButtonStyle style = this.styleCalc.calcForButton(b, _sel);
    int _x = b.getX();
    int _y = b.getY();
    Rect ret = Rect.from(_x, _y, b.width, b.height);
    boolean _and = false;
    if (!((!b.autoWidth) && (!b.autoHeight))) {
      _and = false;
    } else {
      boolean _equals = Objects.equal(mouse, null);
      _and = _equals;
    }
    if (_and) {
      return ret;
    }
    this.dp.setFont(style.font);
    StrGeom _str = this.dp.str(b.text);
    Size size = _str.size();
    int paddingLeft = 0;
    int paddingTop = 0;
    boolean _notEquals = (!Objects.equal(style.padding, null));
    if (_notEquals) {
      paddingLeft = style.padding.left;
      paddingTop = style.padding.top;
      int _width = size.width;
      size.width = (_width + (style.padding.left + style.padding.right));
      int _height = size.height;
      size.height = (_height + (style.padding.top + style.padding.bottom));
    }
    if (b.autoWidth) {
      ret.width = size.width;
    }
    if (b.autoHeight) {
      ret.height = size.height;
    }
    b.setSize(size);
    boolean _equals_1 = Objects.equal(mouse, null);
    if (_equals_1) {
      return ret;
    }
    boolean _contains = ret.contains(mouse);
    if (_contains) {
      PaintStatus _selHover = PaintStatus.selHover(b.sel);
      ButtonStyle _calcForButton = this.styleCalc.calcForButton(b, _selHover);
      style = _calcForButton;
    }
    this.dp.setFont(style.font);
    Style _style = this.dp.style();
    _style.setBackground(style.backgroundColor);
    int r = 5;
    RectGeom _from = this.dp.from(ret);
    RoundRectGeom _round = _from.round(r);
    _round.fill();
    Style _style_1 = this.dp.style();
    Kolor _brighter = style.borderColor.brighter();
    _style_1.setForeground(_brighter);
    Vec2 _point = ret.getPoint();
    Geom _from_1 = this.dp.from(_point);
    Geom _size = _from_1.size(r, r);
    RectGeom _rect = _size.rect();
    ArcGeom _arc = _rect.arc(90, 90);
    _arc.draw();
    Vec2 _point_1 = ret.getPoint();
    Size _size_1 = ret.getSize();
    Vec2 _plus = _point_1.operator_plus(new int[] { _size_1.width, 0 });
    Vec2 _minus = _plus.operator_minus(new int[] { r, 0 });
    Geom _from_2 = this.dp.from(_minus);
    Geom _size_2 = _from_2.size(r, r);
    RectGeom _rect_1 = _size_2.rect();
    ArcGeom _arc_1 = _rect_1.arc(45, 45);
    _arc_1.draw();
    Vec2 _point_2 = ret.getPoint();
    Vec2 _plus_1 = _point_2.operator_plus(new int[] { (r / 2), 0 });
    Geom _from_3 = this.dp.from(_plus_1);
    Geom _shift = _from_3.shift((ret.getSize().width - r), 0);
    _shift.line();
    Vec2 _point_3 = ret.getPoint();
    Vec2 _plus_2 = _point_3.operator_plus(new int[] { 0, (r / 2) });
    Geom _from_4 = this.dp.from(_plus_2);
    Geom _shift_1 = _from_4.shift(0, (ret.getSize().height - r));
    _shift_1.line();
    Style _style_2 = this.dp.style();
    Kolor _darker = style.borderColor.darker();
    _style_2.setForeground(_darker);
    Vec2 _point_4 = ret.getPoint();
    Size _size_3 = ret.getSize();
    Vec2 _plus_3 = _point_4.operator_plus(new int[] { _size_3.width, 0 });
    Vec2 _minus_1 = _plus_3.operator_minus(new int[] { r, 0 });
    Geom _from_5 = this.dp.from(_minus_1);
    Geom _size_4 = _from_5.size(r, r);
    RectGeom _rect_2 = _size_4.rect();
    ArcGeom _arc_2 = _rect_2.arc(0, 45);
    _arc_2.draw();
    Vec2 _point_5 = ret.getPoint();
    Size _size_5 = ret.getSize();
    Vec2 _plus_4 = _point_5.operator_plus(_size_5);
    Vec2 _minus_2 = _plus_4.operator_minus(new int[] { r, r });
    Geom _from_6 = this.dp.from(_minus_2);
    Geom _size_6 = _from_6.size(r, r);
    RectGeom _rect_3 = _size_6.rect();
    ArcGeom _arc_3 = _rect_3.arc((3 * 90), 90);
    _arc_3.draw();
    Vec2 _point_6 = ret.getPoint();
    Vec2 _plus_5 = _point_6.operator_plus(new int[] { 0, ret.height });
    Vec2 _plus_6 = _plus_5.operator_plus(new int[] { 0, (-r) });
    Geom _from_7 = this.dp.from(_plus_6);
    Geom _size_7 = _from_7.size(r, r);
    RectGeom _rect_4 = _size_7.rect();
    ArcGeom _arc_4 = _rect_4.arc(((2 * 90) + 45), 45);
    _arc_4.draw();
    Vec2 _point_7 = ret.getPoint();
    Size _size_8 = ret.getSize();
    Vec2 _plus_7 = _point_7.operator_plus(new int[] { _size_8.width, (r / 2) });
    Geom _from_8 = this.dp.from(_plus_7);
    Geom _shift_2 = _from_8.shift(0, (ret.getSize().height - r));
    _shift_2.line();
    Vec2 _point_8 = ret.getPoint();
    Size _size_9 = ret.getSize();
    Vec2 _plus_8 = _point_8.operator_plus(_size_9);
    Vec2 _minus_3 = _plus_8.operator_minus(new int[] { (r / 2), 0 });
    Geom _from_9 = this.dp.from(_minus_3);
    Geom _shift_3 = _from_9.shift(((-ret.getSize().width) + r), 0);
    _shift_3.line();
    Style _style_3 = this.dp.style();
    Kolor _brighter_1 = style.borderColor.brighter();
    _style_3.setForeground(_brighter_1);
    Vec2 _point_9 = ret.getPoint();
    Vec2 _plus_9 = _point_9.operator_plus(new int[] { 0, ret.height });
    Vec2 _plus_10 = _plus_9.operator_plus(new int[] { 0, (-r) });
    Geom _from_10 = this.dp.from(_plus_10);
    Geom _size_10 = _from_10.size(r, r);
    RectGeom _rect_5 = _size_10.rect();
    ArcGeom _arc_5 = _rect_5.arc((2 * 90), 45);
    _arc_5.draw();
    Style _style_4 = this.dp.style();
    _style_4.setForeground(style.color);
    int dx = (((ret.width - size.width) / 2) + paddingLeft);
    int dy = (((ret.height - size.height) / 2) + paddingTop);
    StrGeom _str_1 = this.dp.str(b.text);
    _str_1.draw((ret.x + dx), (ret.y + dy));
    return ret;
  }
}
