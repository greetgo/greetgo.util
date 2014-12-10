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
}
