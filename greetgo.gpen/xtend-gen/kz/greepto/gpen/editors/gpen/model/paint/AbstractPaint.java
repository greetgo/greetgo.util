package kz.greepto.gpen.editors.gpen.model.paint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import kz.greepto.gpen.drawport.DrawPort;
import kz.greepto.gpen.drawport.Geom;
import kz.greepto.gpen.drawport.Kolor;
import kz.greepto.gpen.drawport.Kursor;
import kz.greepto.gpen.drawport.Rect;
import kz.greepto.gpen.drawport.RectGeom;
import kz.greepto.gpen.drawport.Size;
import kz.greepto.gpen.drawport.Style;
import kz.greepto.gpen.drawport.Vec2;
import kz.greepto.gpen.editors.gpen.action.Oper;
import kz.greepto.gpen.editors.gpen.action.OperGroup;
import kz.greepto.gpen.editors.gpen.action.OperModify;
import kz.greepto.gpen.editors.gpen.model.IdFigure;
import kz.greepto.gpen.editors.gpen.model.PointFigure;
import kz.greepto.gpen.editors.gpen.model.RectFigure;
import kz.greepto.gpen.editors.gpen.model.paint.PaintFigure;
import kz.greepto.gpen.editors.gpen.model.paint.PaintResult;
import kz.greepto.gpen.editors.gpen.model.paint.SelChecker;
import kz.greepto.gpen.editors.gpen.prop.ValueSetter;
import kz.greepto.gpen.editors.gpen.style.StyleCalc;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

@SuppressWarnings("all")
public abstract class AbstractPaint implements PaintFigure {
  public enum Direction {
    LEFT,
    
    UP,
    
    RIGHT,
    
    DOWN;
  }
  
  protected DrawPort dp;
  
  protected StyleCalc styleCalc;
  
  protected Kolor dragingKolor = Kolor.GRAY;
  
  protected SelChecker selChecker;
  
  public AbstractPaint() {
  }
  
  public void setEnvironment(final DrawPort dp, final StyleCalc styleCalc, final SelChecker selChecker) {
    this.dp = dp;
    this.styleCalc = styleCalc;
    this.selChecker = selChecker;
  }
  
  protected boolean isSel(final IdFigure figure) {
    boolean _isSelected = false;
    if (this.selChecker!=null) {
      _isSelected=this.selChecker.isSelected(figure);
    }
    return _isSelected;
  }
  
  public Rect getPlace() {
    PaintResult _work = this.work(null);
    return _work.getPlace();
  }
  
  public PaintResult paint(final Vec2 mouse) {
    return this.work(mouse);
  }
  
  public abstract PaintResult work(final Vec2 mouse);
  
  protected void drawAroundFocus(final Rect rect) {
    final int step = 20;
    final int period = 600;
    double ofs = ((-(((double) (System.currentTimeMillis() % period)) / period)) * step);
    double skvaj = 0.5;
    int d = 2;
    Vec2 _rightBottom = rect.getRightBottom();
    Vec2 _plus = _rightBottom.operator_plus(new int[] { d, d });
    Geom x = this.dp.from(_plus);
    Vec2 _leftBottom = rect.getLeftBottom();
    Vec2 _plus_1 = _leftBottom.operator_plus(new int[] { (-d), d });
    Geom _to = x.to(_plus_1);
    double _dashLine = _to.dashLine(ofs, skvaj, step);
    ofs = _dashLine;
    Vec2 _leftTop = rect.getLeftTop();
    Vec2 _plus_2 = _leftTop.operator_plus(new int[] { (-d), (-d) });
    Geom _to_1 = x.to(_plus_2);
    double _dashLine_1 = _to_1.dashLine(ofs, skvaj, step);
    ofs = _dashLine_1;
    Vec2 _rightTop = rect.getRightTop();
    Vec2 _plus_3 = _rightTop.operator_plus(new int[] { d, (-d) });
    Geom _to_2 = x.to(_plus_3);
    double _dashLine_2 = _to_2.dashLine(ofs, skvaj, step);
    ofs = _dashLine_2;
    Vec2 _rightBottom_1 = rect.getRightBottom();
    Vec2 _plus_4 = _rightBottom_1.operator_plus(new int[] { d, d });
    Geom _to_3 = x.to(_plus_4);
    double _dashLine_3 = _to_3.dashLine(ofs, skvaj, step);
    ofs = _dashLine_3;
  }
  
