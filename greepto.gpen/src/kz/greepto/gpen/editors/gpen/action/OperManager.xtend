package kz.greepto.gpen.editors.gpen.action

import kz.greepto.gpen.editors.gpen.model.Scene
import java.util.List
import java.util.ArrayList

class OperManager {

  final List<Oper> actionList = new ArrayList

  int executedCount = 0

  Scene scene

  def void setScene(Scene scene) {
    this.scene = scene
    actionList.clear
    executedCount = 0
  }


  def void append(Oper action) {
    action.apply(scene)

    if (executedCount < actionList.size) {
      cutToExecutedCount
    }

    actionList.add(action)
    executedCount = actionList.size
  }

  def cutToExecutedCount() {
    while (actionList.size > executedCount) {
      actionList.remove(actionList.size - 1)
    }
  }

  def boolean canUndo() {
    return executedCount > 0
  }

  def boolean canRedo() {
    return executedCount < actionList.size
  }

  def Oper undo() {
    if (executedCount == 0) throw new UndoNothing;
    executedCount--
    return actionList.remove(executedCount)
  }

  def Oper redo() {
    if (executedCount >= actionList.size) throw new RedoNothing;
    var ret = actionList.get(executedCount)
    executedCount++
    return ret
  }
}