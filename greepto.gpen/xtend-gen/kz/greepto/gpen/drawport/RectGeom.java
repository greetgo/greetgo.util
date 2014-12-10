package kz.greepto.gpen.drawport;

@SuppressWarnings("all")
public interface RectGeom {
  public abstract RectGeom draw();
  
  public abstract RectGeom fill();
  
  public abstract RectGeom clip();
}
