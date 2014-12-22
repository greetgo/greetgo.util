package kz.greepto.gpen.editors.gpen.model

import kz.greepto.gpen.drawport.Vec2

abstract class PointFigure extends IdFigure {

  protected int x
  protected int y

  public def Vec2 getPoint() { Vec2.from(x, y) }

  public def void setPoint(Vec2 point) {
    x = point?.x
    y = point?.y
  }

  public def int getX() { x }

  public def int getY() { y }

  public def void setX(int x) { this.x = x }

  public def void setY(int y) { this.y = y }

  new(String id) {
    super(id)
  }

  new(String id, Vec2 point) {
    this(id)
    this.x = point.x
    this.y = point.y
  }

  new(PointFigure a) {
    super(a)
    this.x = a.x
    this.y = a.y
  }
}
