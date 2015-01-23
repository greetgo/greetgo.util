package kz.greepto.gpen.drawport.swt

import org.eclipse.swt.graphics.GC
import kz.greepto.gpen.util.FontManager
import kz.greepto.gpen.util.ColorManager

interface GcSource {
  def GC createGC()
  def FontManager fm()
  def ColorManager cm()
}
