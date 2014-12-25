package kz.greepto.gpen.editors.gpen

import java.io.ByteArrayOutputStream
import java.util.List
import kz.greepto.gpen.drawport.Size
import kz.greepto.gpen.drawport.Vec2
import kz.greepto.gpen.editors.gpen.model.Fig
import kz.greepto.gpen.editors.gpen.model.Scene
import kz.greepto.gpen.editors.gpen.model.Table
import kz.greepto.gpen.editors.gpen.outline.GpenContentOutlinePage
import kz.greepto.gpen.editors.gpen.prop.sheet.GpenPropertySheetPage
import kz.greepto.gpen.editors.gpen.prop.sheet.GpenPropertySheetSourceProvider
import kz.greepto.gpen.util.HandlerKiller
import kz.greepto.gpen.util.StreamUtil
import org.eclipse.core.commands.operations.IUndoContext
import org.eclipse.core.commands.operations.ObjectUndoContext
import org.eclipse.core.resources.IFile
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.swt.widgets.Composite
import org.eclipse.ui.IEditorInput
import org.eclipse.ui.IEditorSite
import org.eclipse.ui.PartInitException
import org.eclipse.ui.operations.UndoRedoActionGroup
import org.eclipse.ui.part.EditorPart
import org.eclipse.ui.views.contentoutline.IContentOutlinePage
import org.eclipse.ui.views.properties.IPropertySheetPage

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

    var file = input.getAdapter(IFile) as IFile
    var in = file.contents
    var out = new ByteArrayOutputStream
    StreamUtil.copyStreams(in, out)
    println(out.toString("UTF-8"))
  }

  override isDirty() { false }

  override isSaveAsAllowed() { false }

  var GpenCanvas contents = null
  val List<HandlerKiller> killers = newArrayList

  override createPartControl(Composite parent) {

    contents = new GpenCanvas(parent, undoContext);
    site.selectionProvider = contents.selectionProvider
    killers += contents.addChangeSceneHandler[sceneChanged]

    var scene = new Scene

    scene.list += Fig.c('Label', 'asd1', 'x 100 y 50 text asd')
    scene.list += Fig.c('Label', 'asd2', 'x 100 y 150 text привет')

    scene.list += Fig.c('Button', 'but1', 'x 200 y 200 text "жми сюда"')

    var tab = new Table('tab_0001')
    tab.point = Vec2.from(400, 10)
    tab.size = Size.from(500, 350)
    tab.colWidths = '100|200|100'

    tab.addLine('#col1|col2 ~| col3')
    tab.addLine('content1 | content2 | cont 3')
    tab.addLine('*content4 ~| content5 ')
    tab.addLine('content6 | content7 | content 8 ')
    tab.addLine('content9 |~ content10 | content 11 |asd')

    scene.list += tab

    contents.scene = scene

    var urag = new UndoRedoActionGroup(site, undoContext, true)
    urag.fillActionBars(editorSite.actionBars)

    editorSite.actionBars.updateActionBars
  }

  override dispose() {
    killers.forEach[kill]
    killers.clear
    super.dispose()
  }

  def sceneChanged() {
    if(propertySheetPage !== null) propertySheetPage.refresh
  }

  override setFocus() {
    if(contents != null) contents.setFocus()
  }

  GpenPropertySheetPage propertySheetPage = null

  def IPropertySheetPage getPropertySheetPage() {
    if (propertySheetPage === null) {
      propertySheetPage = new GpenPropertySheetPage
      propertySheetPage.propertySourceProvider = new GpenPropertySheetSourceProvider
    }
    return propertySheetPage
  }

  GpenContentOutlinePage contentOutlinePage = null

  def IContentOutlinePage getContentOutlinePage() {
    if (contentOutlinePage === null) {
      contentOutlinePage = new GpenContentOutlinePage
    }
    return contentOutlinePage
  }

}
