package kz.greepto.gpen.drawport.swt

import java.util.HashSet
import java.util.Set
import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.drawport.FontDef
import kz.greepto.gpen.util.FontInfo
import org.eclipse.swt.graphics.GC
import org.eclipse.swt.graphics.Rectangle
import kz.greepto.gpen.drawport.Vec2

class DrawPortSwt implements DrawPort, FontPreparator {
  val GcSource gcSource
  val Set<GC> gcSet
  val boolean top
  val GC gc

  def static DrawPort fromGcCreator(GcSource gcSource) {
    var gcSet = new HashSet<GC>
    var gc = gcSource.createGC
    return new DrawPortSwt(gcSource, gcSet, true, gc)
  }

  private new(GcSource gcSource, Set<GC> gcSet, boolean top, GC gc) {
    this.gcSource = gcSource
    this.gcSet = gcSet
    this.top = top
    this.gc = gc
  }

  override dispose() {
    gc.dispose
    if (top) {
      gcSet.forEach[dispose]
      gcSet.clear
    }
  }

  override copy() {
    if(gc.disposed) throw new GcAlreadyDisposed
    var newGC = gcSource.createGC
    var ret = new DrawPortSwt(gcSource, gcSet, false, newGC)
    copyParams(this, ret)
    return ret
  }

  private def static copyParams(DrawPortSwt from, DrawPortSwt to) {
    to.gc.background = from.gc.background
    to.gc.foreground = from.gc.foreground
    to.gc.font = from.gc.font
    to.font.assign(from.font)
    to.gc.alpha = from.gc.alpha
    to.gc.backgroundPattern = from.gc.backgroundPattern
    to.gc.foregroundPattern = from.gc.foregroundPattern

    if (from.gc.clipped) {
      to.gc.clipping = from.gc.clipping
    } else {
      to.gc.clipping = null as Rectangle
    }
  }

  val font = new FontDef
  var FontDef appliedFont = null

  override font() { font }

  override void prepareFont() {
    if(font == appliedFont) return;

    appliedFont = font.copy

    gc.font = gcSource.fm.byFontInfo(
      FontInfo.create(
        appliedFont.family,
        appliedFont.height,
        appliedFont.bold,
        appliedFont.italic
      ))
  }

  override from(int x, int y) {
    from(new Vec2(x, y))
  }

  override from(Vec2 from) {
    return new SwtGeom(gc, from, this)
  }
}
