package kz.greepto.gpen.editors.gpen.outline

import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Label
import org.eclipse.ui.views.contentoutline.ContentOutlinePage

class GpenContentOutlinePage extends ContentOutlinePage {

  override createControl(Composite parent) {

    println('++++++++++++++++asd')

    if ("1a".equals("a")) {
      new Label(parent, SWT.NONE).text = 'dsadsadad'
      return;
    }

    super.createControl(parent)
    treeViewer.contentProvider = new GpenOutlineContentProvider
    treeViewer.labelProvider = new GpenOutlineLabelProvider
    treeViewer.input = 'dsadsadad'
  }

}
