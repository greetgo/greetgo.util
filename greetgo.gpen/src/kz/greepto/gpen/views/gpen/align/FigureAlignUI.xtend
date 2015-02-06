package kz.greepto.gpen.views.gpen.align

import java.util.Map
import kz.greepto.gpen.drawport.swt.DrawPortSwt
import kz.greepto.gpen.drawport.swt.DrawableGcSource
import kz.greepto.gpen.util.ColorManager
import kz.greepto.gpen.util.FontManager
import kz.greepto.gpen.views.gpen.align.worker.AlignWorker
import kz.greepto.gpen.views.gpen.align.worker.FigureAlignType
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.ScrolledComposite
import org.eclipse.swt.events.MouseAdapter
import org.eclipse.swt.events.MouseEvent
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Display

class FigureAlignUI {

  val Map<FigureAlignType, Button> buttonMap = newHashMap

  val colors = new ColorManager
  val fonts = new FontManager

  def update(Composite parent) {
    parent.children.forEach[dispose]

    var ScrolledComposite sc = new ScrolledComposite(parent, SWT.V_SCROLL + SWT.H_SCROLL)
    var Composite wall = new Composite(sc, SWT.NONE)
    wall.backgroundMode = SWT.INHERIT_DEFAULT
    wall.background = colors.rgb(255, 255, 255)

    wall.addMouseListener(
      new MouseAdapter() {
        override mouseDoubleClick(MouseEvent e) {
          update(parent)
        }
      })

    sc.content = wall
    sc.expandHorizontal = true
    sc.expandVertical = true

    var lay = new GridLayout
    lay.numColumns = 4
    wall.layout = lay

    buttonMap.clear
    for (fat : FigureAlignType.values) {
      var b = new Button(wall, SWT.NONE)
      buttonMap.put(fat, b)
      b.image = createImage(parent.display, fat.worker, 61, 61)
    }

    sc.minSize = wall.computeSize(SWT.DEFAULT, SWT.DEFAULT)
    parent.layout(true)
  }

  def Image createImage(Display display, AlignWorker alignWorker, int width, int height) {
    var tmp = new Image(display, width, height);
    {
      var dp = DrawPortSwt.fromGcCreator(new DrawableGcSource(fonts, colors, tmp))

      alignWorker.paintIcon(dp, width, height)

      dp.dispose
    }

    var imd = tmp.imageData
    imd.transparentPixel = imd.getPixel(0, 0)

    var ret = new Image(display, imd)

    tmp.dispose

    return ret
  }

  def void setEnable(FigureAlignType fat, boolean enable) {
    buttonMap.get(fat).enabled = enable
  }

}
