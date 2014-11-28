package kz.greepto.gpen.editors.gpen.model.paint;

import com.google.common.base.Objects;
import kz.greepto.gpen.editors.gpen.model.Button;
import kz.greepto.gpen.editors.gpen.model.paint.AbstractPaint;
import kz.greepto.gpen.editors.gpen.style.ButtonStyle;
import kz.greepto.gpen.editors.gpen.style.PaintStatus;
import kz.greepto.gpen.editors.gpen.style.StyleCalc;
import kz.greepto.gpen.util.Rect;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

@SuppressWarnings("all")
public class PaintButton extends AbstractPaint {
  public PaintButton(final GC gc, final StyleCalc styleCalc) {
    super(gc, styleCalc);
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
    this.gc.setFont(style.font);
    Point size = this.gc.textExtent(b.text);
    if (b.autoWidth) {
      ret.width = size.x;
    }
    if (b.autoHeight) {
      ret.height = size.y;
    }
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
    this.gc.setFont(style.font);
    this.gc.setBackground(style.backgroundColor);
    this.gc.fillRectangle(ret.x, ret.y, ret.width, ret.height);
    this.gc.setForeground(style.borderColor);
    this.gc.drawRectangle(ret.x, ret.y, ret.width, ret.height);
    this.gc.setForeground(style.color);
    int dx = ((ret.width - size.x) / 2);
    int dy = ((ret.height - size.y) / 2);
    this.gc.drawText(b.text, (ret.x + dx), (ret.y + dy), true);
    return ret;
  }
}