  protected PaintResult simpleRect(final Rect rect) {
    return new PaintResult() {
      public Oper createOper(final Vec2 mouseMovedTo) {
        throw new UnsupportedOperationException("No oper");
      }
      
      public Kursor getKursor() {
        return Kursor.ARROW;
      }
      
      public Rect getPlace() {
        return rect;
      }
      
      public boolean isHasOper() {
        return false;
      }
      
      public String toString() {
        return "simpleRect";
      }
      
      public void paintDrag(final DrawPort dp, final Vec2 mouseMovedTo) {
        throw new UnsupportedOperationException("No paintDrag");
      }
    };
  }
  
  protected PaintResult modiBounds(final Vec2 mouseDownedAt, final Rect rect, final IdFigure figure) {
    boolean _contains = rect.contains(mouseDownedAt);
    boolean _not = (!_contains);
    if (_not) {
      return this.simpleRect(rect);
    }
    if (figure.freeze) {
      return this.simpleRect(rect);
    }
    if ((figure instanceof RectFigure)) {
      PaintResult res = this.modiBoundsRect(mouseDownedAt, rect, ((RectFigure)figure));
      boolean _tripleNotEquals = (res != null);
      if (_tripleNotEquals) {
        return res;
      }
    }
    if ((figure instanceof PointFigure)) {
      return this.modiPosition(mouseDownedAt, rect, ((PointFigure)figure));
    }
    return this.simpleRect(rect);
  }
  
  protected PaintResult modiPosition(final Vec2 mouseDownedAt, final Rect rect, final PointFigure figure) {
    abstract class __AbstractPaint_2 implements PaintResult {
      public abstract Oper createDxOper(final int dx);
      
      public abstract Oper createDyOper(final int dy);
    }
    
    __AbstractPaint_2 _xblockexpression = null;
    {
      if (figure.freeze) {
        return this.simpleRect(rect);
      }
      _xblockexpression = new __AbstractPaint_2() {
        public Kursor getKursor() {
          return Kursor.ARROW;
        }
        
        public Rect getPlace() {
          return rect;
        }
        
        public boolean isHasOper() {
          return true;
        }
        
        public Oper createOper(final Vec2 mouseMovedTo) {
          Vec2 d = mouseMovedTo.operator_minus(mouseDownedAt);
          Oper dxOper = this.createDxOper(d.x);
          Oper dyOper = this.createDyOper(d.y);
          boolean _tripleEquals = (dxOper == null);
          if (_tripleEquals) {
            return dyOper;
          }
          boolean _tripleEquals_1 = (dyOper == null);
          if (_tripleEquals_1) {
            return dxOper;
          }
          return new OperGroup(Collections.<Oper>unmodifiableList(CollectionLiterals.<Oper>newArrayList(dxOper, dyOper)), ((("Move dx, dy = " + Integer.valueOf(d.x)) + ", ") + Integer.valueOf(d.y)));
        }
        
        public Oper createDxOper(final int dx) {
          if ((dx == 0)) {
            return null;
          }
          return new OperModify(AbstractPaint.SETTER_X, Integer.valueOf((rect.x + dx)), figure.id, ("Move dx = " + Integer.valueOf(dx)));
        }
        
        public Oper createDyOper(final int dy) {
          if ((dy == 0)) {
            return null;
          }
          return new OperModify(AbstractPaint.SETTER_Y, Integer.valueOf((rect.y + dy)), figure.id, ("Move dy = " + Integer.valueOf(dy)));
        }
        
        public String toString() {
          return "modiPosition";
        }
        
        public void paintDrag(final DrawPort dp, final Vec2 mouseMovedTo) {
          Rect r = rect.copy();
          Vec2 _point = r.getPoint();
          Vec2 _minus = mouseMovedTo.operator_minus(mouseDownedAt);
          Vec2 _plus = _point.operator_plus(_minus);
          r.setPoint(_plus);
          Style _style = dp.style();
          _style.setForeground(AbstractPaint.this.dragingKolor);
          RectGeom _from = dp.from(r);
          _from.draw();
        }
      };
    }
    return _xblockexpression;
  }
  
