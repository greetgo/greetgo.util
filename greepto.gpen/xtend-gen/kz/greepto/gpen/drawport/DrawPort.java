package kz.greepto.gpen.drawport;

import kz.greepto.gpen.drawport.FontDef;
import kz.greepto.gpen.drawport.Geom;
import kz.greepto.gpen.drawport.Vec2;

@SuppressWarnings("all")
public interface DrawPort {
  public abstract void dispose();
  
  public abstract DrawPort copy();
  
  public abstract FontDef font();
  
  public abstract Geom from(final int x, final int y);
  
  public abstract Geom from(final Vec2 point);
}
