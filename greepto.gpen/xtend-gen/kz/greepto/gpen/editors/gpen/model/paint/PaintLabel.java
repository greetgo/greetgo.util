package kz.greepto.gpen.editors.gpen.model.paint;

import com.google.common.base.Objects;
import kz.greepto.gpen.drawport.DrawPort;
import kz.greepto.gpen.drawport.Rect;
import kz.greepto.gpen.drawport.Size;
import kz.greepto.gpen.drawport.StrGeom;
import kz.greepto.gpen.drawport.Style;
import kz.greepto.gpen.drawport.Vec2;
import kz.greepto.gpen.editors.gpen.model.Label;
import kz.greepto.gpen.editors.gpen.model.paint.AbstractPaint;
import kz.greepto.gpen.editors.gpen.style.LabelStyle;
import kz.greepto.gpen.editors.gpen.style.PaintStatus;
import kz.greepto.gpen.editors.gpen.style.StyleCalc;
import org.eclipse.swt.graphics.Point;

@SuppressWarnings("all")
public class PaintLabel extends AbstractPaint {
  public PaintLabel(final DrawPort dp, final StyleCalc styleCalc) {
    super(dp, styleCalc);
  }
  
  public Rect placePaint(final Label label, final Point mouse) {
    PaintStatus _sel = PaintStatus.sel(label.sel);
    LabelStyle calc = this.styleCalc.calcForLabel(label, _sel);
    Style _style = this.dp.style();
    _style.setForeground(calc.color);
    this.dp.setFont(calc.font);
    Vec2 _point = label.getPoint();
    StrGeom _str = this.dp.str(label.text);
    Size _size = _str.size();
    Rect bounds = Rect.pointSize(_point, _size);
    boolean _equals = Objects.equal(mouse, null);
    if (_equals) {
      return bounds;
    }
    boolean hover = bounds.contains(mouse);
    if (hover) {
      PaintStatus _selHover = PaintStatus.selHover(label.sel);
      LabelStyle _calcForLabel = this.styleCalc.calcForLabel(label, _selHover);
      calc = _calcForLabel;
      Style _style_1 = this.dp.style();
      _style_1.setForeground(calc.color);
      this.dp.setFont(calc.font);
    }
    StrGeom _str_1 = this.dp.str(label.text);
    Vec2 _point_1 = label.getPoint();
    _str_1.draw(_point_1);
    return bounds;
  }
}
