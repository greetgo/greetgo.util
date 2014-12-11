package kz.greepto.gpen.editors.gpen.model

import kz.greepto.gpen.drawport.Vec2

abstract class PointFigure extends IdFigure {

  private Vec2 point

  public def Vec2 getPoint() {
    if(point == null) point = new Vec2(0, 0);
    point
  }

  public def void setPoint(Vec2 point) { this.point = point }

  public def int getX() { getPoint.x }

  public def int getY() { getPoint.y }

  public def void setX(int x) { getPoint.x = x }

  public def void setY(int y) { getPoint.y = y }

  new(String id) {
    super(id)
  }

  new(String id, Vec2 point) {
    this(id)
    this.point = point
  }

  new(PointFigure a) {
    super(a)
    if(a.point != null) point = new Vec2(a.point.x, a.point.y)
  }
}
