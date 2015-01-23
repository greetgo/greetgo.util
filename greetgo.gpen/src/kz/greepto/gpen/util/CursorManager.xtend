package kz.greepto.gpen.util

import java.util.HashMap
import java.util.Map
import kz.greepto.gpen.drawport.Kursor
import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Cursor
import org.eclipse.swt.widgets.Display

class CursorManager {

  val Map<Kursor, Cursor> storage = new HashMap

  def Cursor getCursor(Kursor kursor) {
    if (kursor === null) return null
    var ret = storage.get(kursor)
    if (ret === null) {
      ret = createCursor(kursor)
      storage.put(kursor, ret)
    }
    return ret
  }

  def void dispose() {
    for (x : storage.values) {
      x.dispose
    }
    storage.clear
  }

  static def int convertType(Kursor kursor) {
    switch (kursor) {
      case ARROW: SWT.CURSOR_ARROW
      case WAIT: SWT.CURSOR_WAIT
      case CROSS: SWT.CURSOR_CROSS
      case APPSTARTING: SWT.CURSOR_APPSTARTING
      case HELP: SWT.CURSOR_HELP
      case SIZEALL: SWT.CURSOR_SIZEALL
      case SIZENESW: SWT.CURSOR_SIZENESW
      case SIZENS: SWT.CURSOR_SIZENS
      case SIZENWSE: SWT.CURSOR_SIZENWSE
      case SIZEWE: SWT.CURSOR_SIZEWE
      case SIZEN: SWT.CURSOR_SIZEN
      case SIZES: SWT.CURSOR_SIZES
      case SIZEE: SWT.CURSOR_SIZEE
      case SIZEW: SWT.CURSOR_SIZEW
      case SIZENE: SWT.CURSOR_SIZENE
      case SIZESE: SWT.CURSOR_SIZESE
      case SIZESW: SWT.CURSOR_SIZESW
      case SIZENW: SWT.CURSOR_SIZENW
      case UPARROW: SWT.CURSOR_UPARROW
      case IBEAM: SWT.CURSOR_IBEAM
      case NO: SWT.CURSOR_NO
      case HAND: SWT.CURSOR_HAND
      default: throw new IllegalArgumentException("Unknown kursor = " + kursor)
    }
  }

  private static def Cursor createCursor(Kursor kursor) {
    new Cursor(Display.current, convertType(kursor))
  }
}
