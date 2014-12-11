package kz.greepto.gpen.drawport.swt

import kz.greepto.gpen.drawport.Kolor
import kz.greepto.gpen.drawport.Style
import org.eclipse.swt.graphics.GC

class SwtStyle implements Style {
  val GC gc

  val GcSource gcSource

  package new(GC gc, GcSource gcSource) {
    this.gc = gc
    this.gcSource = gcSource
  }

  override setForeground(Kolor kolor) {
    gc.foreground = gcSource.cm.from(kolor)
  }

  override setBackground(Kolor kolor) {
    gc.background = gcSource.cm.from(kolor)
  }
}
