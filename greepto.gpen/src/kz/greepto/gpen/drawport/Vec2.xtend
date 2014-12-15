package kz.greepto.gpen.drawport

class Vec2 {
  public int x
  public int y

  new(int x, int y) {
    this.x = x
    this.y = y
  }

  new() {
    this(0, 0)
  }

  new(Vec2 a) {
    this(a.x, a.y)
  }

  override toString() { "Vec2(" + x + ", " + y + ")" }

  def Vec2 copy() { new Vec2(this) }

  def static from(int x, int y) { new Vec2(x, y) }

  def Vec2 operator_plus(Vec2 a) { from(x + a.x, y + a.y) }

  def Vec2 operator_plus(int[] a) { from(x + a.get(0), y + a.get(1)) }

  def Vec2 operator_plus(Size s) { from(x + s.width, y + s.height) }

  def Vec2 operator_minus(Vec2 a) { from(x - a.x, y - a.y) }

  def Vec2 operator_minus(int[] a) { from(x - a.get(0), y - a.get(1)) }

  def Vec2 operator_minus(Size s) { from(x - s.width, y - s.height) }
}
