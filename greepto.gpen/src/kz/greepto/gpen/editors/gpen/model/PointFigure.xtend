package kz.greepto.gpen.editors.gpen.model

import org.eclipse.swt.graphics.Point

abstract class PointFigure extends IdFigure {

  private Point point

  public def Point getPoint() { if (point == null) point = new Point(0, 0); point }
  public def Point setPoint(Point point) {this.point = point}


  public def int getX() { getPoint.x }

  public def int getY() { getPoint.y }

  public def void setX(int x) { getPoint.x = x }

  public def void setY(int y) { getPoint.y = y }

  new(String id) {
    super(id)
  }

  new(String id, Point point) {
    this(id)
    this.point = point
  }

  new(PointFigure a) {
    super(a)
    if (a.point != null) point = new Point(a.point.x, a.point.y)
  }
}
