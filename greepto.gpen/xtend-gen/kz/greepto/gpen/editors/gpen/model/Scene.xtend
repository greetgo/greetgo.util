package kz.greepto.gpen.editors.gpen.model

class Scene extends Figure {

  override <T> visit(FigureVisitor<T> v) {
    v.visitScene(this);
  }

}
