package kz.greepto.gpen.editors.gpen.model.paint;

import kz.greepto.gpen.drawport.DrawPort;
import kz.greepto.gpen.drawport.Geom;
import kz.greepto.gpen.drawport.Kursor;
import kz.greepto.gpen.drawport.Rect;
import kz.greepto.gpen.drawport.Size;
import kz.greepto.gpen.drawport.Vec2;
import kz.greepto.gpen.editors.gpen.model.paint.MouseInfo;
import kz.greepto.gpen.editors.gpen.model.paint.PaintFigure;
import kz.greepto.gpen.editors.gpen.model.paint.PlaceInfo;
import kz.greepto.gpen.editors.gpen.style.StyleCalc;

@SuppressWarnings("all")
public abstract class AbstractPaint implements PaintFigure {
  public enum RectChangeType {
    POSITION,
    
    CORNER_LEFT_TOP,
    
    CORNER_LEFT_BOTTOM,
    
    CORNER_RIGHT_TOP,
    
    CORNER_RIGHT_BOTTOM,
    
    SIDE_TOP,
    
    SIDE_BOTTOM,
    
    SIDE_LEFT,
    
    SIDE_RIGHT;
  }
  
  public enum Direction {
    LEFT,
    
    UP,
    
    RIGHT,
    
    DOWN;
  }
  
  protected DrawPort dp;
  
  protected StyleCalc styleCalc;
  
  public AbstractPaint() {
  }
  
  public void setEnvironment(final DrawPort dp, final StyleCalc styleCalc) {
    this.dp = dp;
    this.styleCalc = styleCalc;
  }
  
  public Rect getPlace() {
    PlaceInfo _work = this.work(null);
    return _work.rect;
  }
  
  public MouseInfo paint(final Vec2 mouse) {
    PlaceInfo _work = this.work(mouse);
    return _work.mouseInfo;
  }
  
  public abstract PlaceInfo work(final Vec2 mouse);
  
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
  
  protected MouseInfo rectMouseInfo(final Vec2 mouse, final Rect rect, final boolean resizable) {
    boolean _contains = rect.contains(mouse);
    boolean _not = (!_contains);
    if (_not) {
      return null;
    }
    if (resizable) {
      MouseInfo mi = this.resizeMouseInfo(mouse, rect);
      boolean _tripleNotEquals = (mi != null);
      if (_tripleNotEquals) {
        return mi;
      }
    }
    return new MouseInfo(mouse, Kursor.SIZEALL, AbstractPaint.RectChangeType.POSITION);
  }
  
  private final int POL_TOL = 4;
  
  private final int CORNER_LEN = 15;
  
