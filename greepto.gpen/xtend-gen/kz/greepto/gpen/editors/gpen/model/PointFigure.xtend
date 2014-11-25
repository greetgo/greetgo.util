package kz.greepto.gpen.editors.gpen.model

import org.eclipse.swt.graphics.Point

abstract class PointFigure extends IdFigure {

  public Point point

  new(String id) {
    super(id)
  }

  new(String id, Point point) {
    this(id)
    this.point = point
  }

}