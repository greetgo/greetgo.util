package kz.greepto.gpen.drawport

import static java.lang.Math.max
import static java.lang.Math.min

class Rect {
  public int x = 0
  public int y = 0
  public int width = 0
  public int height = 0

  override toString() { 'Rect.point(' + x + ', ' + y + '), size(' + width + ',' + height + ')' }

  def Vec2 getPoint() { new Vec2(x, y) }

  def void setPoint(Vec2 point) {
    x = point?.x
    y = point?.y
  }

  def Size getSize() { Size.from(width, height) }

  def void setSize(Size size) {
    width = size?.width
    height = size?.height
  }

  def Vec2 getLeftTop() {
    var X = if(width < 0) x + width else x
    var Y = if(height < 0) y + height else y
    return Vec2.from(X, Y)
  }

  def Vec2 getRightBottom() {
    var X = if(width < 0) x else x + width
    var Y = if(height < 0) y else y + height
    return Vec2.from(X, Y)
  }

  def Vec2 getLeftBottom() {
    var X = if(width < 0) x + width else x
    var Y = if(height < 0) y else y + height
    return Vec2.from(X, Y)
  }

  def Vec2 getRightTop() {
    var X = if(width < 0) x else x + width
    var Y = if(height < 0) y + height else y
    return Vec2.from(X, Y)
  }

  public static val Rect ZERO = from(0, 0, 0, 0)

  public static def Rect zero() { ZERO }

  public static def Rect copy(Rect r) { new Rect(r) }

  public def Rect copy() { new Rect(this) }

  public static def Rect from(int x, int y, int width, int height) {
    return new Rect(x, y, width, height)
  }

  public static def Rect pointSize(Vec2 point, Size size) {
    return from(point?.x, point?.y, size?.width, size?.height)
  }

  public static def Rect fromTo(Vec2 from, Vec2 to) {
    return from(from?.x, from?.y, to?.x - from?.x, to?.y - from?.y)
  }

  private new() {
  }

  private new(int x, int y, int width, int height) {
    this.x = x
    this.y = y
    this.width = width
    this.height = height
  }

  private new(Rect a) {
    x = a.x
    y = a.y
    width = a.width
    height = a.height
  }

  def asd() {
    var a = zero
    var b = zero
    b += a
  }

  def Rect operator_add(Rect r) {
    var x1 = min(x, r.x)
    var x2 = max(x + width, r.x + r.width)

    var y1 = min(y, r.y)
    var y2 = max(y + height, r.y + r.height)

    x = x1
    y = y1
    width = x2 - x1
    height = y2 - y1

    return this
  }

  def Rect operator_plus(Rect r) {
    return copy(this) += r
  }

  def Rect operator_plus(Vec2 step) {
    var ret = copy(this)
    ret.x += step.x
    ret.y += step.y
    return ret
  }

  def boolean contains(Vec2 p) {
    if(p == null) return false

    if(p.x < x) return false
    if(p.y < y) return false

    if(p.x > x + width) return false
    if(p.y > y + height) return false

    return true
  }

  def Rect operator_and(Rect r) {
    var left = Math.max(x, r.x)
    var right = Math.min(x + width, r.x + r.width)
    if(left >= right) return ZERO
    var top = Math.max(y, r.y)
    var bottom = Math.min(y + height, r.y + r.height)
    if(top >= bottom) return ZERO
    from(left, top, right - left, bottom - top)
  }
}
