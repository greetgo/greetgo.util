package kz.greepto.gpen.editors.gpen.prop

import kz.greepto.gpen.editors.gpen.action.Action
import kz.greepto.gpen.util.HandlerKiller
import kz.greepto.gpen.util.Handler

interface SceneWorker {
  def String takeId(Object object)

  def void sendAction(Action action)

  def HandlerKiller addChangeHandler(Handler handler)
}
