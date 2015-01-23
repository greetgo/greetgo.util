package kz.greepto.gpen.editors.gpen.model.visitor

import kz.greepto.gpen.editors.gpen.model.Scene
import kz.greepto.gpen.editors.gpen.model.Label
import kz.greepto.gpen.editors.gpen.model.Combo
import kz.greepto.gpen.editors.gpen.model.Button
import kz.greepto.gpen.editors.gpen.model.Table

interface FigureVisitor<T> {
  def T visitScene(Scene scene)

  def T visitLabel(Label label)

  def T visitCombo(Combo combo)

  def T visitButton(Button button)

  def T visitTable(Table table)
}
