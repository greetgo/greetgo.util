package kz.greepto.gpen.drawport;

import kz.greepto.gpen.drawport.FontDef;
import kz.greepto.gpen.drawport.Geom;
import kz.greepto.gpen.drawport.Rect;
import kz.greepto.gpen.drawport.RectGeom;
import kz.greepto.gpen.drawport.StrGeom;
import kz.greepto.gpen.drawport.Style;
import kz.greepto.gpen.drawport.Vec2;

@SuppressWarnings("all")
public interface DrawPort {
  public abstract void dispose();
  
  public abstract DrawPort copy();
  
  public abstract FontDef font();
  
  public abstract void setFont(final FontDef font);
  
  public abstract Geom from(final int x, final int y);
  
  public abstract Geom from(final Vec2 from);
  
  public abstract RectGeom from(final Rect rect);
  
  public abstract Style style();
  
  public abstract StrGeom str(final String str);
}
