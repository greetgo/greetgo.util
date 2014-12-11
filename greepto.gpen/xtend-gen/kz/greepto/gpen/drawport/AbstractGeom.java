package kz.greepto.gpen.drawport;

import com.google.common.base.Objects;
import java.util.ArrayList;
import java.util.List;
import kz.greepto.gpen.drawport.Geom;
import kz.greepto.gpen.drawport.Size;
import kz.greepto.gpen.drawport.Vec2;

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
}
