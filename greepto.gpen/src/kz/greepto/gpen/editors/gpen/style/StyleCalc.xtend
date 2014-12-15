package kz.greepto.gpen.editors.gpen.style

import kz.greepto.gpen.editors.gpen.model.Label
import kz.greepto.gpen.editors.gpen.model.Button
import kz.greepto.gpen.editors.gpen.model.Table

interface StyleCalc {
  def LabelStyle calcForLabel(Label label, PaintStatus ps)

  def ButtonStyle calcForButton(Button button, PaintStatus status)

  def TableStyle calcForTable(Table table, PaintStatus status)

}
