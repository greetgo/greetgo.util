package kz.greepto.gpen.editors.gpen.prop

import kz.greepto.gpen.editors.gpen.action.Action
import kz.greepto.gpen.util.HandlerKiller
import kz.greepto.gpen.util.Handler

interface PropAccessor {
  def Class<?> getType()

  def Object getValue()

  def boolean isReadOnly()

  def void setValue(Object value)

  def Action getSettingAction(Object newValue)

  def HandlerKiller addChangeHandler(Handler handler)
}