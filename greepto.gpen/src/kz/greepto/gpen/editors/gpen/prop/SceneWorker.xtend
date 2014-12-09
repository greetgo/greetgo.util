package kz.greepto.gpen.editors.gpen.prop

import kz.greepto.gpen.editors.gpen.action.Oper
import kz.greepto.gpen.util.HandlerKiller
import kz.greepto.gpen.util.Handler

interface SceneWorker {
  def String takeId(Object object)

  def void applyOper(Oper oper)

  def HandlerKiller addChangeHandler(Handler handler)
}
