package kz.greepto.gpen.editors.gpen.model.paint;

import com.google.common.base.Objects;
import kz.greepto.gpen.drawport.DrawPort;
import kz.greepto.gpen.drawport.Rect;
import kz.greepto.gpen.drawport.RectGeom;
import kz.greepto.gpen.drawport.Size;
import kz.greepto.gpen.drawport.StrGeom;
import kz.greepto.gpen.drawport.Style;
import kz.greepto.gpen.editors.gpen.model.Button;
import kz.greepto.gpen.editors.gpen.model.paint.AbstractPaint;
import kz.greepto.gpen.editors.gpen.style.ButtonStyle;
import kz.greepto.gpen.editors.gpen.style.PaintStatus;
import kz.greepto.gpen.editors.gpen.style.StyleCalc;
import org.eclipse.swt.graphics.Point;

@SuppressWarnings("all")
public class PaintButton extends AbstractPaint {
  public PaintButton(final DrawPort dp, final StyleCalc styleCalc) {
    super(dp, styleCalc);
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
    RectGeom _from = this.dp.from(ret);
    _from.fill();
    Style _style_1 = this.dp.style();
    _style_1.setForeground(style.borderColor);
    RectGeom _from_1 = this.dp.from(ret);
    _from_1.draw();
    Style _style_2 = this.dp.style();
    _style_2.setForeground(style.color);
    int dx = (((ret.width - size.width) / 2) + paddingLeft);
    int dy = (((ret.height - size.height) / 2) + paddingTop);
    StrGeom _str_1 = this.dp.str(b.text);
    _str_1.draw((ret.x + dx), (ret.y + dy));
    return ret;
  }
}
