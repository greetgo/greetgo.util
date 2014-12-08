package kz.greepto.gpen.editors.gpen.prop

import kz.greepto.gpen.editors.gpen.action.Action
import kz.greepto.gpen.util.Handler
import kz.greepto.gpen.util.HandlerKiller

interface PropAccessor {
  def Class<?> getType()

  def String getName()

  def Object getValue()

  def PropOptions getOptions()

  def void setValue(Object value)

  def Action getSettingAction(Object newValue)

  def HandlerKiller addChangeHandler(Handler handler)
}