  private final static ValueSetter SETTER_X = new ValueSetter() {
    public Class<?> getType() {
      return Integer.TYPE;
    }
    
    public String getName() {
      return "x";
    }
    
    public Object setValue(final Object object, final Object value) {
      PointFigure fig = ((PointFigure) object);
      int ret = fig.getX();
      fig.setX((((Integer) value)).intValue());
      return Integer.valueOf(ret);
    }
  };
  
  private final static ValueSetter SETTER_Y = new ValueSetter() {
    public Class<?> getType() {
      return Integer.TYPE;
    }
    
    public String getName() {
      return "y";
    }
    
    public Object setValue(final Object object, final Object value) {
      PointFigure fig = ((PointFigure) object);
      int ret = fig.getY();
      fig.setY((((Integer) value)).intValue());
      return Integer.valueOf(ret);
    }
  };
  
  private final static ValueSetter SETTER_WIDTH = new ValueSetter() {
    public Class<?> getType() {
      return Integer.TYPE;
    }
    
    public String getName() {
      return "width";
    }
    
    public Object setValue(final Object object, final Object value) {
      RectFigure fig = ((RectFigure) object);
      int ret = fig.getWidth();
      fig.setWidth((((Integer) value)).intValue());
      return Integer.valueOf(ret);
    }
  };
  
  private final static ValueSetter SETTER_HEIGHT = new ValueSetter() {
    public Class<?> getType() {
      return Integer.TYPE;
    }
    
    public String getName() {
      return "height";
    }
    
    public Object setValue(final Object object, final Object value) {
      RectFigure fig = ((RectFigure) object);
      int ret = fig.getHeight();
      fig.setHeight((((Integer) value)).intValue());
      return Integer.valueOf(ret);
    }
  };
  
