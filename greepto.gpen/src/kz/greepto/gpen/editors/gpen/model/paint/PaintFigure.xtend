package kz.greepto.gpen.editors.gpen.model.paint

import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.drawport.Rect
import kz.greepto.gpen.drawport.Vec2
import kz.greepto.gpen.editors.gpen.style.StyleCalc

interface PaintFigure {
  def void setEnvironment(DrawPort dp, StyleCalc styleCalc)

  def Rect getPlace()

  def PaintResult paint(Vec2 mouse)

  def String getFigureId()
}
