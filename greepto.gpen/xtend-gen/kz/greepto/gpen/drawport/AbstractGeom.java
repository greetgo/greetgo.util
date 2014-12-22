package kz.greepto.gpen.drawport;

import com.google.common.base.Objects;
import java.util.ArrayList;
import java.util.List;
import kz.greepto.gpen.drawport.Geom;
import kz.greepto.gpen.drawport.Size;
import kz.greepto.gpen.drawport.Vec2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public abstract class AbstractGeom implements Geom {
  protected Vec2 from;
  
  protected Size size;
  
  protected final List<Vec2> toList = new ArrayList<Vec2>();
  
  public Geom to(final int x, final int y) {
    Vec2 _vec2 = new Vec2(x, y);
    return this.to(_vec2);
  }
  
  public Geom to(final Vec2 point) {
    AbstractGeom _xblockexpression = null;
    {
      boolean _equals = Objects.equal(point, null);
      if (_equals) {
        throw new NullPointerException();
      }
      this.toList.add(point);
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public Geom line() {
    AbstractGeom _xblockexpression = null;
    {
      boolean _notEquals = (!Objects.equal(this.size, null));
      if (_notEquals) {
        Vec2 _plus = this.from.operator_plus(this.size);
        this.drawLine(this.from, _plus);
        this.move();
        return this;
      }
      Vec2 _get = this.toList.get(0);
      this.drawLine(this.from, _get);
      {
        int i = 1;
        int C = this.toList.size();
        boolean _while = (i < C);
        while (_while) {
          Vec2 _get_1 = this.toList.get((i - 1));
          Vec2 _get_2 = this.toList.get(i);
          this.drawLine(_get_1, _get_2);
          i++;
          _while = (i < C);
        }
      }
      this.move();
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public Geom move() {
    AbstractGeom _xblockexpression = null;
    {
      boolean _notEquals = (!Objects.equal(this.size, null));
      if (_notEquals) {
        Vec2 _plus = this.from.operator_plus(this.size);
        this.from = _plus;
        this.size = null;
        this.toList.clear();
        return this;
      }
      int _size = this.toList.size();
      int _minus = (_size - 1);
      Vec2 _get = this.toList.get(_minus);
      this.from = _get;
      this.toList.clear();
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public abstract void drawLine(final Vec2 from, final Vec2 to);
  
  public Geom size(final int width, final int height) {
    Size _from = Size.from(width, height);
    return this.size(_from);
  }
  
  public Geom size(final Size size) {
    AbstractGeom _xblockexpression = null;
    {
      this.size = size;
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public Geom shift(final Vec2 offset) {
    AbstractGeom _xblockexpression = null;
    {
      int _size = this.toList.size();
      boolean _tripleEquals = (_size == 0);
      if (_tripleEquals) {
        Vec2 _plus = this.from.operator_plus(offset);
        this.toList.add(_plus);
      } else {
        Vec2 _last = IterableExtensions.<Vec2>last(this.toList);
        Vec2 _plus_1 = _last.operator_plus(offset);
        this.toList.add(_plus_1);
      }
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public Geom shift(final int dx, final int dy) {
    Vec2 _from = Vec2.from(dx, dy);
    return this.shift(_from);
  }
  
  public Vec2 last() {
    boolean _and = false;
    boolean _equals = Objects.equal(this.size, null);
    if (!_equals) {
      _and = false;
    } else {
      int _size = this.toList.size();
      boolean _equals_1 = (_size == 0);
      _and = _equals_1;
    }
    if (_and) {
      return this.from;
    }
    boolean _notEquals = (!Objects.equal(this.size, null));
    if (_notEquals) {
      return this.from.operator_plus(this.size);
    }
    return IterableExtensions.<Vec2>last(this.toList);
  }
  
  public double dashLine(final double offset, final double skvaj, final double step) {
    if ((step < 0.1)) {
      throw new IllegalArgumentException("step < 0.1");
    }
    double px = ((double) this.from.x);
    double py = ((double) this.from.y);
    double TOx = 0;
    double TOy = 0;
    boolean _tripleEquals = (this.size == null);
    if (_tripleEquals) {
      Vec2 _get = this.toList.get(0);
      TOx = _get.x;
      Vec2 _get_1 = this.toList.get(0);
      TOy = _get_1.y;
    } else {
      Vec2 end = this.from.operator_plus(this.size);
      TOx = end.x;
      TOy = end.y;
    }
    this.move();
    double nx = (TOx - px);
    double ny = (TOy - py);
    double length = Math.sqrt(((nx * nx) + (ny * ny)));
    double _nx = nx;
    nx = (_nx / length);
    double _ny = ny;
    ny = (_ny / length);
    double lineLen = (step * skvaj);
    double left = length;
    double ofs = offset;
    while ((ofs < 0)) {
      double _ofs = ofs;
      ofs = (_ofs + step);
    }
    while (true) {
      {
        while ((ofs >= step)) {
          double _ofs = ofs;
          ofs = (_ofs - step);
        }
        if ((left <= 0)) {
          return ofs;
        }
        if ((lineLen > ofs)) {
          double drawLen = (lineLen - ofs);
          if ((drawLen > left)) {
            drawLen = left;
          }
          double tox = (px + (drawLen * nx));
          double toy = (py + (drawLen * ny));
          this.drawLineWith(px, py, tox, toy);
          px = tox;
          py = toy;
          double _left = left;
          left = (_left - drawLen);
          double _ofs = ofs;
          ofs = (_ofs + drawLen);
        }
        if ((left <= 0)) {
          return ofs;
        }
        {
          double moveLen = (step - ofs);
          if ((moveLen > left)) {
            moveLen = left;
          }
          double _px = px;
          px = (_px + (moveLen * nx));
          double _py = py;
          py = (_py + (moveLen * ny));
          double _left_1 = left;
          left = (_left_1 - moveLen);
          double _ofs_1 = ofs;
          ofs = (_ofs_1 + moveLen);
        }
      }
    }
  }
  
  public void drawLineWith(final double x1, final double y1, final double x2, final double y2) {
    Vec2 from = Vec2.from(((int) x1), ((int) y1));
    Vec2 to = Vec2.from(((int) x2), ((int) y2));
    this.drawLine(from, to);
  }
}
