package kz.greepto.gpen.drawport

class Size {
  public int width
  public int height

  new(int width, int height) {
    this.width = width
    this.height = height
  }

  new() {
    this(0, 0)
  }

  new(Size a) {
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

}
