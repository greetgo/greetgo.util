package kz.greepto.gpen.editors.gpen

import kz.greepto.gpen.editors.gpen.model.Fig
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Composite
import org.eclipse.ui.IEditorInput
import org.eclipse.ui.IEditorSite
import org.eclipse.ui.PartInitException
import org.eclipse.ui.part.EditorPart
import kz.greepto.gpen.editors.gpen.model.Scene

class GpenEditor extends EditorPart {

  override doSave(IProgressMonitor monitor) {
    println('doSave monitor = ' + monitor);
  }

  override doSaveAs() {}

  override init(IEditorSite site, IEditorInput input) throws PartInitException {
    this.site = site
    this.input = input

  }

  override isDirty() { false }

  override isSaveAsAllowed() { false }

  var GpenCanvas contents = null

  override createPartControl(Composite parent) {

    contents = new GpenCanvas(parent, SWT.NONE);
    site.selectionProvider = contents.selectionProvider

    var scene = new Scene

    scene.list += Fig.c('Label', 'asd1', 'x 100 y 50 text asd')
    scene.list += Fig.c('Label', 'asd2', 'x 100 y 150 text привет')

    scene.list += Fig.c('Button', 'but1', 'x 200 y 200 text "жми сюда"')

    contents.scene = scene
  }

  override setFocus() {
    if(contents != null) contents.setFocus()
  }

}
