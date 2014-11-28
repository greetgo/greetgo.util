package kz.greepto.gpen.editors.gpen.model.paint;

import com.google.common.base.Objects;
import kz.greepto.gpen.editors.gpen.model.Label;
import kz.greepto.gpen.editors.gpen.model.paint.AbstractPaint;
import kz.greepto.gpen.editors.gpen.style.LabelStyle;
import kz.greepto.gpen.editors.gpen.style.PaintStatus;
import kz.greepto.gpen.editors.gpen.style.StyleCalc;
import kz.greepto.gpen.util.Rect;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

@SuppressWarnings("all")
public class PaintLabel extends AbstractPaint {
  public PaintLabel(final GC gc, final StyleCalc styleCalc) {
    super(gc, styleCalc);
  }
  
  public Rect placePaint(final Label label, final Point mouse) {
    PaintStatus _sel = PaintStatus.sel(label.sel);
    LabelStyle calc = this.styleCalc.calcForLabel(label, _sel);
    this.gc.setForeground(calc.color);
    this.gc.setFont(calc.font);
    Point _point = label.getPoint();
    Point _textExtent = this.gc.textExtent(label.text);
    Rect size = Rect.pointSize(_point, _textExtent);
    boolean _equals = Objects.equal(mouse, null);
    if (_equals) {
      return size;
    }
    boolean hover = size.contains(mouse);
    if (hover) {
      PaintStatus _selHover = PaintStatus.selHover(label.sel);
      LabelStyle _calcForLabel = this.styleCalc.calcForLabel(label, _selHover);
      calc = _calcForLabel;
      this.gc.setForeground(calc.color);
      this.gc.setFont(calc.font);
    }
    int _x = label.getX();
    int _y = label.getY();
    this.gc.drawText(label.text, _x, _y, true);
    return size;
  }
}
