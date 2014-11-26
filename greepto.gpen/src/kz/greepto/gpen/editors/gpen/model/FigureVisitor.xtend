package kz.greepto.gpen.editors.gpen.model

interface FigureVisitor<T> {

  def T visitScene(Scene scene)

  def T visitLabel(Label label)

  def T visitCombo(Combo combo)

  def T visitButton(Button button)

}