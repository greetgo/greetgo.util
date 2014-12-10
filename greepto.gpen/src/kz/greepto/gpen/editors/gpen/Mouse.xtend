package kz.greepto.gpen.editors.gpen

import org.eclipse.swt.events.MouseEvent
import org.eclipse.swt.SWT

class Mouse {
  def static boolean noshift(MouseEvent ev) {
    return ev.stateMask.bitwiseAnd(SWT.SHIFT) == 0
  }

  def static boolean shift(MouseEvent ev) {
    return !noshift(ev)
  }

  /**
   * Is left mouse button (no modifier keys)
   */
  def static boolean LMB(MouseEvent ev) {
    return ev.button == 1 && ev.stateMask.bitwiseAnd(SWT.MODIFIER_MASK) == 0
  }

  /**
   * Is left mouse button with crtl (no other modifier keys)
   */
  def static boolean Ctrl_LMB(MouseEvent ev) {
    if (ev.stateMask.bitwiseAnd(SWT.CTRL) == 0) return false
    var noCtrlStateMask = ev.stateMask.bitwiseAnd(SWT.CTRL.bitwiseNot)
    return ev.button == 1 && noCtrlStateMask.bitwiseAnd(SWT.MODIFIER_MASK) == 0
  }
}