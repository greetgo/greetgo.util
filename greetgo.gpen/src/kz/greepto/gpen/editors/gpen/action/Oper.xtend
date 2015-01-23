package kz.greepto.gpen.editors.gpen.action

import kz.greepto.gpen.editors.gpen.model.Scene

abstract class Oper {
  def void apply(Scene scene)

  def void cancel(Scene scene)

  def String getDisplayStr()

  def Oper insteed(Oper oper) { null }
}
