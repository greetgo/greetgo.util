package kz.greepto.gpen.editors.gpen.model

abstract class RectFigure extends PointFigure {

  public int width
  public int height

  new(String id) {
    super(id)
  }

  new(RectFigure a) {
    super(a)
    width = a.width
    height = a.height
  }

}
