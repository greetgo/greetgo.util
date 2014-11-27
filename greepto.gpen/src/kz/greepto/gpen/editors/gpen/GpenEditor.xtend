package kz.greepto.gpen.editors.gpen

import org.eclipse.ui.part.EditorPart
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.ui.IEditorSite
import org.eclipse.ui.IEditorInput
import org.eclipse.ui.PartInitException
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.SWT
import kz.greepto.gpen.editors.gpen.model.Fig

class GpenEditor extends EditorPart {

  override doSave(IProgressMonitor monitor) {
    println('doSave monitor = ' + monitor);
  }

  override doSaveAs() {}

  override init(IEditorSite site, IEditorInput input) throws PartInitException {
    this.site = site
    this.input = input
  }

  override isDirty() {false}

  override isSaveAsAllowed() {false}

  var GpenCanvas contents = null

  override createPartControl(Composite parent) {
    contents = new GpenCanvas(parent, SWT.NONE);

    contents.scene.list += Fig.c('Label', 'asd', 'x 100 y 200')
  }

  override setFocus() {
    if (contents != null) contents.setFocus()
  }

}