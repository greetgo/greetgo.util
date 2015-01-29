package kz.greepto.gpen.views.gpen.figurebox.figureCreator

import kz.greepto.gpen.drawport.Vec2
import kz.greepto.gpen.drawport.swt.DrawPortSwt
import kz.greepto.gpen.drawport.swt.DrawableGcSource
import kz.greepto.gpen.editors.gpen.model.visitor.VisitorPaint
import kz.greepto.gpen.editors.gpen.model.visitor.VisitorPlacer
import kz.greepto.gpen.editors.gpen.style.dev.DevStyleCalc
import kz.greepto.gpen.util.ColorManager
import kz.greepto.gpen.util.FontManager
import kz.greepto.gpen.views.gpen.figurebox.figureCreator.FigureCreator
import org.eclipse.swt.graphics.Device
import org.eclipse.swt.graphics.GC
import org.eclipse.swt.graphics.Image

class FigureCreatorUtil {

  public var Device device
  public var FontManager fonts
  public var ColorManager colors


  def Image createImage(FigureCreator fc) {
    var hs = fc.holstSize()

    var im = new Image(device, hs.width, hs.height)

    var gcSource = new DrawableGcSource(fonts, colors, im)

    var dp = DrawPortSwt.fromGcCreator(gcSource)

    var style = new DevStyleCalc

    var placer = new VisitorPlacer(dp, style, null)

    var vp = new VisitorPaint(placer)
    vp.mouse = Vec2.from(0, 0)

    var but = fc.createFigure()

    var pr = but.visit(vp)

    dp.dispose

    var p = pr.place

    var full = new Image(device, p.width + 1, p.height + 1);

    {
      var gc = new GC(full)
      gc.drawImage(im, p.x, p.y, p.width + 1, p.height + 1, 0, 0, p.width + 1, p.height + 1)
      gc.dispose
    }

    var im32 = new Image(device, 32, 32);
    {
      var gc = new GC(im32)
      gc.drawImage(full, 0, 0, p.width + 1, p.height + 1, 0, 0, 32, 32)
      gc.dispose
    }

    //    {
    //      var ilo = new ImageLoader
    //      ilo.data = #[im.imageData]
    //      ilo.save("/home/pompei/tmp/asd-im.png", SWT.IMAGE_PNG)
    //    }
    //    {
    //      var ilo = new ImageLoader
    //      ilo.data = #[full.imageData]
    //      ilo.save("/home/pompei/tmp/asd-full.png", SWT.IMAGE_PNG)
    //    }
    //    {
    //      var ilo = new ImageLoader
    //      ilo.data = #[im32.imageData]
    //      ilo.save("/home/pompei/tmp/asd-32.png", SWT.IMAGE_PNG)
    //    }

    im.dispose
    full.dispose

    return im32
  }
}
