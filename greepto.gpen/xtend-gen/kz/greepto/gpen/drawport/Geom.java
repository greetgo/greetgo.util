package kz.greepto.gpen.drawport;

import kz.greepto.gpen.drawport.RectGeom;
import kz.greepto.gpen.drawport.Size;
import kz.greepto.gpen.drawport.StrGeom;
import kz.greepto.gpen.drawport.Vec2;

@SuppressWarnings("all")
public interface Geom {
  public abstract Geom to(final int x, final int y);
  
  public abstract Geom to(final Vec2 point);
  
  public abstract Geom line();
  
  public abstract Geom size(final int width, final int height);
  
  public abstract Geom size(final Size size);
  
  public abstract RectGeom rect();
  
  public abstract StrGeom str(final String str);
}
