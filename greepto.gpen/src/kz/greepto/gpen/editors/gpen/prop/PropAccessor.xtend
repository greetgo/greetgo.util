package kz.greepto.gpen.editors.gpen.prop

import kz.greepto.gpen.editors.gpen.action.Oper
import kz.greepto.gpen.util.Handler
import kz.greepto.gpen.util.HandlerKiller

interface PropAccessor {
  public static val DIFF_VALUES = new Object() {
    override toString() { 'Разные значения' }
  }

  def Class<?> getType()

  def String getName()

  def Object getValue()

  def PropOptions getOptions()

  def void setValue(Object value)

  def Oper getSettingOper(Object newValue)

  def HandlerKiller addChangeHandler(Handler handler)

  def boolean compatibleWith(PropAccessor with)

  def PropAccessor operator_add(PropAccessor a)

  def PropAccessor operator_plus(PropAccessor a)

  def ValueGetter getGetter()

  def ValueSetter getSetter()
}
