package kz.greepto.gpen.editors.gpen

import org.eclipse.jface.viewers.ISelectionProvider
import org.eclipse.jface.viewers.ISelectionChangedListener
import org.eclipse.jface.viewers.ISelection
import java.util.List
import java.util.ArrayList
import org.eclipse.jface.viewers.SelectionChangedEvent
import org.eclipse.core.runtime.SafeRunner
import org.eclipse.core.runtime.ISafeRunnable

class SelectionProvider implements ISelectionProvider {

  val GpenCanvas owner

  new(GpenCanvas owner) {
    this.owner = owner
  }

  val List<ISelectionChangedListener> listeners = new ArrayList

  override addSelectionChangedListener(ISelectionChangedListener listener) {
    listeners += listener
  }

  ISelection sel

  override getSelection() {
    return sel
  }

  override removeSelectionChangedListener(ISelectionChangedListener listener) {
    listeners -= listener
  }

  override setSelection(ISelection selection) {
    if (selection == sel) return;
    sel = selection

    val ev = new SelectionChangedEvent(this, selection);

    for (listener : listeners) {
      SafeRunner.run(
        new ISafeRunnable() {
          override run() throws Exception {
            listener.selectionChanged(ev)
          }

          override handleException(Throwable exception) {
            exception.printStackTrace
          }
        })
    }
  }

}
