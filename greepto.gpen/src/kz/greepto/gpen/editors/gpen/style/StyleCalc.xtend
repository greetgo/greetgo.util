package kz.greepto.gpen.editors.gpen.style

import kz.greepto.gpen.editors.gpen.model.Label

interface StyleCalc {
  def LabelStyle calcForLabel(Label label, PaintStatus ps)
}