  private PaintResult modiBoundsRect(final Vec2 mouseDownedAt, final Rect rect, final RectFigure figure) {
    Object _xblockexpression = null;
    {
      Vec2 _point = rect.getPoint();
      Vec2 _minus = _point.operator_minus(new int[] { 2, 2 });
      Rect _calcDirRect = this.calcDirRect(_minus, this.CORNER_LEN, AbstractPaint.Direction.DOWN);
      boolean _contains = _calcDirRect.contains(mouseDownedAt);
      if (_contains) {
        return this.newCornerLeftTop(mouseDownedAt, rect, figure);
      }
      Vec2 _point_1 = rect.getPoint();
      Vec2 _minus_1 = _point_1.operator_minus(new int[] { 2, 2 });
      Rect _calcDirRect_1 = this.calcDirRect(_minus_1, this.CORNER_LEN, AbstractPaint.Direction.RIGHT);
      boolean _contains_1 = _calcDirRect_1.contains(mouseDownedAt);
      if (_contains_1) {
        return this.newCornerLeftTop(mouseDownedAt, rect, figure);
      }
      Vec2 _point_2 = rect.getPoint();
      Vec2 _plus = _point_2.operator_plus(new int[] { (-2), (rect.height + 2) });
      Rect _calcDirRect_2 = this.calcDirRect(_plus, this.CORNER_LEN, AbstractPaint.Direction.UP);
      boolean _contains_2 = _calcDirRect_2.contains(mouseDownedAt);
      if (_contains_2) {
        return this.newCornerLeftBottom(mouseDownedAt, rect, figure);
      }
      Vec2 _point_3 = rect.getPoint();
      Vec2 _plus_1 = _point_3.operator_plus(new int[] { (-2), (rect.height + 2) });
      Rect _calcDirRect_3 = this.calcDirRect(_plus_1, this.CORNER_LEN, AbstractPaint.Direction.RIGHT);
      boolean _contains_3 = _calcDirRect_3.contains(mouseDownedAt);
      if (_contains_3) {
        return this.newCornerLeftBottom(mouseDownedAt, rect, figure);
      }
      Vec2 _point_4 = rect.getPoint();
      Vec2 _plus_2 = _point_4.operator_plus(new int[] { (rect.width + 2), 2 });
      Rect _calcDirRect_4 = this.calcDirRect(_plus_2, this.CORNER_LEN, AbstractPaint.Direction.LEFT);
      boolean _contains_4 = _calcDirRect_4.contains(mouseDownedAt);
      if (_contains_4) {
        return this.newCornerRightTop(mouseDownedAt, rect, figure);
      }
      Vec2 _point_5 = rect.getPoint();
      Vec2 _plus_3 = _point_5.operator_plus(new int[] { (rect.width + 2), 2 });
      Rect _calcDirRect_5 = this.calcDirRect(_plus_3, this.CORNER_LEN, AbstractPaint.Direction.DOWN);
      boolean _contains_5 = _calcDirRect_5.contains(mouseDownedAt);
      if (_contains_5) {
        return this.newCornerRightTop(mouseDownedAt, rect, figure);
      }
      Vec2 _point_6 = rect.getPoint();
      Vec2 _plus_4 = _point_6.operator_plus(new int[] { (rect.width + 2), (rect.height + 2) });
      Rect _calcDirRect_6 = this.calcDirRect(_plus_4, this.CORNER_LEN, AbstractPaint.Direction.UP);
      boolean _contains_6 = _calcDirRect_6.contains(mouseDownedAt);
      if (_contains_6) {
        return this.newCornerRightBottom(mouseDownedAt, rect, figure);
      }
      Vec2 _point_7 = rect.getPoint();
      Vec2 _plus_5 = _point_7.operator_plus(new int[] { (rect.width + 2), (rect.height + 2) });
      Rect _calcDirRect_7 = this.calcDirRect(_plus_5, this.CORNER_LEN, AbstractPaint.Direction.LEFT);
      boolean _contains_7 = _calcDirRect_7.contains(mouseDownedAt);
      if (_contains_7) {
        return this.newCornerRightBottom(mouseDownedAt, rect, figure);
      }
      Vec2 _point_8 = rect.getPoint();
      Vec2 _minus_2 = _point_8.operator_minus(new int[] { 2, 2 });
      Rect _calcDirRect_8 = this.calcDirRect(_minus_2, rect.width, AbstractPaint.Direction.RIGHT);
      boolean _contains_8 = _calcDirRect_8.contains(mouseDownedAt);
      if (_contains_8) {
        return this.newSideTop(mouseDownedAt, rect, figure);
      }
      Vec2 _point_9 = rect.getPoint();
      Vec2 _minus_3 = _point_9.operator_minus(new int[] { 2, 2 });
      Rect _calcDirRect_9 = this.calcDirRect(_minus_3, rect.height, AbstractPaint.Direction.DOWN);
      boolean _contains_9 = _calcDirRect_9.contains(mouseDownedAt);
      if (_contains_9) {
        return this.newSideLeft(mouseDownedAt, rect, figure);
      }
      Vec2 _point_10 = rect.getPoint();
      Vec2 _plus_6 = _point_10.operator_plus(new int[] { (rect.width - 2), (-2) });
      Rect _calcDirRect_10 = this.calcDirRect(_plus_6, rect.height, AbstractPaint.Direction.DOWN);
      boolean _contains_10 = _calcDirRect_10.contains(mouseDownedAt);
      if (_contains_10) {
        return this.newSideRight(mouseDownedAt, rect, figure);
      }
      Vec2 _point_11 = rect.getPoint();
      Vec2 _plus_7 = _point_11.operator_plus(new int[] { (-2), (rect.height - 2) });
      Rect _calcDirRect_11 = this.calcDirRect(_plus_7, rect.width, AbstractPaint.Direction.RIGHT);
      boolean _contains_11 = _calcDirRect_11.contains(mouseDownedAt);
      if (_contains_11) {
        return this.newSideBottom(mouseDownedAt, rect, figure);
      }
      _xblockexpression = null;
    }
    return ((PaintResult)_xblockexpression);
  }
  
  private static Oper group(final Collection<Oper> opers, final String name) {
    OperGroup _xblockexpression = null;
    {
      int _size = opers.size();
      boolean _tripleEquals = (_size == 0);
      if (_tripleEquals) {
        return null;
      }
      int _size_1 = opers.size();
      boolean _tripleEquals_1 = (_size_1 == 1);
      if (_tripleEquals_1) {
        Iterator<Oper> _iterator = opers.iterator();
        return _iterator.next();
      }
      _xblockexpression = new OperGroup(opers, name);
    }
    return _xblockexpression;
  }
  
