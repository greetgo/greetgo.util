package kz.greepto.gpen.editors.gpen.action

import kz.greepto.gpen.editors.gpen.model.Scene
import kz.greepto.gpen.util.Repainter
import org.eclipse.core.commands.ExecutionException
import org.eclipse.core.commands.operations.AbstractOperation
import org.eclipse.core.commands.operations.OperationStatus
import org.eclipse.core.runtime.IAdaptable
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.IStatus

import static kz.greepto.gpen.Activator.PLUGIN_ID

class GpenOperation extends AbstractOperation {

  public val Oper oper
  val Scene scene
  val Repainter repainter

  new(Oper oper, Scene scene, Repainter repainter) {
    super(oper.displayStr ?: oper.class.simpleName)
    this.oper = oper
    this.scene = scene
    this.repainter = repainter
  }

  override dispose() {}

  override execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
    doit[oper.apply(scene); repainter.repaint]
  }

  override getLabel() {
    return oper.displayStr
  }

  override redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
    doit[oper.apply(scene); repainter.repaint]
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
    doit[oper.cancel(scene); repainter.repaint]
  }

  def void simpleUndo() {
    oper.cancel(scene)
  }
}
