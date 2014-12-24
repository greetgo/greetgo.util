package kz.greepto.gpen.editors.gpen.outline;

import kz.greepto.gpen.editors.gpen.outline.GpenOutlineContentProvider;
import kz.greepto.gpen.editors.gpen.outline.GpenOutlineLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class GpenContentOutlinePage extends ContentOutlinePage {
  public void createControl(final Composite parent) {
    InputOutput.<String>println("++++++++++++++++asd");
    boolean _equals = "1a".equals("a");
    if (_equals) {
      Label _label = new Label(parent, SWT.NONE);
      _label.setText("dsadsadad");
      return;
    }
    super.createControl(parent);
    TreeViewer _treeViewer = this.getTreeViewer();
    GpenOutlineContentProvider _gpenOutlineContentProvider = new GpenOutlineContentProvider();
    _treeViewer.setContentProvider(_gpenOutlineContentProvider);
    TreeViewer _treeViewer_1 = this.getTreeViewer();
    GpenOutlineLabelProvider _gpenOutlineLabelProvider = new GpenOutlineLabelProvider();
    _treeViewer_1.setLabelProvider(_gpenOutlineLabelProvider);
    TreeViewer _treeViewer_2 = this.getTreeViewer();
    _treeViewer_2.setInput("dsadsadad");
  }
}
