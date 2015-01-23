package kz.greepto.gpen.drawport;

import kz.greepto.gpen.drawport.ArcGeom;
import kz.greepto.gpen.drawport.RoundRectGeom;
import kz.greepto.gpen.drawport.Size;

@SuppressWarnings("all")
public interface RectGeom {
  public abstract RectGeom draw();
  
  public abstract RectGeom fill();
  
  public abstract RectGeom clip();
  
  public abstract RectGeom drawOval();
  
  public abstract RectGeom fillOval();
  
  public abstract ArcGeom arc(final int from, final int angle);
  
  public abstract RoundRectGeom round(final Size arcSize);
  
  public abstract RoundRectGeom round(final int arcWidth, final int arcHeight);
  
  public abstract RoundRectGeom round(final int arcWidthAndHeight);
}
