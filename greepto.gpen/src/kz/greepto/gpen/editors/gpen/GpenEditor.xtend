package kz.greepto.gpen.editors.gpen

import kz.greepto.gpen.editors.gpen.model.Fig
import kz.greepto.gpen.editors.gpen.model.Scene
import org.eclipse.core.commands.operations.IUndoContext
import org.eclipse.core.commands.operations.ObjectUndoContext
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.swt.widgets.Composite
import org.eclipse.ui.IEditorInput
import org.eclipse.ui.IEditorSite
import org.eclipse.ui.PartInitException
import org.eclipse.ui.operations.UndoRedoActionGroup
import org.eclipse.ui.part.EditorPart

class GpenEditor extends EditorPart {

  override doSave(IProgressMonitor monitor) {
    println('doSave monitor = ' + monitor);
  }

  override doSaveAs() {}

  var IUndoContext undoContext

  override init(IEditorSite site, IEditorInput input) throws PartInitException {
    this.site = site
    this.input = input

    undoContext = new ObjectUndoContext(this)
  }

  override isDirty() { false }

  override isSaveAsAllowed() { false }

  var GpenCanvas contents = null

  override createPartControl(Composite parent) {

    contents = new GpenCanvas(parent, undoContext);
    site.selectionProvider = contents.selectionProvider

    var scene = new Scene

    scene.list += Fig.c('Label', 'asd1', 'x 100 y 50 text asd')
    scene.list += Fig.c('Label', 'asd2', 'x 100 y 150 text привет')

    scene.list += Fig.c('Button', 'but1', 'x 200 y 200 text "жми сюда"')

    contents.scene = scene

    var urag = new UndoRedoActionGroup(site, undoContext, true)
    urag.fillActionBars(editorSite.actionBars)

    editorSite.actionBars.updateActionBars

  }

  override setFocus() {
    if(contents != null) contents.setFocus()
  }
}
