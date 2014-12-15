package kz.greepto.gpen.drawport

class Size {
  public int width
  public int height

  def static from(int width, int height) { new Size(width, height) }

  def static zero() { from(0, 0) }

  def copy() { new Size(this) }

  private new(int width, int height) {
    this.width = width
    this.height = height
  }

  private new() {
    this(0, 0)
  }

  private new(Size a) {
    this(a.width, a.height)
  }

  def static fromTo(Vec2 from, Vec2 to) {
    if(from === null) throw new NullPointerException('from == null')
    if(to === null) throw new NullPointerException('to == null')
    var w = from.x - to.x
    var h = from.y - to.y
    if(w < 0) w = -w
    if(h < 0) h = -h
    return new Size(w, h)
  }

  def Size operator_plus(Vec2 a) { Size.from(width + a.x, height + a.y) }

  def Size operator_minus(Vec2 a) { Size.from(width - a.x, height - a.y) }

  def Size operator_plus(int[] a) { Size.from(width + a.get(0), height + a.get(1)) }

  def Size operator_minus(int[] a) { Size.from(width - a.get(0), height - a.get(1)) }

  def Size operator_add(int[] a) {
    width += a.get(0)
    height += a.get(1)
    this
  }

  def Size operator_substract(int[] a) {
    width -= a.get(0)
    height -= a.get(1)
    this
  }
}
