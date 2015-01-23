package kz.greepto.gpen.drawport.swt

import kz.greepto.gpen.util.ColorManager
import kz.greepto.gpen.util.FontManager
import org.eclipse.swt.graphics.Drawable
import org.eclipse.swt.graphics.GC

class DrawableGcSource implements GcSource {

  val FontManager fm
  val ColorManager cm
  val Drawable drawable

  new(FontManager fm, ColorManager cm, Drawable drawable) {
    this.fm = fm
    this.cm = cm
    this.drawable = drawable
  }

  override createGC() {
    return new GC(drawable)
  }

  override fm() { fm }

  override cm() { cm }
}
