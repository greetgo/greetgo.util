package kz.greepto.gpen.editors.gpen.model

interface FigureVisitor<T> {

  def T visitScene(Scene scene)

}