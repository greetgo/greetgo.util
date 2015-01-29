package kz.greepto.gpen.views.gpen.figurebox

import kz.greepto.gpen.util.ColorManager
import kz.greepto.gpen.util.FontManager
import kz.greepto.gpen.views.gpen.figurebox.figureCreator.FigureCreatorList
import kz.greepto.gpen.views.gpen.figurebox.figureCreator.FigureCreatorUtil
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.ScrolledComposite
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Label

class FigureBoxUI {

  val colors = new ColorManager
  val fonts = new FontManager

  val fcList = new FigureCreatorList;

  new(Composite parent) {

    var fcu = new FigureCreatorUtil
    fcu.fonts = fonts
    fcu.colors = colors
    fcu.device = parent.display

    var ScrolledComposite sc = new ScrolledComposite(parent, SWT.V_SCROLL + SWT.H_SCROLL)
    var Composite wall = new Composite(sc, SWT.NONE)
    wall.backgroundMode = SWT.INHERIT_DEFAULT
    wall.background = colors.rgb(255, 255, 255)

    sc.content = wall
    sc.expandHorizontal = true
    sc.expandVertical = true

    var lay = new GridLayout
    lay.numColumns = 2
    wall.layout = lay

    var String currentGroup = null

    for (fc : fcList) {
      {
        var group = fc.group
        if (group != currentGroup) {
          currentGroup = group
          var labu = new Label(wall, SWT.NONE)
          labu.text = group
          var gdu = new GridData
          gdu.horizontalSpan = 2
          labu.layoutData = gdu
        }
      }
      {
        var im = fcu.createImage(fc)

        var lab = new Label(wall, SWT.NONE)
        lab.text = '   '
        lab.background = null

        var b = new Button(wall, SWT.NONE)
        b.image = im
        b.text = fc.name + '  '
        var gd = new GridData
        b.layoutData = gd
      }
    }

    sc.minSize = wall.computeSize(SWT.DEFAULT, SWT.DEFAULT)
    parent.layout(true)
  }

  def dispose() {
    colors.dispose
    fonts.dispose
  }
}