  private PaintResult newCornerLeftTop(final Vec2 mouseDownedAt, final Rect rect, final RectFigure figure) {
    return new PaintResult() {
      public Kursor getKursor() {
        return Kursor.SIZENW;
      }
      
      public Rect getPlace() {
        return rect;
      }
      
      public boolean isHasOper() {
        return true;
      }
      
      public String toString() {
        return "newCornerLeftTop";
      }
      
      public Oper createOper(final Vec2 mouseMovedTo) {
        Vec2 d = mouseMovedTo.operator_minus(mouseDownedAt);
        ArrayList<Oper> opers = new ArrayList<Oper>();
        if ((d.x != 0)) {
          OperModify _operModify = new OperModify(AbstractPaint.SETTER_X, Integer.valueOf((rect.x + d.x)), figure.id);
          opers.add(_operModify);
          OperModify _operModify_1 = new OperModify(AbstractPaint.SETTER_WIDTH, Integer.valueOf((rect.width - d.x)), figure.id);
          opers.add(_operModify_1);
        }
        if ((d.y != 0)) {
          OperModify _operModify_2 = new OperModify(AbstractPaint.SETTER_Y, Integer.valueOf((rect.y + d.y)), figure.id);
          opers.add(_operModify_2);
          OperModify _operModify_3 = new OperModify(AbstractPaint.SETTER_HEIGHT, Integer.valueOf((rect.height - d.y)), figure.id);
          opers.add(_operModify_3);
        }
        return AbstractPaint.group(opers, "Corner Left Top");
      }
      
      public void paintDrag(final DrawPort dp, final Vec2 mouseMovedTo) {
        Vec2 d = mouseMovedTo.operator_minus(mouseDownedAt);
        Style _style = dp.style();
        _style.setForeground(AbstractPaint.this.dragingKolor);
        Vec2 _point = rect.getPoint();
        Vec2 _plus = _point.operator_plus(d);
        Geom _from = dp.from(_plus);
        Size _size = rect.getSize();
        Size _minus = _size.operator_minus(d);
        Geom _size_1 = _from.size(_minus);
        RectGeom _rect = _size_1.rect();
        _rect.draw();
      }
    };
  }
  
  private PaintResult newCornerLeftBottom(final Vec2 mouseDownedAt, final Rect rect, final RectFigure figure) {
    return new PaintResult() {
      public Kursor getKursor() {
        return Kursor.SIZESW;
      }
      
      public Rect getPlace() {
        return rect;
      }
      
      public boolean isHasOper() {
        return true;
      }
      
      public String toString() {
        return "newCornerLeftBottom";
      }
      
      public Oper createOper(final Vec2 mouseMovedTo) {
        Vec2 d = mouseMovedTo.operator_minus(mouseDownedAt);
        ArrayList<Oper> opers = new ArrayList<Oper>();
        if ((d.x != 0)) {
          OperModify _operModify = new OperModify(AbstractPaint.SETTER_X, Integer.valueOf((rect.x + d.x)), figure.id);
          opers.add(_operModify);
          OperModify _operModify_1 = new OperModify(AbstractPaint.SETTER_WIDTH, Integer.valueOf((rect.width - d.x)), figure.id);
          opers.add(_operModify_1);
        }
        if ((d.y != 0)) {
          OperModify _operModify_2 = new OperModify(AbstractPaint.SETTER_HEIGHT, Integer.valueOf((rect.height + d.y)), figure.id);
          opers.add(_operModify_2);
        }
        return AbstractPaint.group(opers, "Corner Left Bottom");
      }
      
      public void paintDrag(final DrawPort dp, final Vec2 mouseMovedTo) {
        Style _style = dp.style();
        _style.setForeground(AbstractPaint.this.dragingKolor);
        Vec2 d = mouseMovedTo.operator_minus(mouseDownedAt);
        Geom _from = dp.from((rect.x + d.x), rect.y);
        Geom _size = _from.size((rect.width - d.x), (rect.height + d.y));
        RectGeom _rect = _size.rect();
        _rect.draw();
      }
    };
  }
  
