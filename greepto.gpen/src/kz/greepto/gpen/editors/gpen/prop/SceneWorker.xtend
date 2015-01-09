package kz.greepto.gpen.editors.gpen.prop

import kz.greepto.gpen.editors.gpen.action.Oper
import kz.greepto.gpen.util.HandlerKiller
import kz.greepto.gpen.util.Handler
import kz.greepto.gpen.editors.gpen.model.IdFigure
import java.util.List

interface SceneWorker {
  def String takeId(Object object)

  def IdFigure findByIdOrDie(String id)

  def List<String> all()

  def void applyOper(Oper oper)

  def HandlerKiller addChangeHandler(Handler handler)
}