  private MouseInfo resizeMouseInfo(final Vec2 mouse, final Rect rect) {
    Object _xblockexpression = null;
    {
      Vec2 _point = rect.getPoint();
      Vec2 _minus = _point.operator_minus(new int[] { 2, 2 });
      Rect _calcDirRect = this.calcDirRect(_minus, this.CORNER_LEN, AbstractPaint.Direction.DOWN);
      boolean _contains = _calcDirRect.contains(mouse);
      if (_contains) {
        return new MouseInfo(mouse, Kursor.SIZENW, AbstractPaint.RectChangeType.CORNER_LEFT_TOP);
      }
      Vec2 _point_1 = rect.getPoint();
      Vec2 _minus_1 = _point_1.operator_minus(new int[] { 2, 2 });
      Rect _calcDirRect_1 = this.calcDirRect(_minus_1, this.CORNER_LEN, AbstractPaint.Direction.RIGHT);
      boolean _contains_1 = _calcDirRect_1.contains(mouse);
      if (_contains_1) {
        return new MouseInfo(mouse, Kursor.SIZENW, AbstractPaint.RectChangeType.CORNER_LEFT_TOP);
      }
      Vec2 _point_2 = rect.getPoint();
      Vec2 _plus = _point_2.operator_plus(new int[] { (-2), (rect.height + 2) });
      Rect _calcDirRect_2 = this.calcDirRect(_plus, this.CORNER_LEN, AbstractPaint.Direction.UP);
      boolean _contains_2 = _calcDirRect_2.contains(mouse);
      if (_contains_2) {
        return new MouseInfo(mouse, Kursor.SIZESW, AbstractPaint.RectChangeType.CORNER_LEFT_BOTTOM);
      }
      Vec2 _point_3 = rect.getPoint();
      Vec2 _plus_1 = _point_3.operator_plus(new int[] { (-2), (rect.height + 2) });
      Rect _calcDirRect_3 = this.calcDirRect(_plus_1, this.CORNER_LEN, AbstractPaint.Direction.RIGHT);
      boolean _contains_3 = _calcDirRect_3.contains(mouse);
      if (_contains_3) {
        return new MouseInfo(mouse, Kursor.SIZESW, AbstractPaint.RectChangeType.CORNER_LEFT_BOTTOM);
      }
      Vec2 _point_4 = rect.getPoint();
      Vec2 _plus_2 = _point_4.operator_plus(new int[] { (rect.width + 2), 2 });
      Rect _calcDirRect_4 = this.calcDirRect(_plus_2, this.CORNER_LEN, AbstractPaint.Direction.LEFT);
      boolean _contains_4 = _calcDirRect_4.contains(mouse);
      if (_contains_4) {
        return new MouseInfo(mouse, Kursor.SIZENE, AbstractPaint.RectChangeType.CORNER_RIGHT_TOP);
      }
      Vec2 _point_5 = rect.getPoint();
      Vec2 _plus_3 = _point_5.operator_plus(new int[] { (rect.width + 2), 2 });
      Rect _calcDirRect_5 = this.calcDirRect(_plus_3, this.CORNER_LEN, AbstractPaint.Direction.DOWN);
      boolean _contains_5 = _calcDirRect_5.contains(mouse);
      if (_contains_5) {
        return new MouseInfo(mouse, Kursor.SIZENE, AbstractPaint.RectChangeType.CORNER_RIGHT_TOP);
      }
      Vec2 _point_6 = rect.getPoint();
      Vec2 _plus_4 = _point_6.operator_plus(new int[] { (rect.width + 2), (rect.height + 2) });
      Rect _calcDirRect_6 = this.calcDirRect(_plus_4, this.CORNER_LEN, AbstractPaint.Direction.UP);
      boolean _contains_6 = _calcDirRect_6.contains(mouse);
      if (_contains_6) {
        return new MouseInfo(mouse, Kursor.SIZESE, AbstractPaint.RectChangeType.CORNER_RIGHT_BOTTOM);
      }
      Vec2 _point_7 = rect.getPoint();
      Vec2 _plus_5 = _point_7.operator_plus(new int[] { (rect.width + 2), (rect.height + 2) });
      Rect _calcDirRect_7 = this.calcDirRect(_plus_5, this.CORNER_LEN, AbstractPaint.Direction.LEFT);
      boolean _contains_7 = _calcDirRect_7.contains(mouse);
      if (_contains_7) {
        return new MouseInfo(mouse, Kursor.SIZESE, AbstractPaint.RectChangeType.CORNER_RIGHT_BOTTOM);
      }
      Vec2 _point_8 = rect.getPoint();
      Vec2 _minus_2 = _point_8.operator_minus(new int[] { 2, 2 });
      Rect _calcDirRect_8 = this.calcDirRect(_minus_2, rect.width, AbstractPaint.Direction.RIGHT);
      boolean _contains_8 = _calcDirRect_8.contains(mouse);
      if (_contains_8) {
        return new MouseInfo(mouse, Kursor.SIZEN, AbstractPaint.RectChangeType.SIDE_TOP);
      }
      Vec2 _point_9 = rect.getPoint();
      Vec2 _minus_3 = _point_9.operator_minus(new int[] { 2, 2 });
      Rect _calcDirRect_9 = this.calcDirRect(_minus_3, rect.height, AbstractPaint.Direction.DOWN);
      boolean _contains_9 = _calcDirRect_9.contains(mouse);
      if (_contains_9) {
        return new MouseInfo(mouse, Kursor.SIZEW, AbstractPaint.RectChangeType.SIDE_LEFT);
      }
      Vec2 _point_10 = rect.getPoint();
      Vec2 _plus_6 = _point_10.operator_plus(new int[] { (rect.width - 2), (-2) });
      Rect _calcDirRect_10 = this.calcDirRect(_plus_6, rect.height, AbstractPaint.Direction.DOWN);
      boolean _contains_10 = _calcDirRect_10.contains(mouse);
      if (_contains_10) {
        return new MouseInfo(mouse, Kursor.SIZEE, AbstractPaint.RectChangeType.SIDE_RIGHT);
      }
      Vec2 _point_11 = rect.getPoint();
      Vec2 _plus_7 = _point_11.operator_plus(new int[] { (-2), (rect.height - 2) });
      Rect _calcDirRect_11 = this.calcDirRect(_plus_7, rect.width, AbstractPaint.Direction.RIGHT);
      boolean _contains_11 = _calcDirRect_11.contains(mouse);
      if (_contains_11) {
        return new MouseInfo(mouse, Kursor.SIZES, AbstractPaint.RectChangeType.SIDE_BOTTOM);
      }
      _xblockexpression = null;
    }
    return ((MouseInfo)_xblockexpression);
  }
  
  public Rect calcDirRect(final Vec2 from, final int len, final AbstractPaint.Direction direction) {
    Rect _switchResult = null;
    if (direction != null) {
      switch (direction) {
        case LEFT:
          Vec2 _minus = from.operator_minus(new int[] { len, this.POL_TOL });
          Size _from = Size.from(len, ((2 * this.POL_TOL) + 1));
          _switchResult = Rect.pointSize(_minus, _from);
          break;
        case UP:
          Vec2 _minus_1 = from.operator_minus(new int[] { this.POL_TOL, len });
          Size _from_1 = Size.from(((2 * this.POL_TOL) + 1), len);
          _switchResult = Rect.pointSize(_minus_1, _from_1);
          break;
        case RIGHT:
          Vec2 _minus_2 = from.operator_minus(new int[] { 0, this.POL_TOL });
          Size _from_2 = Size.from(len, ((2 * this.POL_TOL) + 1));
          _switchResult = Rect.pointSize(_minus_2, _from_2);
          break;
        case DOWN:
          Vec2 _minus_3 = from.operator_minus(new int[] { this.POL_TOL, 0 });
          Size _from_3 = Size.from(((2 * this.POL_TOL) + 1), len);
          _switchResult = Rect.pointSize(_minus_3, _from_3);
          break;
        default:
          break;
      }
    }
    return _switchResult;
  }
}
