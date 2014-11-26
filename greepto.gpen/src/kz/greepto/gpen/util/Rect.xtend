package kz.greepto.gpen.util

class Rect {
  public int x = 0
  public int y = 0
  public int width = 0
  public int height = 0

  public static def Rect zero() { new Rect }

  private new() {
  }

  private new(Rect a) {
    x = a.x
    y = a.y
    width = a.width
    height = a.height
  }

}