  private PaintResult newCornerRightTop(final Vec2 mouseDownedAt, final Rect rect, final RectFigure figure) {
    return new PaintResult() {
      public Kursor getKursor() {
        return Kursor.SIZENE;
      }
      
      public Rect getPlace() {
        return rect;
      }
      
      public boolean isHasOper() {
        return true;
      }
      
      public String toString() {
        return "newCornerRightTop";
      }
      
      public Oper createOper(final Vec2 mouseMovedTo) {
        Vec2 d = mouseMovedTo.operator_minus(mouseDownedAt);
        ArrayList<Oper> opers = new ArrayList<Oper>();
        if ((d.x != 0)) {
          OperModify _operModify = new OperModify(AbstractPaint.SETTER_WIDTH, Integer.valueOf((rect.width + d.x)), figure.id);
          opers.add(_operModify);
        }
        if ((d.y != 0)) {
          OperModify _operModify_1 = new OperModify(AbstractPaint.SETTER_Y, Integer.valueOf((rect.y + d.y)), figure.id);
          opers.add(_operModify_1);
          OperModify _operModify_2 = new OperModify(AbstractPaint.SETTER_HEIGHT, Integer.valueOf((rect.height - d.y)), figure.id);
          opers.add(_operModify_2);
        }
        return AbstractPaint.group(opers, "Corner Right Top");
      }
      
      public void paintDrag(final DrawPort dp, final Vec2 mouseMovedTo) {
        Style _style = dp.style();
        _style.setForeground(AbstractPaint.this.dragingKolor);
        Vec2 d = mouseMovedTo.operator_minus(mouseDownedAt);
        Geom _from = dp.from(rect.x, (rect.y + d.y));
        Geom _size = _from.size((rect.width + d.x), (rect.height - d.y));
        RectGeom _rect = _size.rect();
        _rect.draw();
      }
    };
  }
  
  private PaintResult newCornerRightBottom(final Vec2 mouseDownedAt, final Rect rect, final RectFigure figure) {
    return new PaintResult() {
      public Kursor getKursor() {
        return Kursor.SIZESE;
      }
      
      public Rect getPlace() {
        return rect;
      }
      
      public boolean isHasOper() {
        return true;
      }
      
      public String toString() {
        return "newCornerRightBottom";
      }
      
      public Oper createOper(final Vec2 mouseMovedTo) {
        Vec2 d = mouseMovedTo.operator_minus(mouseDownedAt);
        ArrayList<Oper> opers = new ArrayList<Oper>();
        if ((d.x != 0)) {
          OperModify _operModify = new OperModify(AbstractPaint.SETTER_WIDTH, Integer.valueOf((rect.width + d.x)), figure.id);
          opers.add(_operModify);
        }
        if ((d.y != 0)) {
          OperModify _operModify_1 = new OperModify(AbstractPaint.SETTER_HEIGHT, Integer.valueOf((rect.height + d.y)), figure.id);
          opers.add(_operModify_1);
        }
        return AbstractPaint.group(opers, "Corner Right Bottom");
      }
      
      public void paintDrag(final DrawPort dp, final Vec2 mouseMovedTo) {
        Style _style = dp.style();
        _style.setForeground(AbstractPaint.this.dragingKolor);
        Vec2 d = mouseMovedTo.operator_minus(mouseDownedAt);
        Vec2 _point = rect.getPoint();
        Geom _from = dp.from(_point);
        Size _size = rect.getSize();
        Size _plus = _size.operator_plus(d);
        Geom _size_1 = _from.size(_plus);
        RectGeom _rect = _size_1.rect();
        _rect.draw();
      }
    };
  }
  
  private PaintResult newSideTop(final Vec2 mouseDownedAt, final Rect rect, final RectFigure figure) {
    return new PaintResult() {
      public Kursor getKursor() {
        return Kursor.SIZEN;
      }
      
      public Rect getPlace() {
        return rect;
      }
      
      public boolean isHasOper() {
        return true;
      }
      
      public String toString() {
        return "newSideTop";
      }
      
      public Oper createOper(final Vec2 mouseMovedTo) {
        int dy = (mouseMovedTo.y - mouseDownedAt.y);
        if ((dy == 0)) {
          return null;
        }
        ArrayList<Oper> opers = new ArrayList<Oper>();
        OperModify _operModify = new OperModify(AbstractPaint.SETTER_Y, Integer.valueOf((rect.y + dy)), figure.id);
        opers.add(_operModify);
        OperModify _operModify_1 = new OperModify(AbstractPaint.SETTER_HEIGHT, Integer.valueOf((rect.height - dy)), figure.id);
        opers.add(_operModify_1);
        return AbstractPaint.group(opers, "Side Top");
      }
      
      public void paintDrag(final DrawPort dp, final Vec2 mouseMovedTo) {
        Style _style = dp.style();
        _style.setForeground(AbstractPaint.this.dragingKolor);
        Vec2 d = mouseMovedTo.operator_minus(mouseDownedAt);
        Geom _from = dp.from(rect.x, (rect.y + d.y));
        Geom _size = _from.size(rect.width, (rect.height - d.y));
        RectGeom _rect = _size.rect();
        _rect.draw();
      }
    };
  }
  
