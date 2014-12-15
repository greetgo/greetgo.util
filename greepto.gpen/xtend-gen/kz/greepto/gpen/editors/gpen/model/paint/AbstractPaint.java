package kz.greepto.gpen.editors.gpen.model.paint;

import kz.greepto.gpen.drawport.DrawPort;
import kz.greepto.gpen.drawport.Geom;
import kz.greepto.gpen.drawport.Rect;
import kz.greepto.gpen.drawport.Size;
import kz.greepto.gpen.drawport.Vec2;
import kz.greepto.gpen.editors.gpen.style.StyleCalc;

@SuppressWarnings("all")
public abstract class AbstractPaint {
  protected final DrawPort dp;
  
  protected final StyleCalc styleCalc;
  
  public AbstractPaint(final DrawPort dp, final StyleCalc styleCalc) {
    this.dp = dp;
    this.styleCalc = styleCalc;
  }
  
  protected void drawAroundFocus(final Rect rect) {
    final int step = 10;
    final int period = 300;
    double offset = ((-(((double) (System.currentTimeMillis() % period)) / period)) * step);
    double skvaj = 0.5;
    Vec2 _point = rect.getPoint();
    Size _size = rect.getSize();
    Vec2 _plus = _point.operator_plus(_size);
    Vec2 _plus_1 = _plus.operator_plus(new int[] { 2, 2 });
    Geom _from = this.dp.from(_plus_1);
    Vec2 _from_1 = Vec2.from((-step), 0);
    Geom _to = _from.to(_from_1);
    double _dashLine = _to.dashLine(offset, skvaj, (rect.width + 4));
    offset = _dashLine;
    Vec2 _point_1 = rect.getPoint();
    Vec2 _plus_2 = _point_1.operator_plus(new int[] { (-2), (rect.height + 2) });
    Geom _from_2 = this.dp.from(_plus_2);
    Vec2 _from_3 = Vec2.from(0, (-step));
    Geom _to_1 = _from_2.to(_from_3);
    double _dashLine_1 = _to_1.dashLine(offset, skvaj, (rect.height + 4));
    offset = _dashLine_1;
    Vec2 _point_2 = rect.getPoint();
    Vec2 _minus = _point_2.operator_minus(new int[] { 2, 2 });
    Geom _from_4 = this.dp.from(_minus);
    Vec2 _from_5 = Vec2.from(step, 0);
    Geom _to_2 = _from_4.to(_from_5);
    double _dashLine_2 = _to_2.dashLine(offset, skvaj, (rect.width + 4));
    offset = _dashLine_2;
    Vec2 _point_3 = rect.getPoint();
    Vec2 _plus_3 = _point_3.operator_plus(new int[] { (rect.width + 3), (-2) });
    Geom _from_6 = this.dp.from(_plus_3);
    Vec2 _from_7 = Vec2.from(0, step);
    Geom _to_3 = _from_6.to(_from_7);
    double _dashLine_3 = _to_3.dashLine(offset, skvaj, (rect.height + 4));
    offset = _dashLine_3;
  }
}
