package kz.greepto.gpen.editors.gpen.style

import kz.greepto.gpen.editors.gpen.model.Label
import kz.greepto.gpen.editors.gpen.model.Button

interface StyleCalc {
  def LabelStyle calcForLabel(Label label, PaintStatus ps)

  def ButtonStyle calcForButton(Button button, PaintStatus status)

}
