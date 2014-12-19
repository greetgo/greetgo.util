package kz.greepto.gpen.editors.gpen.model.paint

import kz.greepto.gpen.editors.gpen.action.Oper
import kz.greepto.gpen.drawport.Vec2

interface DraggingWorker {
  def Oper createOper(Vec2 mouseNow)
}