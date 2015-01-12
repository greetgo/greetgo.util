package kz.greepto.gpen.editors.gpen.outline

import kz.greepto.gpen.editors.gpen.GpenEditor
import kz.greepto.gpen.editors.gpen.prop.SceneWorker
import org.eclipse.jface.viewers.ISelection
import org.eclipse.jface.viewers.ITreeContentProvider
import org.eclipse.jface.viewers.SelectionChangedEvent
import org.eclipse.jface.viewers.Viewer
import org.eclipse.swt.widgets.Composite
import org.eclipse.ui.ISelectionListener
import org.eclipse.ui.IWorkbenchPart
import org.eclipse.ui.views.contentoutline.ContentOutline
import org.eclipse.ui.views.contentoutline.ContentOutlinePage
import org.eclipse.ui.views.properties.PropertySheet

class GpenContentOutlinePage extends ContentOutlinePage {
  public SceneWorker sceneWorker

  new(SceneWorker sceneWorker) {
    this.sceneWorker = sceneWorker
  }

  val contentProvider = new ITreeContentProvider() {
    override getChildren(Object parentElement) { #[] }

    override getElements(Object inputElement) {
      if(sceneWorker === null) return #[]
      return sceneWorker.all
    }

    override getParent(Object element) { null }

    override hasChildren(Object element) { false }

    override dispose() {}

    override inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
  }

  val labelProvider = new GpenContentOutlineLabelProvider(this)

  boolean quietSelectionChange = false

  val ISelectionListener listener = [ IWorkbenchPart part, ISelection selection |
    if (part instanceof GpenEditor) {
      sceneWorker = (part as GpenEditor).sceneWorker
    }
    if(part instanceof ContentOutline) return;
    if(part instanceof PropertySheet) return;
    quietSelectionChange = true
    try {
      treeViewer.setSelection(selection, true)
    } finally {
      quietSelectionChange = false
    }
  ]

  override createControl(Composite parent) {
    super.createControl(parent)
    treeViewer.contentProvider = contentProvider
    treeViewer.labelProvider = labelProvider

    site.workbenchWindow.selectionService.addSelectionListener(listener)

    refresh
  }

  override selectionChanged(SelectionChangedEvent event) {
    if(quietSelectionChange) return;
    super.selectionChanged(event)
  }

  override dispose() {
    site.workbenchWindow.selectionService.removeSelectionListener(listener)
    super.dispose()
  }

  def refresh() {
    treeViewer.input = 'abra kadabra'
  }

}
