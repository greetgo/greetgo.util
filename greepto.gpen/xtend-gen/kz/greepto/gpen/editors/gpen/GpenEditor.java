package kz.greepto.gpen.editors.gpen;

import com.google.common.base.Objects;
import kz.greepto.gpen.editors.gpen.GpenCanvas;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class GpenEditor extends EditorPart {
  public void doSave(final IProgressMonitor monitor) {
    InputOutput.<String>println(("doSave monitor = " + monitor));
  }
  
  public void doSaveAs() {
  }
  
  public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
    this.setSite(site);
    this.setInput(input);
  }
  
  public boolean isDirty() {
    return false;
  }
  
  public boolean isSaveAsAllowed() {
    return false;
  }
  
  private GpenCanvas contents = null;
  
  public void createPartControl(final Composite parent) {
    GpenCanvas _gpenCanvas = new GpenCanvas(parent, SWT.NONE);
    this.contents = _gpenCanvas;
  }
  
  public void setFocus() {
    boolean _notEquals = (!Objects.equal(this.contents, null));
    if (_notEquals) {
      this.contents.setFocus();
    }
  }
}
