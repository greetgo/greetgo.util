package kz.greepto.gpen.editors.gpen.action

import kz.greepto.gpen.editors.gpen.model.Scene

abstract class Action {
  def void apply(Scene scene)
  def void cancel(Scene scene)
}