  private PaintResult newSideLeft(final Vec2 mouseDownedAt, final Rect rect, final RectFigure figure) {
    return new PaintResult() {
      public Kursor getKursor() {
        return Kursor.SIZEW;
      }
      
      public Rect getPlace() {
        return rect;
      }
      
      public boolean isHasOper() {
        return true;
      }
      
      public String toString() {
        return "newSideLeft";
      }
      
      public Oper createOper(final Vec2 mouseMovedTo) {
        int dx = (mouseMovedTo.x - mouseDownedAt.x);
        if ((dx == 0)) {
          return null;
        }
        ArrayList<Oper> opers = new ArrayList<Oper>();
        OperModify _operModify = new OperModify(AbstractPaint.SETTER_X, Integer.valueOf((rect.x + dx)), figure.id);
        opers.add(_operModify);
        OperModify _operModify_1 = new OperModify(AbstractPaint.SETTER_WIDTH, Integer.valueOf((rect.width - dx)), figure.id);
        opers.add(_operModify_1);
        return AbstractPaint.group(opers, "Side Left");
      }
      
      public void paintDrag(final DrawPort dp, final Vec2 mouseMovedTo) {
        Style _style = dp.style();
        _style.setForeground(AbstractPaint.this.dragingKolor);
        Vec2 d = mouseMovedTo.operator_minus(mouseDownedAt);
        Geom _from = dp.from((rect.x + d.x), rect.y);
        Geom _size = _from.size((rect.width - d.x), rect.height);
        RectGeom _rect = _size.rect();
        _rect.draw();
      }
    };
  }
  
  private PaintResult newSideRight(final Vec2 mouseDownedAt, final Rect rect, final RectFigure figure) {
    return new PaintResult() {
      public Kursor getKursor() {
        return Kursor.SIZEE;
      }
      
      public Rect getPlace() {
        return rect;
      }
      
      public boolean isHasOper() {
        return true;
      }
      
      public String toString() {
        return "newSideRight";
      }
      
      public Oper createOper(final Vec2 mouseMovedTo) {
        int dx = (mouseMovedTo.x - mouseDownedAt.x);
        if ((dx == 0)) {
          return null;
        }
        return new OperModify(AbstractPaint.SETTER_WIDTH, Integer.valueOf((rect.width + dx)), figure.id);
      }
      
      public void paintDrag(final DrawPort dp, final Vec2 mouseMovedTo) {
        Style _style = dp.style();
        _style.setForeground(AbstractPaint.this.dragingKolor);
        Vec2 d = mouseMovedTo.operator_minus(mouseDownedAt);
        Vec2 _point = rect.getPoint();
        Geom _from = dp.from(_point);
        Geom _size = _from.size((rect.width + d.x), rect.height);
        RectGeom _rect = _size.rect();
        _rect.draw();
      }
    };
  }
  
  private PaintResult newSideBottom(final Vec2 mouseDownedAt, final Rect rect, final RectFigure figure) {
    return new PaintResult() {
      public Kursor getKursor() {
        return Kursor.SIZES;
      }
      
      public Rect getPlace() {
        return rect;
      }
      
      public boolean isHasOper() {
        return true;
      }
      
      public String toString() {
        return "newSideBottom";
      }
      
      public Oper createOper(final Vec2 mouseMovedTo) {
        int dy = (mouseMovedTo.y - mouseDownedAt.y);
        if ((dy == 0)) {
          return null;
        }
        return new OperModify(AbstractPaint.SETTER_HEIGHT, Integer.valueOf((rect.height + dy)), figure.id);
      }
      
      public void paintDrag(final DrawPort dp, final Vec2 mouseMovedTo) {
        Style _style = dp.style();
        _style.setForeground(AbstractPaint.this.dragingKolor);
        Vec2 d = mouseMovedTo.operator_minus(mouseDownedAt);
        Vec2 _point = rect.getPoint();
        Geom _from = dp.from(_point);
        Geom _size = _from.size(rect.width, (rect.height + d.y));
        RectGeom _rect = _size.rect();
        _rect.draw();
      }
    };
  }
  
  private final int POL_TOL = 4;
  
  private final int CORNER_LEN = 15;
  
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
