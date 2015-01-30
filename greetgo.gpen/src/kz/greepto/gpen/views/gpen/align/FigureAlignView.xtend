package kz.greepto.gpen.views.gpen.align

import kz.greepto.gpen.drawport.swt.DrawPortSwt
import kz.greepto.gpen.drawport.swt.DrawableGcSource
import kz.greepto.gpen.editors.gpen.GpenEditor
import kz.greepto.gpen.util.ColorManager
import kz.greepto.gpen.util.FontManager
import kz.greepto.gpen.views.gpen.align.worker.AlignWorker
import kz.greepto.gpen.views.gpen.align.worker.FigureAlignType
import org.eclipse.jface.viewers.ISelection
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.ScrolledComposite
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Display
import org.eclipse.ui.ISelectionListener
import org.eclipse.ui.IWorkbenchPart
import org.eclipse.ui.part.ViewPart

class FigureAlignView extends ViewPart {

  val colors = new ColorManager
  val fonts = new FontManager

  GpenEditor gpenEditor = null

  val ISelectionListener listener = [ IWorkbenchPart part, ISelection selection |
    if (part instanceof GpenEditor) {
      gpenEditor = (part as GpenEditor)
    } else {
      gpenEditor = null
    }
  ]

  def Image createImage(Display display, AlignWorker alignWorker, int width, int height) {
    var tmp = new Image(display, width, height);
    {
      var dp = DrawPortSwt.fromGcCreator(new DrawableGcSource(fonts, colors, tmp))

      alignWorker.paintIcon(dp, colors, width, height)

      dp.dispose
    }

    var imd = tmp.imageData
    imd.transparentPixel = imd.getPixel(0, 0)

    var ret = new Image(display, imd)

    tmp.dispose

    return ret
  }

  override createPartControl(Composite parent) {
    site.workbenchWindow.selectionService.addSelectionListener(listener)

    var ScrolledComposite sc = new ScrolledComposite(parent, SWT.V_SCROLL + SWT.H_SCROLL)
    var Composite wall = new Composite(sc, SWT.NONE)
    wall.backgroundMode = SWT.INHERIT_DEFAULT
    wall.background = colors.rgb(255, 255, 255)

    sc.content = wall
    sc.expandHorizontal = true
    sc.expandVertical = true

    var lay = new GridLayout
    lay.numColumns = 3
    wall.layout = lay

    for (fat : FigureAlignType.values) {
      var b = new Button(wall, SWT.NONE)
      b.image = createImage(parent.display, fat.worker, 61, 61)
    }

    sc.minSize = wall.computeSize(SWT.DEFAULT, SWT.DEFAULT)
    parent.layout(true)
  }

  override setFocus() {
  }

  override dispose() {
    site.workbenchWindow.selectionService.removeSelectionListener(listener)
    colors.dispose
    fonts.dispose
    super.dispose()
  }
}
