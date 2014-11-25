package kz.greepto.gpen.editors.gpen.model;

import kz.greepto.gpen.editors.gpen.model.IdFigure;
import org.eclipse.swt.graphics.Point;

@SuppressWarnings("all")
public abstract class PointFigure extends IdFigure {
  public Point point;
  
  public PointFigure(final String id) {
    super(id);
  }
  
  public PointFigure(final String id, final Point point) {
    this(id);
    this.point = point;
  }
}
