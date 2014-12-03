package kz.greepto.gpen.views.gpen;

import kz.greepto.gpen.editors.gpen.Selection
import kz.greepto.gpen.editors.gpen.model.PointFigure
import org.eclipse.jface.viewers.ISelection
import org.eclipse.swt.SWT
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Text
import org.eclipse.ui.ISelectionListener
import org.eclipse.ui.IWorkbenchPart
import org.eclipse.ui.part.ViewPart
import org.eclipse.swt.custom.ScrolledComposite

public class GpenPropertyView extends ViewPart {
  Composite parent
  Composite forFocus

  ISelectionListener listener

  override void createPartControl(Composite parent) {
    this.parent = parent

    //site.workbenchWindow.selectionService.addSelectionListener
    listener = [ IWorkbenchPart part, ISelection selection |
      if (selection instanceof Selection) {
        setSelection(selection as Selection)
      } else {
        setSelection(null)
      }
    ]

    site.workbenchWindow.selectionService.addSelectionListener(listener)
  }

  override dispose() {
    if (listener != null) {
      site.workbenchWindow.selectionService.removeSelectionListener(listener)
      println('fdsafsdf DISPOSE')
      listener = null
    }
    super.dispose()
  }

  def void setSelection(Selection sel) {
    if(parent.disposed) return;
    parent.children.forEach[dispose]

    if (sel == null) {
      var lab = new Label(parent, SWT.NONE)
      lab.text = 'Выделите элементы в Gpen Editor-е'
      parent.layout(true)
      return;
    }

    if(sel.figureList.empty) return;

    var fig = sel.figureList.get(0)

    if(!(fig instanceof PointFigure)) return;

    var pfig = fig as PointFigure

    var ScrolledComposite sc = new ScrolledComposite(parent, SWT.V_SCROLL);
    var Composite wall = new Composite(sc, SWT.NONE)

    var lay = new GridLayout
    lay.numColumns = 3
    wall.layout = lay

    {
      var lab = new Label(wall, SWT.NONE)
      lab.text = 'id'
    }
    new Label(wall, SWT.NONE).text = ':'
    {
      var lab = new Label(wall, SWT.NONE)
      lab.text = fig.id
    }
    {
      var lab = new Label(wall, SWT.NONE)
      lab.text = 'x'
    }
    new Label(wall, SWT.NONE).text = ':'
    {
      val txt = new Text(wall, SWT.SINGLE + SWT.BORDER)
      var gd = new GridData()
      gd.horizontalAlignment = GridData.FILL
      txt.layoutData = gd
      txt.text = '' + pfig.x
      txt.addModifyListener [
        println(txt.text)
      ]
    }

    for (var i = 0; i < 10; i++) {
      new Label(wall, SWT.NONE).text = 'addi ' + i
      new Label(wall, SWT.NONE).text = ':'
      new Label(wall, SWT.NONE).text = 'adddi'
    }

    parent.layout(true)
  }

  override void setFocus() {
    if(forFocus != null) forFocus.setFocus
  }
}
