package kz.greepto.gpen.views.gpen;

import com.google.common.base.Objects;
import kz.greepto.gpen.editors.gpen.Selection;
import kz.greepto.gpen.editors.gpen.model.IdFigure;
import kz.greepto.gpen.editors.gpen.model.PointFigure;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class GpenPropertyView extends ViewPart {
  private Composite parent;
  
  private Composite forFocus;
  
  private ISelectionListener listener;
  
  public void createPartControl(final Composite parent) {
    this.parent = parent;
    final ISelectionListener _function = new ISelectionListener() {
      public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
        if ((selection instanceof Selection)) {
          GpenPropertyView.this.setSelection(((Selection) selection));
        } else {
          GpenPropertyView.this.setSelection(null);
        }
      }
    };
    this.listener = _function;
    IWorkbenchPartSite _site = this.getSite();
    IWorkbenchWindow _workbenchWindow = _site.getWorkbenchWindow();
    ISelectionService _selectionService = _workbenchWindow.getSelectionService();
    _selectionService.addSelectionListener(this.listener);
  }
  
  public void dispose() {
    boolean _notEquals = (!Objects.equal(this.listener, null));
    if (_notEquals) {
      IWorkbenchPartSite _site = this.getSite();
      IWorkbenchWindow _workbenchWindow = _site.getWorkbenchWindow();
      ISelectionService _selectionService = _workbenchWindow.getSelectionService();
      _selectionService.removeSelectionListener(this.listener);
      InputOutput.<String>println("fdsafsdf DISPOSE");
      this.listener = null;
    }
    super.dispose();
  }
  
  public void setSelection(final Selection sel) {
    boolean _isDisposed = this.parent.isDisposed();
    if (_isDisposed) {
      return;
    }
    Control[] _children = this.parent.getChildren();
    final Procedure1<Control> _function = new Procedure1<Control>() {
      public void apply(final Control it) {
        it.dispose();
      }
    };
    IterableExtensions.<Control>forEach(((Iterable<Control>)Conversions.doWrapArray(_children)), _function);
    boolean _equals = Objects.equal(sel, null);
    if (_equals) {
      Label lab = new Label(this.parent, SWT.NONE);
      lab.setText("Выделите элементы в Gpen Editor-е");
      this.parent.layout(true);
      return;
    }
    boolean _isEmpty = sel.figureList.isEmpty();
    if (_isEmpty) {
      return;
    }
    IdFigure fig = sel.figureList.get(0);
    if ((!(fig instanceof PointFigure))) {
      return;
    }
    PointFigure pfig = ((PointFigure) fig);
    ScrolledComposite sc = new ScrolledComposite(this.parent, SWT.V_SCROLL);
    Composite wall = new Composite(sc, SWT.NONE);
    GridLayout lay = new GridLayout();
    lay.numColumns = 3;
    wall.setLayout(lay);
    {
      Label lab_1 = new Label(wall, SWT.NONE);
      lab_1.setText("id");
    }
    Label _label = new Label(wall, SWT.NONE);
    _label.setText(":");
    {
      Label lab_1 = new Label(wall, SWT.NONE);
      lab_1.setText(fig.id);
    }
    {
      Label lab_1 = new Label(wall, SWT.NONE);
      lab_1.setText("x");
    }
    Label _label_1 = new Label(wall, SWT.NONE);
    _label_1.setText(":");
    {
      final Text txt = new Text(wall, (SWT.SINGLE + SWT.BORDER));
      GridData gd = new GridData();
      gd.horizontalAlignment = GridData.FILL;
      txt.setLayoutData(gd);
      int _x = pfig.getX();
      String _plus = ("" + Integer.valueOf(_x));
      txt.setText(_plus);
      final ModifyListener _function_1 = new ModifyListener() {
        public void modifyText(final ModifyEvent it) {
          String _text = txt.getText();
          InputOutput.<String>println(_text);
        }
      };
      txt.addModifyListener(_function_1);
    }
    for (int i = 0; (i < 10); i++) {
      {
        Label _label_2 = new Label(wall, SWT.NONE);
        _label_2.setText(("addi " + Integer.valueOf(i)));
        Label _label_3 = new Label(wall, SWT.NONE);
        _label_3.setText(":");
        Label _label_4 = new Label(wall, SWT.NONE);
        _label_4.setText("adddi");
      }
    }
    this.parent.layout(true);
  }
  
  public void setFocus() {
    boolean _notEquals = (!Objects.equal(this.forFocus, null));
    if (_notEquals) {
      this.forFocus.setFocus();
    }
  }
}
