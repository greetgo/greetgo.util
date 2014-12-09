package kz.greepto.gpen.editors.gpen.action

import java.util.ArrayList
import java.util.List
import kz.greepto.gpen.editors.gpen.model.Scene
import kz.greepto.gpen.util.Repainter
import org.eclipse.core.commands.ExecutionException
import org.eclipse.core.commands.operations.IUndoContext
import org.eclipse.core.commands.operations.IUndoableOperation
import org.eclipse.core.commands.operations.OperationStatus
import org.eclipse.core.runtime.IAdaptable
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.IStatus

import static kz.greepto.gpen.Activator.PLUGIN_ID

class UndoableOperation implements IUndoableOperation {

  val Oper action
  val Scene scene
  val Repainter repainter

  new(Oper action, Scene scene, Repainter repainter) {
    this.action = action
    this.scene = scene
    this.repainter = repainter
  }

  val List<IUndoContext> contextList = new ArrayList

  override addContext(IUndoContext context) {
    contextList.add(context)
  }

  override canExecute() {
    return true
  }

  override canRedo() {
    return true
  }

  override canUndo() {
    return true
  }

  override dispose() {}

  override execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
    doit[action.apply(scene); repainter.repaint]
  }

  override getContexts() {
    return contextList
  }

  override getLabel() {
    return action.displayStr
  }

  override hasContext(IUndoContext context) {
    for (c : contextList) {
      if(c == context) return true
    }
    return false
  }

  override redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
    doit[action.apply(scene); repainter.repaint]
  }

  override removeContext(IUndoContext context) {
    contextList.remove(context)
  }

  private def IStatus doit(Runnable runnable) {
    try {
      runnable.run
      return new OperationStatus(OperationStatus.OK, PLUGIN_ID, OperationStatus.OK, "OK", null)
    } catch (Throwable t) {
      return new OperationStatus(
        OperationStatus.ERROR,
        PLUGIN_ID,
        OperationStatus.ERROR,
        "ERROR " + t.message,
        t
      )
    }
  }

  override undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
    doit[action.cancel(scene); repainter.repaint]
  }

}
