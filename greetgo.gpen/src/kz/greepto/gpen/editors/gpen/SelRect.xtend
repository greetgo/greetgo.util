package kz.greepto.gpen.editors.gpen

import kz.greepto.gpen.drawport.Rect
import kz.greepto.gpen.drawport.Vec2

class SelRect {
  public val Vec2 from
  public val Vec2 to

  private new(Vec2 from, Vec2 to) {
    this.from = from.copy
    this.to = to.copy
  }

  public static def SelRect from(Vec2 from, Vec2 to) {
    return new SelRect(from, to)
  }

  public def boolean at(Rect rect) {
    if (from.x < to.x) {
      return fullIn(rect)
    }
    return partIn(rect)
  }

  private def boolean fullIn(Rect rect) {

    var A = Rect.fromTo(from, to)

    var A_LT = A.leftTop
    var B_LT = rect.leftTop

    if(A_LT.x > B_LT.x) return false
    if(A_LT.y > B_LT.y) return false

    var A_RB = A.rightBottom
    var B_RB = rect.rightBottom

    if(A_RB.x < B_RB.x) return false
    if(A_RB.y < B_RB.y) return false

    return true
  }

  private def boolean partIn(Rect rect) {
    var A = Rect.fromTo(from, to)

    var A_LT = A.leftTop
    var B_RB = rect.rightBottom

    if(A_LT.x > B_RB.x) return false
    if(A_LT.y > B_RB.y) return false

    var B_LT = rect.leftTop
    var A_RB = A.rightBottom

    if(B_LT.x > A_RB.x) return false
    if(B_LT.y > A_RB.y) return false

    return true
  }
}
